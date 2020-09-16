package com.bcaf.ivan.finalprojectandroid.Helper

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.bcaf.ivan.finalprojectandroid.Entity.TokenResult
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.apache.commons.codec.binary.Base64
import java.lang.Exception


class SessionManager {
    val gson: Gson = GsonBuilder().create()
    var prefs: SharedPreferences

    constructor(context: Context) {
        prefs = context.getSharedPreferences("TokenUser", Context.MODE_PRIVATE)
    }

    // region decoder
    private val jsonDecoder = object :
        JsonDecoder<JWTAuthHeader, CustomJWTAuthPayload> {

        override fun headerFrom(json: String): JWTAuthHeader {
            return gson.fromJson(json, JWTAuthHeader::class.java)
        }

        override fun payloadFrom(json: String): CustomJWTAuthPayload {
            return gson.fromJson(json, CustomJWTAuthPayload::class.java)
        }
    }
    private val decoder = object :
        Base64Decoder {
        override fun decode(bytes: ByteArray): ByteArray {
            return Base64.decodeBase64(bytes)
        }

        override fun decode(string: String): ByteArray {
            return Base64.decodeBase64(string)
        }
    }

    // endregion
    fun setSession(rs: String) {
        val t = JWT.decode(rs, jsonDecoder, decoder)
        prefs
        with(prefs.edit()) {
            try {
                putString(
                    "Token",
                    gson.toJson(gson.fromJson(t!!.payload.iss, TokenResult::class.java))
                )
            } catch (e: Exception) {
                putString(
                    "Token",
                    gson.toJson(TokenResult())
                )
            }
            commit()
        }
    }

    fun getSession(): TokenResult {
        return gson.fromJson(
            if (prefs.getString("Token", "") == "") gson.toJson(
                TokenResult()
            ) else prefs.getString("Token", ""), TokenResult::class.java
        )
    }

    @SuppressLint("CommitPrefEdits")
    fun removeSession() {
        prefs.edit().remove("Token").apply()
    }
}
package com.bcaf.ivan.finalprojectandroid.Controller

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.bcaf.ivan.finalprojectandroid.R
import com.bcaf.ivan.finalprojectandroid.Util.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import org.apache.commons.codec.binary.Base64


class LoginActivity : AppCompatActivity() {
    var gson: Gson = GsonBuilder().create()

    private val jsonDecoder = object : JsonDecoder<JWTAuthHeader, CustomJWTAuthPayload> {

        override fun headerFrom(json: String): JWTAuthHeader {
            return gson.fromJson(json, JWTAuthHeader::class.java)
        }

        override fun payloadFrom(json: String): CustomJWTAuthPayload {
            return gson.fromJson(json, CustomJWTAuthPayload::class.java)
        }
    }
    private val decoder = object : Base64Decoder {
        override fun decode(bytes: ByteArray): ByteArray {
            return Base64.decodeBase64(bytes)
        }

        override fun decode(string: String): ByteArray {
            return Base64.decodeBase64(string)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

    }

    fun loginClick(view: View) {
        var email = inp_email.text.toString()
        var password = inp_password.text.toString()
        if (emailValidation(email)) {

            val emailBody: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                email
            )

            val passwordBody: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                password
            )
            UserUtil().getUser().login(emailBody, passwordBody)
                .enqueue(object : Callback<String> {
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.d("Failed", "Failed Login")
                        Toast.makeText(
                            this@LoginActivity,
                            "Wrong email or password!",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        var rs = response.body()!!.toString()
                        Log.d("Result", rs)
                        val t = JWT.decode(rs, jsonDecoder, decoder)
                        Log.d("Result token", t!!.payload.iss)

//                Toast.makeText(this@LoginActivity,response.body(),Toast.LENGTH_LONG).show()
                    }
                })
        } else {
            Toast.makeText(this,"email not valid!",Toast.LENGTH_LONG).show()
        }

    }

    fun emailValidation(email: String): Boolean {
        var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    fun registerClick(view: View) {
        startActivity(
            Intent(
                this.baseContext,
                RegisterActivity::class.java
            )
        )
    }
}

package com.bcaf.ivan.finalprojectandroid.Controller

import android.content.Intent
import android.os.Bundle
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
import org.apache.commons.codec.binary.Base64
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    var gson: Gson = GsonBuilder().create()

    // region decoder
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
    // endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

    }

    // region login click
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

            userLogin(emailBody,passwordBody)
        } else {
            Toast.makeText(this, "email not valid!", Toast.LENGTH_LONG).show()
        }
    }
    // endregion

    // region user login
    fun userLogin(emailBody: RequestBody, passwordBody: RequestBody) {
        UserUtil().getUser().login(emailBody, passwordBody)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Wrong email or password!",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    var rs = response.body()!!.toString()
                    val t = JWT.decode(rs, jsonDecoder, decoder)
                    startActivity(
                        Intent(
                            applicationContext,
                            MainActivity::class.java
                        ).putExtra("tokenResult", t!!.payload.iss)
                    )

                }
            })
    }
    // endregion

    // region email validation
    fun emailValidation(email: String): Boolean {
        var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }
    // endregion

    fun registerClick(view: View) {
        startActivity(
            Intent(
                this.baseContext,
                RegisterActivity::class.java
            )
        )
    }
}

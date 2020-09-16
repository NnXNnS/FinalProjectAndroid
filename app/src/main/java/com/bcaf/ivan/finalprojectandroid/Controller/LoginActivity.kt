package com.bcaf.ivan.finalprojectandroid.Controller

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bcaf.ivan.finalprojectandroid.Helper.*
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
    lateinit var sessionManager: SessionManager
    lateinit var fieldChecker: FieldChecker
    lateinit var message: ToastMessage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sessionManager = SessionManager(applicationContext)
        fieldChecker = FieldChecker()
        message = ToastMessage(applicationContext)
        if (sessionManager.getSession().userId != "")
            startActivity(
                Intent(
                    applicationContext,
                    MainActivity::class.java
                )
            )
    }

    // region login click
    fun loginClick(view: View) {
        var email = inp_email.text.toString()
        var password = inp_password.text.toString()
        var listField = arrayListOf(email, password)
        if (fieldChecker.fieldNull(email.trim(), password.trim())) {
            message.nullField()
        } else {
            if (fieldChecker.emailValidation(email)) {

                val emailBody: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    email
                )

                val passwordBody: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    password
                )

                userLogin(emailBody, passwordBody)
            } else {
                message.emailNotValid()
            }
        }
    }
    // endregion

    // region user login
    fun userLogin(emailBody: RequestBody, passwordBody: RequestBody) {
        UserUtil().getUser().login(emailBody, passwordBody)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    message.custom("Wrong email or password!")
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    var rs = response.body()!!.toString()
                    sessionManager.setSession(rs)
                    if (fieldChecker.fieldNull(sessionManager.getSession().userId.trim()))
                        sessionManager.removeSession()
                    else
                        startActivity(
                            Intent(
                                applicationContext,
                                MainActivity::class.java
                            )
                        )

                }
            })
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

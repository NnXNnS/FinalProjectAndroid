package com.bcaf.ivan.finalprojectandroid.Controller

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bcaf.ivan.finalprojectandroid.Entity.Agency
import com.bcaf.ivan.finalprojectandroid.Entity.User
import com.bcaf.ivan.finalprojectandroid.R
import com.bcaf.ivan.finalprojectandroid.Util.AgencyUtil
import com.bcaf.ivan.finalprojectandroid.Util.UserUtil
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterActivity : AppCompatActivity() {
    var gson: Gson = GsonBuilder().create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

    }

    fun createParsingUser(
        firstName: String,
        lastName: String,
        password: String,
        email: String,
        mobileNumber: String
    ): User {
        var user = User("", firstName, lastName, email, password, mobileNumber, "")
        return user
    }

    fun registerClick(v: View) {
        val pass = inp_password.text.toString()
        val rePass = inp_rePassword.text.toString()

        var user = createParsingUser(
            inp_firstName.text.toString(),
            inp_lastName.text.toString(),
            inp_password.text.toString(),
            inp_email.text.toString(),
            inp_mobileNUmber.text.toString()
        )
        if (checkPassword(pass, rePass)) {
            if (emailValidation(user.email!!)) {
                UserUtil().getUser().checkEmail(user)
                    .enqueue(object : Callback<ResponseBody> {
                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Toast.makeText(
                                this@RegisterActivity,
                                "Error!",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                        ) {
                            val checkEmailResult = response.body()!!.string()
                            var userCheckEmailResult =
                                gson.fromJson(checkEmailResult, User::class.java)
                            if (userCheckEmailResult.id == null || userCheckEmailResult.id == "") {
                                createUser(user)
                            } else {
                                Toast.makeText(
                                    this@RegisterActivity,
                                    "Email telah terdaftar!",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    })

            } else {
                Toast.makeText(this, "Email not valid!", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "wrong input password!", Toast.LENGTH_LONG).show()
        }
    }

    fun createUser(user:User) {
        UserUtil().getUser().register(user)
            .enqueue(object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Error!",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onResponse(
                    call: Call<User>,
                    response: Response<User>
                ) {
                    val userRegisterResult = response.body()!!

                    val agencyName = inp_agencyName.text.toString()
                    val agencyDetails = inp_agencyDetail.text.toString()
                    var agency: Agency =
                        Agency(
                            "",
                            "",
                            agencyName,
                            agencyDetails,
                            userRegisterResult.id
                        )
                    Log.d("Agency", gson.toJson(agency))
                    createAgency(agency)
                }
            })
    }

    fun createAgency(agency: Agency) {
        AgencyUtil().getAgency().createAgency(agency)
            .enqueue(object : Callback<Agency> {
                override fun onFailure(
                    call: Call<Agency>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Error!",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onResponse(
                    call: Call<Agency>,
                    response: Response<Agency>
                ) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Register Success!",
                        Toast.LENGTH_LONG
                    ).show()
                    startActivity(
                        Intent(
                            this@RegisterActivity.baseContext,
                            LoginActivity::class.java
                        )
                    )
                }

            })
    }

    fun emailValidation(email: String): Boolean {
        var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    fun checkPassword(pass: String, rePass: String): Boolean {
        return pass == rePass
    }
}
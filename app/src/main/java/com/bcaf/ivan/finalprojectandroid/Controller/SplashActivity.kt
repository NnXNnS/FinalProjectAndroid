package com.bcaf.ivan.finalprojectandroid.Controller

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bcaf.ivan.finalprojectandroid.R

class SplashActivity: Activity() {
    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        var logo=findViewById<ImageView>(R.id.ic_logo)
        val animation = AnimationUtils.loadAnimation(this@SplashActivity, R.anim.fade_in)
        logo.startAnimation(animation)
        handler = Handler()
        handler.postDelayed({
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 5000)

    }
}
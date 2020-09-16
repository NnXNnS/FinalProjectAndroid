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
import com.bcaf.ivan.finalprojectandroid.Helper.CustomActivity
import com.bcaf.ivan.finalprojectandroid.R

class SplashActivity: Activity() {
    lateinit var handler: Handler
    lateinit var activity: CustomActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        activity= CustomActivity(this)
        // region splash screen animation fade in
        /*var logo=findViewById<ImageView>(R.id.ic_logo)
        val animation = AnimationUtils.loadAnimation(this@SplashActivity, R.anim.fade_in)
        logo.startAnimation(animation)*/
        // endregion
        activity.startAndDestroy(LoginActivity::class.java,2000L)
    }
}
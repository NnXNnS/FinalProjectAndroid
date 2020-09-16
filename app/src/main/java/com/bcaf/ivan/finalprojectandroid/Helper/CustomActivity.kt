package com.bcaf.ivan.finalprojectandroid.Helper

import android.app.Activity
import android.content.Intent
import android.os.Handler

class CustomActivity(var activity: Activity) {
    fun startAndDestroy(route:Class<out Any>,duration:Long=100L){
        var handler = Handler()
        handler.postDelayed({
            activity.startActivity(
                Intent(
                    activity.applicationContext,
                    route
                )
            )
            activity.finish()
        }, duration)
    }
    fun start(route:Class<out Any>,duration:Long=100L){
        var handler = Handler()
        handler.postDelayed({
            activity.startActivity(
                Intent(
                    activity.applicationContext,
                    route
                )
            )
        }, duration)
    }
}
@file:Suppress("DEPRECATION")

package com.bcaf.ivan.finalprojectandroid.Helper

import android.app.Activity
import android.content.Intent
import android.os.Handler

class CustomActivity(var activity: Activity) {
    fun startAndDestroy(route:Class<out Any>,duration:Long=100L,key:String="",value:String=""){
        val handler = Handler()
        handler.postDelayed({
            activity.startActivity(
                Intent(
                    activity.applicationContext,
                    route
                ).putExtra(key,value)
            )
            activity.finish()
        }, duration)
    }
    fun start(route:Class<out Any>,duration:Long=100L,key:String="",value:String=""){
        val handler = Handler()
        handler.postDelayed({
            activity.startActivity(
                Intent(
                    activity.applicationContext,
                    route
                ).putExtra(key,value)
            )
        }, duration)
    }
}
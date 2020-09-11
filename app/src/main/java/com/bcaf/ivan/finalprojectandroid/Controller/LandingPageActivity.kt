package com.bcaf.ivan.finalprojectandroid.Controller

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bcaf.ivan.finalprojectandroid.Adapter.LandingPageAdapter
import com.bcaf.ivan.finalprojectandroid.Entity.Bus
import com.bcaf.ivan.finalprojectandroid.Entity.TokenResult
import com.bcaf.ivan.finalprojectandroid.R
import com.bcaf.ivan.finalprojectandroid.Util.BusUtil
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_landing_page.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LandingPageActivity : AppCompatActivity() {
    private val gson = GsonBuilder().create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page)
        var tokenResult = intent.getStringExtra("tokenResult")
        var tokenUser = gson.fromJson(tokenResult, TokenResult::class.java)
        Toast.makeText(applicationContext, "Welcome " + tokenUser.userName, Toast.LENGTH_LONG)
            .show()
        txt_firstName.text = tokenUser.userName

        rv_bus.setHasFixedSize(true)
        rv_bus.layoutManager = LinearLayoutManager(this)
        val agencyBody: RequestBody = RequestBody.create(
            MediaType.parse("text/plain"),
            tokenUser.agencyId!!
        )
        getAllBus(agencyBody)
    }
    override fun onBackPressed() {
        Toast.makeText(applicationContext,"Log out?",Toast.LENGTH_LONG).show()
    }
    fun getAllBus(agencyBody:RequestBody){
        BusUtil().getBus().getAllBus(agencyBody).enqueue(object : Callback<List<Bus>> {
            override fun onFailure(call: Call<List<Bus>>, t: Throwable) {
                Toast.makeText(applicationContext,t.message,Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<Bus>>, response: Response<List<Bus>>) {
                var busList=response.body()!!

                createAdapter(busList)
            }
        })
    }
    fun createAdapter(busList:List<Bus>){
        val adapter = LandingPageAdapter(busList)
        adapter.notifyDataSetChanged()
        rv_bus.adapter = adapter
    }
}
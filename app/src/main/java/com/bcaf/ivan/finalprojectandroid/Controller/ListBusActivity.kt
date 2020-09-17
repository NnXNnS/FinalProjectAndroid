package com.bcaf.ivan.finalprojectandroid.Controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bcaf.ivan.finalprojectandroid.Entity.Bus
import com.bcaf.ivan.finalprojectandroid.Helper.ToastMessage
import com.bcaf.ivan.finalprojectandroid.R
import com.bcaf.ivan.finalprojectandroid.Util.BusUtil
import kotlinx.android.synthetic.main.activity_list_bus.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListBusActivity : AppCompatActivity() {
    lateinit var toast:ToastMessage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_bus)
        toast= ToastMessage(applicationContext)
        var busId=intent.getStringExtra("busId")
        getBus(busId!!)
    }
    fun getBus(busId:String){
        val busIdReq: RequestBody = RequestBody.create(
            MediaType.parse("text/plain"),
            busId
        )
        BusUtil().getBus().getBus(busIdReq).enqueue(object : Callback<Bus>{
            override fun onResponse(call: Call<Bus>, response: Response<Bus>) {
                var bus=response.body()!!
                txt_busDetails_code.text=bus.code
                txt_busDetails_make.text=bus.make
            }

            override fun onFailure(call: Call<Bus>, t: Throwable) {
                toast.error()
            }
        })
    }
}
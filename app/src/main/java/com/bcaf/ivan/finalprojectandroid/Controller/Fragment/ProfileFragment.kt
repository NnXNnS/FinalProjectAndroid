package com.bcaf.ivan.finalprojectandroid.Controller.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bcaf.ivan.finalprojectandroid.Entity.User
import com.bcaf.ivan.finalprojectandroid.Helper.SessionManager
import com.bcaf.ivan.finalprojectandroid.Helper.ToastMessage
import com.bcaf.ivan.finalprojectandroid.R
import com.bcaf.ivan.finalprojectandroid.Util.UserUtil
import kotlinx.android.synthetic.main.fragment_profile.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    lateinit var sessionManager:SessionManager
    lateinit var toast:ToastMessage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManager= SessionManager(context!!)
        toast= ToastMessage(context!!)

        getProfile()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
    fun getProfile(){
        val userId: RequestBody = RequestBody.create(
            MediaType.parse("text/plain"),
            sessionManager.getSession().userId
        )
        UserUtil().getUser().getUser(userId).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                var user = response.body()
                txt_profile_name.text="${user!!.firstName} ${user!!.lastName}"
                txt_profile_mobileNumber.text="${user!!.mobileNumber}"

            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                toast.error()
            }

        })
    }
}
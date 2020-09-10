package com.bcaf.ivan.finalprojectandroid.Util

import com.bcaf.ivan.finalprojectandroid.Services.ApiService
import com.bcaf.ivan.finalprojectandroid.Services.UserService

class UserUtil {
    constructor()
    fun getUser():UserService{
        return ApiService.getClient().create(UserService::class.java)
    }
}
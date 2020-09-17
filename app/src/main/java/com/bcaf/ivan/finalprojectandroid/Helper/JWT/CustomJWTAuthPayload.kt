package com.bcaf.ivan.finalprojectandroid.Helper.JWT


class CustomJWTAuthPayload(val jti:String,iat:Long,val sub:String,iss:String,val exp:String): JWTAuthPayload(iss, iat)
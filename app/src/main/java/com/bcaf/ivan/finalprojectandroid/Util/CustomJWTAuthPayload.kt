package com.bcaf.ivan.finalprojectandroid.Util


class CustomJWTAuthPayload(val jti:String,iat:Long,val sub:String,iss:String,val exp:String): JWTAuthPayload(iss, iat)
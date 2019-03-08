package com.raj.sharephoto.common

import android.text.TextUtils

fun String.isEmailValid(email:String):Boolean{
    return  !(TextUtils.isEmpty(email))||email.contains("@")
}

fun String.isPasswordValid(password:String):Boolean{
    return password.length > 4
}


fun String.isNameValid(name:String):Boolean{
    return name.length > 2
}
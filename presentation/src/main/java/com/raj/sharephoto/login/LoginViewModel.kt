package com.raj.sharephoto.login

import androidx.lifecycle.ViewModel
import com.raj.sharephoto.common.isEmailValid
import com.raj.sharephoto.common.isNameValid
import com.raj.sharephoto.common.isPasswordValid

open class LoginViewModel: ViewModel(

){

    fun isEmailValid(mail:String):Boolean{
        return mail.isEmailValid(mail)
    }

    fun isPasswordValid(password:String):Boolean{
        return password.isPasswordValid(password)
    }


}
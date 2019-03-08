package com.raj.sharephoto.signup

import androidx.lifecycle.ViewModel
import com.raj.sharephoto.common.isEmailValid
import com.raj.sharephoto.common.isNameValid
import com.raj.sharephoto.common.isPasswordValid

open class SignUpViewModel: ViewModel(

){

    fun isEmailValid(mail:String):Boolean{
        return mail.isEmailValid(mail)
    }

    fun isPasswordValid(password:String):Boolean{
        return password.isPasswordValid(password)
    }

    fun isNameValid(name:String):Boolean{
        return name.isNameValid(name)
    }

}
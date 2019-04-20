package com.raj.sharephoto.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel :ViewModel(){

    val emailField:MutableLiveData<String> = MutableLiveData()
    val passwordField:MutableLiveData<String> = MutableLiveData()
    val isLoggingIn:MutableLiveData<Boolean> = MutableLiveData()

    fun doLogin(){

        var email=emailField.value
        var password=passwordField.value







        //do validation

    }


}
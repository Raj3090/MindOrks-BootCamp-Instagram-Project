package com.raj.sharephoto.common

import android.content.Context
import android.content.Intent
import com.raj.sharephoto.login.LoginActivity
import com.raj.sharephoto.signup.SignUpActivity

fun Context.getSignUpActivityIntent():Intent{
    return Intent(this, SignUpActivity::class.java)
}


fun Context.getLoginActivityIntent():Intent{
    return Intent(this, LoginActivity::class.java)
}

package com.raj.sharephoto.login

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.raj.sharephoto.R
import com.raj.sharephoto.common.getSignUpActivityIntent
import kotlinx.android.synthetic.main.activity_login.*


/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity() {

    lateinit var loginVM:LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginVM=ViewModelProviders.of(this).get(LoginViewModel::class.java)

        password.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })

        email_sign_in_button.setOnClickListener {
                attemptLogin()
        }

        sign_up_button.setOnClickListener {
            startActivity(getSignUpActivityIntent())
        }

    }



    private fun attemptLogin() {

        // Store values at the time of the login attempt.
        val emailStr = email.text.toString()
        val passwordStr = password.text.toString()

        val emailValid = loginVM.isEmailValid(emailStr)

        val passwordValid = loginVM.isPasswordValid(password = passwordStr)

        if (!emailValid){

        }

        if (!passwordValid){

        }
    }

}

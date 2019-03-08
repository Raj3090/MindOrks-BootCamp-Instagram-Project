package com.raj.sharephoto.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import com.raj.sharephoto.R
import com.raj.sharephoto.common.getLoginActivityIntent
import com.raj.sharephoto.login.LoginViewModel
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    lateinit var signUpVM:SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        signUpVM= ViewModelProviders.of(this).get(SignUpViewModel::class.java)

        password.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptSignUp()
                return@OnEditorActionListener true
            }
            false
        })

        sign_up_button.setOnClickListener {
            attemptSignUp()
        }

        login_button.setOnClickListener {
            startActivity(getLoginActivityIntent())
        }
    }

    private fun attemptSignUp() {
        // Store values at the time of the login attempt.
        val emailStr = email.text.toString()
        val passwordStr = password.text.toString()
        val name = name.text.toString()

        val emailValid = signUpVM.isEmailValid(emailStr)

        val passwordValid = signUpVM.isPasswordValid(password = passwordStr)

        val nameValid = signUpVM.isNameValid(name = name)

        if (!emailValid){

        }

        if (!passwordValid){

        }

        if (!nameValid){

        }

    }
}

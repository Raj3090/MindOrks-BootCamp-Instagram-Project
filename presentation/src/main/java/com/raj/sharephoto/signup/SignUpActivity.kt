package com.raj.sharephoto.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import com.raj.sharephoto.R
import com.raj.sharephoto.common.getLoginActivityIntent
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
            finish()
        }

        cancel_mail.setOnClickListener {
            email.setText("")
        }

        email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(s.isNotEmpty()){
                    cancel_mail.visibility= View.VISIBLE
                }else{
                    cancel_mail.visibility= View.GONE
                }
            }

            override fun afterTextChanged(s: Editable) {

            }
        })
    }

    private fun attemptSignUp() {
        // Store values at the time of the login attempt.
        val emailStr = email.text.toString()
        val passwordStr = password.text.toString()
        val nameStr = name.text.toString()

        val emailValid = signUpVM.isEmailValid(emailStr)

        val passwordValid = signUpVM.isPasswordValid(password = passwordStr)

        val nameValid = signUpVM.isNameValid(name = nameStr)

        if (!emailValid){
            mail_container.setBackgroundResource(R.drawable.login_edittext_error_bcg)

        }

        if (!passwordValid){
            password.setBackgroundResource(R.drawable.login_edittext_error_bcg)

        }

        if (!nameValid){
            name.setBackgroundResource(R.drawable.login_edittext_error_bcg)

        }

    }
}

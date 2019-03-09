package com.raj.sharephoto.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

        mail_container.setBackgroundResource(R.drawable.login_edittext_rounded_bcg)

        password.setBackgroundResource(R.drawable.login_edittext_rounded_bcg)


        email_sign_in_button.setOnClickListener {
            attemptLogin()
        }

        cancel_mail.setOnClickListener {
            email.setText("")
        }

        sign_up_button.setOnClickListener {
            startActivity(getSignUpActivityIntent())
            finish()
        }

    }



    private fun attemptLogin() {

        // Store values at the time of the login attempt.
        val emailStr = email.text.toString()
        val passwordStr = password.text.toString()

        val emailValid = loginVM.isEmailValid(emailStr)

        val passwordValid = loginVM.isPasswordValid(password = passwordStr)

        if (!emailValid){
            mail_container.setBackgroundResource(R.drawable.login_edittext_error_bcg)
        }

        if (!passwordValid){
            password.setBackgroundResource(R.drawable.login_edittext_error_bcg)
        }
    }

}

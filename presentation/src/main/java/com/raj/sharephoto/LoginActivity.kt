package com.raj.sharephoto

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_login.*


/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : Activity() {

    private var  isSignUpUIVisible=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Set up the login form.
        password.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })

        email_sign_in_button.setOnClickListener {
            val drawable = email.getBackground() as GradientDrawable
            drawable.setStroke(3, Color.GRAY) // set stroke width and stroke color

            if(isSignUpUIVisible){
                attemptSignUp()
            }else{
                attemptLogin()
            }
        }

    }

    private fun attemptSignUp() {

    }

    private fun changeUI() {
        if(isSignUpUIVisible){
            name.visibility=View.GONE
            app_logo.visibility=View.VISIBLE
            user_profile_image.visibility=View.GONE
            email_sign_in_button.text="LOGIN"
        }else{
            name.visibility=View.VISIBLE
            app_logo.visibility=View.GONE
            user_profile_image.visibility=View.VISIBLE
            email_sign_in_button.text="SIGN UP"
        }

    }

    private fun attemptLogin() {


        // Reset errors.
        email.error = null
        password.error = null

        // Store values at the time of the login attempt.
        val emailStr = email.text.toString()
        val passwordStr = password.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(passwordStr) && !isPasswordValid(passwordStr)) {
            password.error = getString(R.string.error_invalid_password)
            focusView = password
            cancel = true
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(emailStr)) {
            email.error = getString(R.string.error_field_required)
            focusView = email
            cancel = true
        } else if (!isEmailValid(emailStr)) {
            email.error = getString(R.string.error_invalid_email)
            focusView = email
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return email.contains("@")
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 4
    }



    companion object {

        /**
         * Id to identity READ_CONTACTS permission request.
         */
        private val REQUEST_READ_CONTACTS = 0

        /**
         * A dummy authentication store containing known user names and passwords.
         */
        private val DUMMY_CREDENTIALS = arrayOf("foo@example.com:hello", "bar@example.com:world")
    }
}

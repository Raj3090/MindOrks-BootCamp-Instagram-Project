package com.raj.sharephoto.ui.login

import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import com.raj.sharephoto.utils.common.Event
import com.raj.sharephoto.InstagramApplication
import com.raj.sharephoto.R
import com.raj.sharephoto.databinding.ActivityLoginBinding
import com.raj.sharephoto.di.component.DaggerActivityComponent
import com.raj.sharephoto.di.module.ActivityModule
import com.raj.sharephoto.ui.main.MainActivity
import com.raj.sharephoto.ui.signup.SignUpActivity
import com.raj.sharephoto.utils.display.Toaster
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: LoginViewModel


    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        insertDependencies()
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_login)
        binding.setLifecycleOwner(this)
        binding.viewModel=viewModel
        setupObservers()
    }

    private fun insertDependencies() {
        DaggerActivityComponent
            .builder()
            .applicationComponent((application as InstagramApplication).applicationComponent)
            .activityModule(ActivityModule(this))
            .build()
            .inject(this)

    }
    private fun setupObservers() {

        viewModel.messageString.observe(this, Observer {
            it.data?.run { showMessage(this) }
        })

        viewModel.messageStringId.observe(this, Observer {
            it.data?.run { showMessage(this) }
        })

        viewModel.mainNavigation.observe(this, Observer<Event<Bundle>> {
            it.getIfNotHandled()?.run {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }
        })

        viewModel.signUpNavigation.observe(this, Observer<Event<Bundle>> {
            it.getIfNotHandled()?.run {
                startActivity(Intent(applicationContext, SignUpActivity::class.java))
                finish()
            }
        })
    }

    private fun showMessage(@StringRes resId: Int) = showMessage(getString(resId))

    private fun showMessage(message: String) =  Toaster.show(application, message)
}

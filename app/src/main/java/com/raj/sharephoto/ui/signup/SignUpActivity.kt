package com.raj.sharephoto.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.raj.sharephoto.utils.common.Event
import com.raj.sharephoto.InstagramApplication
import com.raj.sharephoto.R
import com.raj.sharephoto.databinding.ActivitySignUpBinding
import com.raj.sharephoto.di.component.DaggerActivityComponent
import com.raj.sharephoto.di.module.ActivityModule
import com.raj.sharephoto.ui.login.LoginActivity
import com.raj.sharephoto.ui.main.MainActivity
import com.raj.sharephoto.utils.display.Toaster
import javax.inject.Inject

class SignUpActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel:SignUpViewModel

    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        insertDependencies()
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_sign_up)
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

        viewModel.loginNavigation.observe(this, Observer<Event<Bundle>> {
            it.getIfNotHandled()?.run {
                startActivity(Intent(applicationContext, LoginActivity::class.java))
                finish()
            }
        })
    }


    private fun showMessage(@StringRes resId: Int) = showMessage(getString(resId))

    private fun showMessage(message: String) =  Toaster.show(application, message)
}

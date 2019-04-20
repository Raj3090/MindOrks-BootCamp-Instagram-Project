package com.raj.sharephoto.ui.login

import androidx.databinding.DataBindingUtil
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.raj.sharephoto.R
import com.raj.sharephoto.databinding.ActivityLoginBinding
import com.raj.sharephoto.di.component.DaggerActivityComponent
import com.raj.sharephoto.di.module.ActivityModule
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
    }

    private fun insertDependencies() {
        DaggerActivityComponent.builder().activityModule(ActivityModule(this)).build().inject(this)
    }
}

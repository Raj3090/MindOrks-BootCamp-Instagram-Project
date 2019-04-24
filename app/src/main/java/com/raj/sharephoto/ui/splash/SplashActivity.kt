package com.raj.sharephoto.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.raj.sharephoto.utils.common.Event
import com.raj.sharephoto.InstagramApplication
import com.raj.sharephoto.R
import com.raj.sharephoto.databinding.ActivitySplashBinding
import com.raj.sharephoto.di.component.DaggerActivityComponent
import com.raj.sharephoto.di.module.ActivityModule
import com.raj.sharephoto.ui.login.LoginActivity
import com.raj.sharephoto.ui.main.MainActivity
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var splashViewModel: SplashViewModel

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        insertDependencies()
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_splash)
        binding.viewModel=splashViewModel
        setUpObservers()
        splashViewModel.onViewCreated()
    }

    private fun setUpObservers() {
        splashViewModel.launchLogin.observe(this, Observer<Event<Bundle>>{
            it.getIfNotHandled()?.run {
                startActivity(Intent(applicationContext, LoginActivity::class.java))
                finish()

            }
        })

        splashViewModel.launchMain.observe(this, Observer<Event<Bundle>>{
            it.getIfNotHandled()?.run {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()

            }
        })
    }

    private fun insertDependencies() {
        DaggerActivityComponent
            .builder().applicationComponent((application as InstagramApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }


}

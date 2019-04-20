package com.raj.sharephoto.di.module

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.mindorks.bootcamp.instagram.utils.ViewModelProviderFactory
import com.raj.sharephoto.ui.login.LoginViewModel
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    fun provideLoginViewModel():LoginViewModel=

        ViewModelProviders.of(activity, ViewModelProviderFactory(LoginViewModel::class){
            LoginViewModel()
        }).get(LoginViewModel::class.java)



}
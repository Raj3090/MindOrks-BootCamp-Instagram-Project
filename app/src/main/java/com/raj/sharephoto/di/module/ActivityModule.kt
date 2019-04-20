package com.raj.sharephoto.di.module

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.raj.sharephoto.data.repository.UserRepository
import com.mindorks.bootcamp.instagram.utils.ViewModelProviderFactory
import com.raj.sharephoto.utils.network.NetworkHelper
import com.raj.sharephoto.ui.login.LoginViewModel
import com.raj.sharephoto.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable


@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    fun provideLoginViewModel(schedulerProvider: SchedulerProvider, networkHelper:NetworkHelper,
                              compositeDisposable:CompositeDisposable, userRepository: UserRepository
    ):LoginViewModel=

        ViewModelProviders.of(activity, ViewModelProviderFactory(LoginViewModel::class){

            LoginViewModel(schedulerProvider,networkHelper, compositeDisposable,userRepository)

        }).get(LoginViewModel::class.java)



}
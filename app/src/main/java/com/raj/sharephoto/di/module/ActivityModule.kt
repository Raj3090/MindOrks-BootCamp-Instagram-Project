package com.raj.sharephoto.di.module

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.raj.sharephoto.data.repository.UserRepository
import com.mindorks.bootcamp.instagram.utils.ViewModelProviderFactory
import com.raj.sharephoto.data.repository.PostRepository
import com.raj.sharephoto.utils.network.NetworkHelper
import com.raj.sharephoto.ui.login.LoginViewModel
import com.raj.sharephoto.ui.main.MainViewModel
import com.raj.sharephoto.ui.photo.share.SharePhotoViewModel
import com.raj.sharephoto.ui.profile.edit.EditProfileViewModel
import com.raj.sharephoto.ui.signup.SignUpViewModel
import com.raj.sharephoto.ui.splash.SplashViewModel
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



    @Provides
    fun provideEditProfileViewModel(schedulerProvider: SchedulerProvider, networkHelper:NetworkHelper,
                              compositeDisposable:CompositeDisposable, userRepository: UserRepository
    ):EditProfileViewModel=

        ViewModelProviders.of(activity, ViewModelProviderFactory(EditProfileViewModel::class){

            EditProfileViewModel(schedulerProvider,networkHelper, compositeDisposable,userRepository)

        }).get(EditProfileViewModel::class.java)



    @Provides
    fun provideMainViewModel(schedulerProvider: SchedulerProvider, networkHelper:NetworkHelper,
                              compositeDisposable:CompositeDisposable, userRepository: UserRepository
    ):MainViewModel=

        ViewModelProviders.of(activity, ViewModelProviderFactory(MainViewModel::class){

            MainViewModel(schedulerProvider,compositeDisposable,networkHelper, userRepository)

        }).get(MainViewModel::class.java)


    @Provides
    fun provideSignUpViewModelViewModel(schedulerProvider: SchedulerProvider, networkHelper:NetworkHelper,
                             compositeDisposable:CompositeDisposable, userRepository: UserRepository
    ): SignUpViewModel =

        ViewModelProviders.of(activity, ViewModelProviderFactory(SignUpViewModel::class){

            SignUpViewModel(schedulerProvider,networkHelper, compositeDisposable,userRepository)

        }).get(SignUpViewModel::class.java)



    @Provides
    fun provideSharePhotoViewModelViewModel(schedulerProvider: SchedulerProvider, networkHelper:NetworkHelper,
                                            compositeDisposable:CompositeDisposable, postRepository: PostRepository, userRepository: UserRepository
    ): SharePhotoViewModel =

        ViewModelProviders.of(activity, ViewModelProviderFactory(SharePhotoViewModel::class){

            SharePhotoViewModel(schedulerProvider,networkHelper, compositeDisposable,postRepository,userRepository)

        }).get(SharePhotoViewModel::class.java)


    @Provides
    fun provideSplashViewModelViewModel(schedulerProvider: SchedulerProvider, networkHelper:NetworkHelper,
                                        compositeDisposable:CompositeDisposable, userRepository: UserRepository
    ): SplashViewModel =

        ViewModelProviders.of(activity, ViewModelProviderFactory(SplashViewModel::class){

            SplashViewModel(schedulerProvider,networkHelper, compositeDisposable,userRepository)

        }).get(SplashViewModel::class.java)



}
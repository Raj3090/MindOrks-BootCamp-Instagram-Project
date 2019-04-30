package com.raj.sharephoto.di.component

import com.raj.sharephoto.di.ActivityScope
import com.raj.sharephoto.di.module.ActivityModule
import com.raj.sharephoto.ui.login.LoginActivity
import com.raj.sharephoto.ui.main.MainActivity
import com.raj.sharephoto.ui.photo.share.SharePhotoActivity
import com.raj.sharephoto.ui.profile.edit.EditProfileActivity
import com.raj.sharephoto.ui.signup.SignUpActivity
import com.raj.sharephoto.ui.splash.SplashActivity
import dagger.Component

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ActivityModule::class]
)
interface ActivityComponent{

    fun inject(loginActivity: LoginActivity)
    fun inject(mainActivity: MainActivity)
    fun inject(signUpActivity: SignUpActivity)
    fun inject(splashActivity: SplashActivity)
    fun inject(sharePhotoActivity: SharePhotoActivity)
    fun inject(editProfileActivity: EditProfileActivity)


}
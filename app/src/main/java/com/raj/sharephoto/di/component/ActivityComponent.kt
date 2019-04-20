package com.raj.sharephoto.di.component

import com.raj.sharephoto.di.module.ActivityModule
import com.raj.sharephoto.ui.login.LoginActivity
import dagger.Component

@Component(
    modules = [ActivityModule::class]
)
interface ActivityComponent{

    fun inject(loginActivity: LoginActivity)

}
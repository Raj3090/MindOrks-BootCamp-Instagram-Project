package com.raj.sharephoto

import android.app.Application
import com.raj.sharephoto.di.component.ApplicationComponent
import com.raj.sharephoto.di.component.DaggerApplicationComponent

import com.raj.sharephoto.di.module.ApplicationModule

class InstagramApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }

    private fun injectDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }
}
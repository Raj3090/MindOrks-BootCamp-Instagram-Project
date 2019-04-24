package com.raj.sharephoto.di.component

import com.raj.sharephoto.di.FragmentScope
import com.raj.sharephoto.di.module.FragmentModule
import com.raj.sharephoto.ui.home.HomeFragment
import com.raj.sharephoto.ui.photo.PhotoFragment
import com.raj.sharephoto.ui.profile.ProfileFragment
import dagger.Component

@FragmentScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [FragmentModule::class]
)
interface FragmentComponent {

    fun inject(fragment: HomeFragment)

    fun inject(fragment: ProfileFragment)

    fun inject(fragment: PhotoFragment)
}
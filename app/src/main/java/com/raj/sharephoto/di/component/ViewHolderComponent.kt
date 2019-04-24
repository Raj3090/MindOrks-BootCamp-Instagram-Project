package com.raj.sharephoto.di.component

import com.raj.sharephoto.di.ViewModelScope
import com.raj.sharephoto.di.module.ViewHolderModule
import com.raj.sharephoto.ui.home.post.PostItemViewHolder
import dagger.Component

@ViewModelScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ViewHolderModule::class]
)
interface ViewHolderComponent {

    fun inject(holder: PostItemViewHolder)
}
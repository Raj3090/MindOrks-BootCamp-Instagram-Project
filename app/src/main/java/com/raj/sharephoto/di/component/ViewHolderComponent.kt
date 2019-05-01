package com.raj.sharephoto.di.component

import com.raj.sharephoto.di.ViewModelScope
import com.raj.sharephoto.di.module.ViewHolderModule
import com.raj.sharephoto.ui.home.post.PostItemViewHolder
import com.raj.sharephoto.ui.photo.gallery.PhotoItemViewHolder
import com.raj.sharephoto.ui.profile.post.MyPostItemViewHolder
import dagger.Component

@ViewModelScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ViewHolderModule::class]
)
interface ViewHolderComponent {

    fun inject(holder: PostItemViewHolder)

    fun inject(holder: PhotoItemViewHolder)
    fun inject(myPostItemViewHolder: MyPostItemViewHolder)
}
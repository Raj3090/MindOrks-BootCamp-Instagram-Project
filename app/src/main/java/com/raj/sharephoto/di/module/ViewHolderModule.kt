package com.raj.sharephoto.di.module

import androidx.recyclerview.widget.RecyclerView
import com.raj.sharephoto.data.repository.PostRepository
import com.raj.sharephoto.data.repository.UserRepository
import com.raj.sharephoto.di.ViewModelScope
import com.raj.sharephoto.ui.home.post.PostItemViewModel
import com.raj.sharephoto.ui.photo.gallery.PhotoItemViewModel
import com.raj.sharephoto.ui.profile.post.MyPostItemViewModel
import com.raj.sharephoto.utils.network.NetworkHelper
import com.raj.sharephoto.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class ViewHolderModule(private val viewHolder: RecyclerView.ViewHolder) {

    @ViewModelScope
    @Provides
    fun providePostItemViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        userRepository: UserRepository,
        postRepository: PostRepository,
        networkHelper: NetworkHelper
    ): PostItemViewModel =
        PostItemViewModel(
            schedulerProvider,
            compositeDisposable,
            userRepository,
            postRepository,
            networkHelper
        )


    @ViewModelScope
    @Provides
    fun providePhotoItemViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        userRepository: UserRepository,
        postRepository: PostRepository,
        networkHelper: NetworkHelper
    ): PhotoItemViewModel =
        PhotoItemViewModel(
            schedulerProvider,
            compositeDisposable,
            userRepository,
            postRepository,
            networkHelper
        )


    @ViewModelScope
    @Provides
    fun provideMyPostItemViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        userRepository: UserRepository,
        postRepository: PostRepository,
        networkHelper: NetworkHelper
    ): MyPostItemViewModel =
        MyPostItemViewModel(
            schedulerProvider,
            compositeDisposable,
            userRepository,
            postRepository,
            networkHelper
        )

}
package com.raj.sharephoto.ui.photo.gallery

import android.net.Uri
import com.raj.sharephoto.data.model.Post
import com.raj.sharephoto.data.repository.PostRepository
import com.raj.sharephoto.data.repository.UserRepository
import com.raj.sharephoto.utils.network.NetworkHelper
import com.raj.sharephoto.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.io.File

class PhotoItemViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    userRepository: UserRepository,
    postRepository: PostRepository,
    networkHelper: NetworkHelper,
    var uri:String?=null
) {

    fun onPhotoClick(){
       uri?.let {
           //open detials activity
       }
    }

    fun updateData(post: String) {
        this.uri = post
    }

}

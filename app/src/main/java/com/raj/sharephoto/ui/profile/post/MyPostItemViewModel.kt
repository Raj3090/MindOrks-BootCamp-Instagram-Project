package com.raj.sharephoto.ui.profile.post

import android.content.Intent
import android.net.Uri
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.raj.sharephoto.data.model.Post
import com.raj.sharephoto.data.model.UploadedPostData
import com.raj.sharephoto.data.repository.PostRepository
import com.raj.sharephoto.data.repository.UserRepository
import com.raj.sharephoto.ui.main.MainActivity
import com.raj.sharephoto.utils.network.NetworkHelper
import com.raj.sharephoto.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.io.File

class MyPostItemViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    userRepository: UserRepository,
    postRepository: PostRepository,
    networkHelper: NetworkHelper,
    var url:String?=null
) {


    fun updateData(post: UploadedPostData) {
        this.url = post.imageUrl
    }

}

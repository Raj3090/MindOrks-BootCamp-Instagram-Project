package com.raj.sharephoto.ui.photo.share

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raj.sharephoto.data.remote.request.PostRequest
import com.raj.sharephoto.data.repository.PostRepository
import com.raj.sharephoto.data.repository.UserRepository
import com.raj.sharephoto.utils.common.Event
import com.raj.sharephoto.utils.display.ScreenUtils.getDropboxIMGSize
import com.raj.sharephoto.utils.display.Toaster
import com.raj.sharephoto.utils.network.NetworkHelper
import com.raj.sharephoto.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File
import okhttp3.MultipartBody



class SharePhotoViewModel(
    private val schedulerProvider: SchedulerProvider,
    private val networkHelper: NetworkHelper,
    private val compositeDisposable: CompositeDisposable,
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) :ViewModel(){
    val profileNavigation: MutableLiveData<Event<Bundle>> = MutableLiveData()
    lateinit var uri:String

    fun sharePhoto(){
        val file = File(uri)
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val body = MultipartBody.Part.createFormData("image", file.name, requestFile)

        val currentUser = userRepository.getCurrentUser()!!
        if (networkHelper.isNetworkConnected()) {
            val call =
                postRepository.uploadPostImage(currentUser,body)
                    .flatMap {
                       val (imgHeight,imgWidth)= getDropboxIMGSize(file)
                        val requestBody=PostRequest(it.data.imageUrl,imgWidth.toString(),imgHeight.toString())
                        postRepository.uploadPost(currentUser,requestBody)
                    }

            compositeDisposable.add(call
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                    {
                        profileNavigation.postValue(Event(Bundle()))
                    },
                    {
                        profileNavigation.postValue(Event(Bundle()))
                    }
                )
            )
        }

    }

    fun setCurrentUri(it: String) {
        uri=it
    }


}

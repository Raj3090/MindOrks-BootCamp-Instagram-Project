package com.raj.sharephoto.ui.profile.edit

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raj.sharephoto.data.remote.request.PostRequest
import com.raj.sharephoto.data.remote.request.ProfileUpdateRequest
import com.raj.sharephoto.data.repository.PostRepository
import com.raj.sharephoto.data.repository.UserRepository
import com.raj.sharephoto.utils.common.Event
import com.raj.sharephoto.utils.display.ScreenUtils
import com.raj.sharephoto.utils.network.NetworkHelper
import com.raj.sharephoto.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import android.provider.MediaStore
import android.util.Log


class EditProfileViewModel(
    private val  schedulerProvider: SchedulerProvider,
    private val  networkHelper: NetworkHelper,
    private val compositeDisposable: CompositeDisposable,
  private val userRepository: UserRepository,private val postRepository: PostRepository
) :ViewModel(){

    lateinit var uri:String
    val profileNavigation: MutableLiveData<Event<Bundle>> = MutableLiveData()
    val selectProfilePhotoDialog: MutableLiveData<Event<Bundle>> = MutableLiveData()
    val nameField: MutableLiveData<String> = MutableLiveData()
    val taglineField: MutableLiveData<String> = MutableLiveData()

    fun onClose(){
        profileNavigation.postValue(Event(Bundle()))
    }


    fun onUpdate(){
        val file = File(uri)
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val body = MultipartBody.Part.createFormData("image", file.name, requestFile)

        val currentUser = userRepository.getCurrentUser()!!
        if (networkHelper.isNetworkConnected()) {
            val call =
                postRepository.uploadPostImage(currentUser,body)
                    .flatMap {
                        val requestBody= ProfileUpdateRequest(nameField.value?.toString() ?:"",it.data.imageUrl, taglineField.value?.toString()?:"")
                        userRepository.updateUserProfileInfo(requestBody)
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


    fun onProfilePhotoChange(){
        selectProfilePhotoDialog.postValue(Event(Bundle()))
    }



}
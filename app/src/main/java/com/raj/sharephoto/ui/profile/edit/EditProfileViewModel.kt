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
import com.raj.sharephoto.data.remote.Networking
import com.raj.sharephoto.data.remote.response.UploadPhotoResponse
import io.reactivex.Single


class EditProfileViewModel(
    private val  schedulerProvider: SchedulerProvider,
    private val  networkHelper: NetworkHelper,
    private val compositeDisposable: CompositeDisposable,
  private val userRepository: UserRepository,private val postRepository: PostRepository
) :ViewModel(){

    var uri:String?=null
    val profileNavigation: MutableLiveData<Event<Bundle>> = MutableLiveData()
    val profileUpdateSuccessfullNavigation: MutableLiveData<Event<Bundle>> = MutableLiveData()
    val selectProfilePhotoDialog: MutableLiveData<Event<Bundle>> = MutableLiveData()
    val nameField: MutableLiveData<String> = MutableLiveData()
    val taglineField: MutableLiveData<String> = MutableLiveData()
    val imageUrlField: MutableLiveData<String> = MutableLiveData()
    val emailField: MutableLiveData<String> = MutableLiveData()
    val isUpdating: MutableLiveData<Boolean> = MutableLiveData()


    fun onClose(){
        profileNavigation.postValue(Event(Bundle()))
    }


    fun onUpdate(){

        uri?.let {
            val file = File(uri)
            val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val body = MultipartBody.Part.createFormData("image", file.name, requestFile)

            val currentUser = userRepository.getCurrentUser()!!

            if (networkHelper.isNetworkConnected()) {
                isUpdating.postValue(true)
                val call =
                    postRepository.uploadPostImage(currentUser,body)
                        .flatMap {
                            userRepository.saveUserProfileUrl(it.data.imageUrl)
                            val requestBody= ProfileUpdateRequest(nameField.value?.toString() ?:"",it.data.imageUrl, taglineField.value?.toString()?:"")
                            userRepository.updateUserProfileInfo(requestBody)
                        }

                compositeDisposable.add(call
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe(
                        {
                            isUpdating.postValue(false)
                            userRepository.saveUserName(nameField.value?.toString() ?:"")
                            profileUpdateSuccessfullNavigation.postValue(Event(Bundle()))
                        },
                        {
                            isUpdating.postValue(false)
                            profileNavigation.postValue(Event(Bundle()))
                        }
                    )
                )
            }
        }

        if(uri==null){
            if (networkHelper.isNetworkConnected()) {
                isUpdating.postValue(true)
                val requestBody= ProfileUpdateRequest(nameField.value?.toString() ?:"",userRepository.getUserProfileUrl(), taglineField.value?.toString()?:"")

                val call =
                    userRepository.updateUserProfileInfo(requestBody)


                compositeDisposable.add(call
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe(
                        {
                            userRepository.saveUserName(nameField.value?.toString() ?:"")
                            profileNavigation.postValue(Event(Bundle()))
                        },
                        {
                            profileNavigation.postValue(Event(Bundle()))
                        }
                    )
                )
            }
        }


    }



    fun onProfilePhotoChange(){
        selectProfilePhotoDialog.postValue(Event(Bundle()))
    }

    fun loadProfileData() {
        userRepository.getCurrentUser()?.let {
            imageUrlField.setValue(userRepository.getUserProfileUrl())
            nameField.value=it.name
            emailField.value=it.email

        }

    }

    val headers: Map<String, String> = mapOf(
        Pair(
            Networking.HEADER_API_KEY,
            Networking.API_KEY
        ),
        Pair(Networking.HEADER_USER_ID, userRepository.getCurrentUser()!!.id),
        Pair(Networking.HEADER_ACCESS_TOKEN, userRepository.getCurrentUser()!!.accessToken)
    )


}
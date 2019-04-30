package com.raj.sharephoto.ui.profile.edit

import androidx.lifecycle.ViewModel
import com.raj.sharephoto.data.repository.UserRepository
import com.raj.sharephoto.utils.network.NetworkHelper
import com.raj.sharephoto.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class EditProfileViewModel(
    schedulerProvider: SchedulerProvider,
    networkHelper: NetworkHelper,
    compositeDisposable: CompositeDisposable,
    userRepository: UserRepository
) :ViewModel(){


    fun onClose(){

    }


    fun onUpdate(){

    }


    fun onProfilePhotoChange(){

    }


}
package com.raj.sharephoto.ui.splash

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raj.sharephoto.utils.common.Event
import com.raj.sharephoto.data.repository.UserRepository
import com.raj.sharephoto.utils.network.NetworkHelper
import com.raj.sharephoto.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class SplashViewModel(
    schedulerProvider: SchedulerProvider,
    networkHelper: NetworkHelper,
    compositeDisposable: CompositeDisposable,
   private val userRepository: UserRepository
) :ViewModel(){

    // Event is used by the view model to tell the activity to launch another Activity
    // view model also provided the Bundle in the event that is needed for the Activity
    val launchMain: MutableLiveData<Event<Bundle>> = MutableLiveData()
    val launchLogin: MutableLiveData<Event<Bundle>> = MutableLiveData()



    fun onViewCreated() {
        // Empty Bundle passed to Activity in Event that is needed by the other Activity
        if (userRepository.getCurrentUser() != null)
            launchMain.postValue(Event(Bundle()))
        else
            launchLogin.postValue(Event(Bundle()))
    }

}
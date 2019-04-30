package com.raj.sharephoto.ui.profile

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raj.sharephoto.data.model.MyProfileData
import com.raj.sharephoto.data.remote.Networking
import com.raj.sharephoto.data.repository.UserRepository
import com.raj.sharephoto.utils.common.Event
import com.raj.sharephoto.utils.network.NetworkHelper
import com.raj.sharephoto.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class ProfileViewModel(
    private val  schedulerProvider: SchedulerProvider,
    private val  compositeDisposable: CompositeDisposable,
    private val  networkHelper: NetworkHelper,
    private val userRepository: UserRepository
) : ViewModel() {

    val tagline: MutableLiveData<String> = MutableLiveData()
    val name: MutableLiveData<String> = MutableLiveData()
    val editNavigation: MutableLiveData<Event<Bundle>> = MutableLiveData()
    val photoUrl: MutableLiveData<String> = MutableLiveData()

    fun fetchProfileInfo(){
        if (networkHelper.isNetworkConnected()) {
            val call =
                userRepository.getUserProfileInfo()

            compositeDisposable.add(call
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                    {
                        tagline.setValue(it.tagline)
                        name.setValue(it.name)
                        photoUrl.setValue(it.profilePicUrl)
                    },
                    {

                    }
                )
            )
        }


    }

    fun navigateEdit(){
        editNavigation.setValue(Event(Bundle()))
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

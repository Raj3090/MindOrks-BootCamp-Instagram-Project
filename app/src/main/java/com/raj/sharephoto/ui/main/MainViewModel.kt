package com.raj.sharephoto.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raj.sharephoto.utils.common.Event
import com.raj.sharephoto.R
import com.raj.sharephoto.data.repository.UserRepository
import com.raj.sharephoto.utils.network.NetworkHelper
import com.raj.sharephoto.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(private val schedulerProvider: SchedulerProvider,
                    private val compositeDisposable: CompositeDisposable,
                    private val networkHelper: NetworkHelper,
                    private val userRepository: UserRepository
) :ViewModel(){
    val navigation: MutableLiveData<Event<BottomMenuNavigation>> = MutableLiveData()

    val navigationListener = object : BottomMenuNavigationListener {
        override fun onMenuSelected(itemId: Int): Boolean {
            when (itemId) {
                R.id.itemHome ->
                    navigation.postValue(Event(BottomMenuNavigation.HOME))

                R.id.itemAddPhotos ->
                    navigation.postValue(Event(BottomMenuNavigation.ADD_PHOTOS))

                R.id.itemProfile ->
                    navigation.postValue(Event(BottomMenuNavigation.PROFILE))

                else -> return false
            }
            return true
        }
    }

    fun onViewCreated() {
        navigation.postValue(Event(BottomMenuNavigation.HOME))
    }
}
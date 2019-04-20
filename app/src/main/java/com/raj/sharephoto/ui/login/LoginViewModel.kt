package com.raj.sharephoto.ui.login

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mindorks.bootcamp.instagram.utils.common.Event
import com.raj.sharephoto.data.repository.UserRepository
import com.mindorks.bootcamp.instagram.utils.common.Status
import com.raj.sharephoto.R
import com.raj.sharephoto.utils.common.Resource
import com.raj.sharephoto.utils.common.Validator
import com.raj.sharephoto.utils.network.NetworkHelper
import com.raj.sharephoto.utils.common.Validator.validateEmailPassword
import com.raj.sharephoto.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import org.reactivestreams.Subscriber
import javax.net.ssl.HttpsURLConnection

class LoginViewModel(private val schedulerProvider: SchedulerProvider,
                     private val networkHelper: NetworkHelper
                     , private val compositeDisposable: CompositeDisposable,
                     private val userRepository: UserRepository
) :ViewModel() {

    private val validationsList: MutableLiveData<List<Validator.Validation>> = MutableLiveData()

    val emailField: MutableLiveData<String> = MutableLiveData()
    val passwordField: MutableLiveData<String> = MutableLiveData()
    val isLoggingIn: MutableLiveData<Boolean> = MutableLiveData()

    val messageStringId: MutableLiveData<Resource<Int>> = MutableLiveData()
    val messageString: MutableLiveData<Resource<String>> = MutableLiveData()
    val dummyNavigation: MutableLiveData<Event<Bundle>> = MutableLiveData()

    val emailValidation: LiveData<Resource<Int>> =
        transformValidation(Validator.Validation.Field.EMAIL)

    val passwordValidation: LiveData<Resource<Int>> =
        transformValidation(Validator.Validation.Field.PASSWORD)

    private fun transformValidation(field: Validator.Validation.Field) =
        Transformations.map(validationsList) {
            it.find { validation -> validation.field == field }
                ?.run { return@run this.resource }
                ?: Resource.unknown()
        }


    fun doLogin() {

        val email = emailField.value
        val password = passwordField.value

        //do validation of password and email
        val validations = validateEmailPassword(email, password)
        validationsList.postValue(validations)
        if (validations.isNotEmpty() && email != null && password != null) {

            val successfullvalidation = validations.filter { it.resource.status == Status.SUCCESS }

            if (successfullvalidation.size == validations.size && networkHelper.isNetworkConnected()) {
                isLoggingIn.postValue(true)
                compositeDisposable.addAll(userRepository.doUserLogin(
                    email,
                    password
                ).subscribeOn(schedulerProvider.io())
                    .subscribe(
                        {
                            userRepository.saveCurrentUser(it)
                            isLoggingIn.postValue(true)
//                        dummyNavigation.postValue(Event(Bundle()))
                        },
                        {
                            handleNetworkError(it)
                            isLoggingIn.postValue(false)
                        }
                    )


                )
            }

        }

    }

    private fun handleNetworkError(err: Throwable?) =
        err?.let {
            networkHelper.castToNetworkError(it).run {
                when (status) {
                    -1 ->
                        messageStringId.postValue(Resource.error(R.string.network_default_error))
                    0 ->
                        messageStringId.postValue(Resource.error(R.string.server_connection_error))
                    HttpsURLConnection.HTTP_INTERNAL_ERROR ->
                        messageStringId.postValue(Resource.error(R.string.network_internal_error))
                    HttpsURLConnection.HTTP_UNAVAILABLE ->
                        messageStringId.postValue(Resource.error(R.string.network_server_not_available))
                    else ->
                        messageString.postValue(Resource.error(message))
                }
            }
        }

}
package com.raj.sharephoto.ui.signup

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.raj.sharephoto.utils.common.Event
import com.raj.sharephoto.data.repository.UserRepository
import com.mindorks.bootcamp.instagram.utils.common.Status
import com.raj.sharephoto.R
import com.raj.sharephoto.utils.common.Resource
import com.raj.sharephoto.utils.common.Validator
import com.raj.sharephoto.utils.network.NetworkHelper
import com.raj.sharephoto.utils.common.Validator.validateEmailPasswordName
import com.raj.sharephoto.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.net.ssl.HttpsURLConnection

class SignUpViewModel(private val schedulerProvider: SchedulerProvider,
                      private val networkHelper: NetworkHelper
                      , private val compositeDisposable: CompositeDisposable,
                      private val userRepository: UserRepository
) :ViewModel() {

    private val validationsList: MutableLiveData<List<Validator.Validation>> = MutableLiveData()

    val emailField: MutableLiveData<String> = MutableLiveData()
    val passwordField: MutableLiveData<String> = MutableLiveData()
    val nameField: MutableLiveData<String> = MutableLiveData()
    val isSigningIn: MutableLiveData<Boolean> = MutableLiveData()

    val messageStringId: MutableLiveData<Resource<Int>> = MutableLiveData()
    val messageString: MutableLiveData<Resource<String>> = MutableLiveData()
    val mainNavigation: MutableLiveData<Event<Bundle>> = MutableLiveData()
    val loginNavigation: MutableLiveData<Event<Bundle>> = MutableLiveData()

    val emailValidation: LiveData<Resource<Int>> =
        transformValidation(Validator.Validation.Field.EMAIL)

    val passwordValidation: LiveData<Resource<Int>> =
        transformValidation(Validator.Validation.Field.PASSWORD)

    val nameValidation: LiveData<Resource<Int>> =
        transformValidation(Validator.Validation.Field.NAME)

    private fun transformValidation(field: Validator.Validation.Field) =
        Transformations.map(validationsList) {
            it.find { validation -> validation.field == field }
                ?.run { return@run this.resource }
                ?: Resource.unknown()
        }


    fun doSignUp() {

        val email = emailField.value
        val password = passwordField.value
        val name = nameField.value

        //do validation of password and email
        val validations = validateEmailPasswordName(email, password,name)
        validationsList.postValue(validations)
        if (validations.isNotEmpty() && email != null && password != null&& name != null) {

            val successfullvalidation = validations.filter { it.resource.status == Status.SUCCESS }

            if (successfullvalidation.size == validations.size && networkHelper.isNetworkConnected()) {
                isSigningIn.postValue(true)
                compositeDisposable.addAll(userRepository.doUserSignUp(
                    email,
                    password,
                    name
                ).subscribeOn(schedulerProvider.io())
                    .subscribe(
                        {
                            userRepository.saveCurrentUser(it)
                            isSigningIn.postValue(true)
                            mainNavigation.postValue(Event(Bundle()))
                        },
                        {
                            handleNetworkError(it)
                            isSigningIn.postValue(false)
                        }
                    )


                )
            }

        }

    }

    fun goToLoginScreen(){
        loginNavigation.postValue(Event(Bundle()))
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
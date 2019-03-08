package com.raj.sharephoto.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.raj.domain.common.Mapper
import com.raj.domain.entities.UserEntity
import com.raj.domain.usecases.UserLogin
import com.raj.sharephoto.entities.User

/**
 * Created by Yossi Segev on 01/01/2018.
 */
class SignUpVMFactory(private val useCase: UserLogin,
                      private val mapper: Mapper<UserEntity, User>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SignUpViewModel() as T
    }

}
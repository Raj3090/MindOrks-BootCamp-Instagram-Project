package com.raj.domain.usecases

import com.raj.domain.common.Transformer
import com.raj.domain.entities.UserEntity
import com.raj.domain.repositories.user.UserRepository
import io.reactivex.Observable

open class UserLogin(transformer: Transformer<UserEntity>,
                     private val userRepository: UserRepository
) : UseCase<UserEntity>(transformer) {
    override fun createObservable(data: Map<String, Any>?): Observable<UserEntity> {
        return userRepository.getUser()
    }

}
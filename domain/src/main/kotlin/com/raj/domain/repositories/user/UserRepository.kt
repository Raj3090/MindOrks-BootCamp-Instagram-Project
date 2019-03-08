package com.raj.domain.repositories.user

import com.raj.domain.entities.UserEntity
import io.reactivex.Observable

interface UserRepository{
    fun getUser(): Observable<UserEntity>
}
package com.raj.sharephoto.data.repository

import com.raj.sharephoto.data.local.db.DatabaseService
import com.mindorks.bootcamp.instagram.data.local.prefs.UserPreferences
import com.raj.sharephoto.data.model.User
import com.raj.sharephoto.data.remote.NetworkService
import com.raj.sharephoto.data.remote.request.LoginRequest
import com.mindorks.bootcamp.instagram.data.remote.request.SignUpRequest
import com.raj.sharephoto.data.model.MyProfileData
import com.raj.sharephoto.data.remote.request.ProfileUpdateRequest
import com.raj.sharephoto.data.remote.response.GeneralResponse
import com.raj.sharephoto.data.remote.response.MyProfileInfoResponse
import io.reactivex.Single
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService,
    private val userPreferences: UserPreferences
) {

    fun saveCurrentUser(user: User) {
        userPreferences.setUserId(user.id)
        userPreferences.setUserName(user.name)
        userPreferences.setUserEmail(user.email)
        userPreferences.setAccessToken(user.accessToken)
    }

    fun removeCurrentUser() {
        userPreferences.removeUserId()
        userPreferences.removeUserName()
        userPreferences.removeUserEmail()
        userPreferences.removeAccessToken()
    }

    fun getCurrentUser(): User? {

        val userId = userPreferences.getUserId()
        val userName = userPreferences.getUserName()
        val userEmail = userPreferences.getUserEmail()
        val accessToken = userPreferences.getAccessToken()

        return if (userId !== null && userName != null && userEmail != null && accessToken != null)
            User(userId, userName, userEmail, accessToken)
        else
            null
    }

    fun doUserLogin(email: String, password: String): Single<User> =
        networkService.doLoginCall(LoginRequest(email, password))
            .map {
                User(
                    it.userId,
                    it.userName,
                    it.userEmail,
                    it.accessToken
                )
            }

    fun doUserSignUp(email: String, password: String, name: String): Single<User> =
        networkService.doSignUpCall(SignUpRequest(email, password,name))
            .map {
                User(
                    it.userId,
                    it.userName,
                    it.userEmail,
                    it.accessToken
                )
            }


    fun getUserProfileInfo(): Single<MyProfileData> =
        networkService.
            doMyProfileInfoCall(userPreferences.getUserId()!!,userPreferences.getAccessToken()!!)
            .map {
               it.data
            }

    fun updateUserProfileInfo(updateRequest: ProfileUpdateRequest): Single<GeneralResponse> =
        networkService.
            doUpdateProfileInfoCall(updateRequest,userPreferences.getUserId()!!,userPreferences.getAccessToken()!!)

}
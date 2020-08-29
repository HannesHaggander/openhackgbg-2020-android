package com.towerowl.openhackgbg2020.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.towerowl.openhackgbg2020.data.AuthenticationDao
import com.towerowl.openhackgbg2020.ext.enqueue
import com.towerowl.openhackgbg2020.repositories.ApiRepository

class AuthenticationViewModel(
    private val apiRepository: ApiRepository,
    private val authenticationDao: AuthenticationDao
) : ViewModel() {

    companion object {
        private const val TAG = "AuthenticationViewModel"
    }

    private val mCurrentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> get() = mCurrentUser

    suspend fun login(user: User) {
        apiRepository.authentication.login(user).enqueue(
            onResponse = { _, r ->
                if (!r.isSuccessful) {
                    return@enqueue
                }

                r.body()?.run {
                    mCurrentUser.postValue(this)
                }
            },
            onFailure = { _, err ->
                Log.w(TAG, "login: Failed to login", Exception(err))
            }
        )
    }

    suspend fun logout() {
        authenticationDao.getUser()?.run { authenticationDao.deleteUser(this) }
        mCurrentUser.postValue(null)
    }

    suspend fun getUser(): User? {
        return authenticationDao.getUser()
    }
}
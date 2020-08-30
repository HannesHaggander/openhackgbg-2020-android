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

    fun register(authUser: AuthUser) {
        apiRepository.authentication.register(authUser).enqueue(onResponse = { c, r ->
            if (!r.isSuccessful) {
                Log.w(TAG, "login: Failed", Exception(r.errorBody()?.string().orEmpty()))
                return@enqueue
            }

            login(authUser)
        }, onFailure = { c, err ->
            Log.e(TAG, "login: Failed", Exception(err.cause))
        })
    }

    fun login(authUser: AuthUser) {
        with(User(authUser.username, "1902837ajikshd9132y4")) {
            authenticationDao.insertUser(this)
            mCurrentUser.postValue(this)
        }

//        apiRepository.authentication.login(authUser).enqueue(
//            onResponse = { c, r ->
//                if (!r.isSuccessful) {
//                    Log.w(TAG, "login: ")
//                    return@enqueue
//                }
//
//                mCurrentUser.postValue(r.body())
//            },
//            onFailure = { c, err ->
//                Log.e(TAG, "login: Failed", java.lang.Exception(err.cause))
//            })
    }

    suspend fun logout() {
        authenticationDao.getUser()?.run { authenticationDao.deleteUser(this) }
        mCurrentUser.postValue(null)
    }

    suspend fun getUser(): User? {
        return authenticationDao.getUser()
    }
}
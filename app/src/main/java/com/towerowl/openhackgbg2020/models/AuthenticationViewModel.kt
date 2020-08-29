package com.towerowl.openhackgbg2020.models

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay

class AuthenticationViewModel : ViewModel() {

    suspend fun authenticate(username: String, password: String): Long {
        //todo
        delay(10000)
        return 0
    }

}
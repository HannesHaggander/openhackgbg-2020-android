package com.towerowl.openhackgbg2020.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.towerowl.openhackgbg2020.App
import com.towerowl.openhackgbg2020.R

class MainActivity : AppCompatActivity() {

    private val navController: NavController by lazy { findNavController(R.id.main_navigation) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userObserver()
    }

    private fun userObserver() {
        App.instance()
            .globalComponent
            .authenticationViewModel()
            .currentUser
            .observe(this) { user ->
                if (user == null) {
                    navController.navigate(R.id.authenticateFragment)
                }
            }
    }

}
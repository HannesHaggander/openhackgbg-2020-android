package com.towerowl.openhackgbg2020.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.towerowl.openhackgbg2020.App
import com.towerowl.openhackgbg2020.R
import com.towerowl.openhackgbg2020.ext.asVisibility
import com.towerowl.openhackgbg2020.ext.invert
import kotlinx.android.synthetic.main.fragment_authenticate.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthenticateFragment : Fragment() {

    private var authenticationJob: Job? = null
        set(value) {
            authentication_cancel.visibility = (value != null).asVisibility()
            field = value
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_authenticate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLayout()
    }

    private fun setupLayout() {
        authentication_login.setOnClickListener {
            with(authenticate_input_username.text.toString()) {
                if (isNullOrEmpty()) {
                    authenticate_input_username.error = getString(R.string.error_username_empty)
                    return@setOnClickListener
                }

                loadingState(true)

                lifecycleScope.launch(IO) {
                    App.instance()
                        .globalComponent
                        .authenticationViewModel()
                        .authenticate(this@with)

                    withContext(Main) {
                        loadingState(false)
                        authenticationJob = null
                    }
                }.also { authenticationJob = it }
            }
        }

        authentication_cancel.setOnClickListener {
            authenticationJob?.cancel("User interrupted job")
            authenticationJob = null
            loadingState(false)
        }
    }

    private fun loadingState(loading: Boolean) {
        authenticate_input_username.isEnabled = loading.invert()
        authentication_login.isEnabled = loading.invert()
        authentication_loading.visibility = loading.asVisibility()

    }
}
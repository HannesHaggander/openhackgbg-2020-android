package com.towerowl.openhackgbg2020.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.towerowl.openhackgbg2020.App
import com.towerowl.openhackgbg2020.R
import com.towerowl.openhackgbg2020.ext.asVisibility
import com.towerowl.openhackgbg2020.ext.invert
import com.towerowl.openhackgbg2020.models.User
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
    ): View? = inflater.inflate(R.layout.fragment_authenticate, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch(IO) {
            with(App.instance().globalComponent.authenticationViewModel().getUser()) {
                withContext(Main) {
                    if (this@with == null) setupLayout()
                    else findNavController().navigate(R.id.action_authenticateFragment_to_communitiesFragment)
                }
            }
        }
    }

    private fun setupLayout() {
        authentication_login.setOnClickListener {
            val username = authenticate_input_username.text.toString()
            val password = authenticate_input_password.text.toString()

            if (username.isNullOrEmpty()) {
                authenticate_input_username.error = getString(R.string.error_username_empty)
                return@setOnClickListener
            }

            if (password.isNullOrEmpty()) {
                authenticate_input_password.error = getString(R.string.error_password_empty)
                return@setOnClickListener
            }

            loadingState(true)

            lifecycleScope.launch(IO) {
                App.instance()
                    .globalComponent
                    .authenticationViewModel()
                    .login(User(username, password))

                withContext(Main) {
                    authenticate_input_password.text?.clear()
                    loadingState(false)
                    authenticationJob = null
                }
            }.also { authenticationJob = it }
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
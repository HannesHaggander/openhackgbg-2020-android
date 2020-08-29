package com.towerowl.openhackgbg2020

import android.app.Application
import com.towerowl.openhackgbg2020.di.ContextModule
import com.towerowl.openhackgbg2020.di.DaggerGlobalComponent
import com.towerowl.openhackgbg2020.di.GlobalComponent

class App : Application() {

    companion object {
        private var _instance: App? = null

        fun instance() = _instance ?: throw Exception("App instance not set")
    }

    lateinit var globalComponent: GlobalComponent

    private fun globalComponentBuilder() = DaggerGlobalComponent.builder()
        .contextModule(ContextModule(this))

    override fun onCreate() {
        super.onCreate()
        _instance = this
        globalComponent = globalComponentBuilder().build()
    }
}
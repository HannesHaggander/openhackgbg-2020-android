package com.towerowl.openhackgbg2020.di

import android.content.Context
import com.towerowl.openhackgbg2020.models.AuthenticationViewModel
import com.towerowl.openhackgbg2020.repositories.AuthenticationRepository
import dagger.Component
import dagger.Module
import dagger.Provides

@Component(modules = [ViewModelsModule::class])
interface GlobalComponent {
    fun authenticationViewModel(): AuthenticationViewModel
}

@Module(includes = [ContextModule::class])
class RepositoriesModule {

    private val authenticationRepository by lazy { AuthenticationRepository() }

    @Provides
    fun provideAuthenticationRepository(): AuthenticationRepository = authenticationRepository

}

@Module
class ContextModule(private val ctx: Context) {
    @Provides
    fun provideContext(): Context = ctx
}

@Module(includes = [RepositoriesModule::class])
class ViewModelsModule() {
    private val authenticationViewModel by lazy { AuthenticationViewModel() }

    @Provides
    fun provideAuthenticationViewModel(): AuthenticationViewModel = authenticationViewModel
}
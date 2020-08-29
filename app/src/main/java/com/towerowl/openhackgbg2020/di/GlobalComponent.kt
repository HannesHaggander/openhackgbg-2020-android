package com.towerowl.openhackgbg2020.di

import android.content.Context
import com.towerowl.openhackgbg2020.data.AppDatabase
import com.towerowl.openhackgbg2020.data.AuthenticationDao
import com.towerowl.openhackgbg2020.models.AuthenticationViewModel
import com.towerowl.openhackgbg2020.models.CommunitiesViewModel
import com.towerowl.openhackgbg2020.repositories.ApiRepository
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Component(modules = [ViewModelsModule::class])
interface GlobalComponent {
    @Singleton
    fun authenticationViewModel(): AuthenticationViewModel

    @Singleton
    fun communitiesViewModel(): CommunitiesViewModel

    @Singleton
    fun apiCommunication(): ApiRepository
}

@Module(includes = [ContextModule::class])
class RepositoriesModule {

    private val apiRepository by lazy { ApiRepository() }

    @Provides
    fun provideApiRepository(): ApiRepository = apiRepository

    private var database: AppDatabase? = null

    private fun database(context: Context): AppDatabase = database
        ?: AppDatabase.create(context).also { database = it }

    @Provides
    fun provideAuthenticationDao(context: Context): AuthenticationDao =
        database(context).authenticationDao()
}

@Module
class ContextModule(private val ctx: Context) {
    @Provides
    fun provideContext(): Context = ctx
}

@Module(includes = [RepositoriesModule::class])
class ViewModelsModule {

    private var authenticationViewModel: AuthenticationViewModel? = null

    @Provides
    fun provideAuthenticationViewModel(
        apiRepository: ApiRepository,
        authenticationDao: AuthenticationDao
    ): AuthenticationViewModel = authenticationViewModel
        ?: AuthenticationViewModel(apiRepository, authenticationDao)
            .also { authenticationViewModel = it }

    private var communitiesViewModel: CommunitiesViewModel? = null

    @Provides
    fun provideCommunitiesViewModel(apiRepository: ApiRepository): CommunitiesViewModel =
        communitiesViewModel ?: CommunitiesViewModel(apiRepository)
            .also { communitiesViewModel = it }

}
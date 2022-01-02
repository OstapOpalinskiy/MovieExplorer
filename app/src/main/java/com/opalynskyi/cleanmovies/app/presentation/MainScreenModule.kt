package com.opalynskyi.cleanmovies.app.presentation

import android.content.SharedPreferences
import com.google.gson.Gson
import com.opalynskyi.cleanmovies.app.di.scopes.MainScreenScope
import com.opalynskyi.cleanmovies.app.SchedulerProvider
import com.opalynskyi.cleanmovies.app.data.LocalUserDataSource
import com.opalynskyi.cleanmovies.app.data.LocalUserDataSourceImpl
import com.opalynskyi.cleanmovies.app.data.RemoteUserDataSource
import com.opalynskyi.cleanmovies.app.data.UserRepositoryImpl
import com.opalynskyi.cleanmovies.app.domain.UserInteractor
import com.opalynskyi.cleanmovies.app.domain.UserInteractorImpl
import com.opalynskyi.cleanmovies.app.domain.UserRepository
import dagger.Module
import dagger.Provides


@Module
class MainScreenModule {

    @Provides
    @MainScreenScope
    fun provideMainScreenPresenter(
        userInteractor: UserInteractor,
        scheduler: SchedulerProvider
    ): MainScreenContract.Presenter =
        MainScreenPresenter(
            userInteractor,
            scheduler
        )

    @Provides
    fun provideUserInteractor(
        userRepository: UserRepository,
        scheduler: SchedulerProvider
    ): UserInteractor =
        UserInteractorImpl(userRepository, scheduler)

    @Provides
    fun provideUserRepository(
        localUserDataSource: LocalUserDataSource,
        remoteUserDataSource: RemoteUserDataSource
    ): UserRepository =
        UserRepositoryImpl(localUserDataSource, remoteUserDataSource)

    @Provides
    fun provideLocalUserDataStore(
        sharedPreferences: SharedPreferences,
        gson: Gson
    ): LocalUserDataSource = LocalUserDataSourceImpl(sharedPreferences, gson)

    @Provides
    fun provideRemoteUserDataStore(
    ): RemoteUserDataSource = RemoteUserDataSourceImpl()
}
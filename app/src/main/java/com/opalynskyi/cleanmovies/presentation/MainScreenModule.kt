package com.opalynskyi.cleanmovies.presentation

import android.content.SharedPreferences
import com.google.gson.Gson
import com.opalynskyi.cleanmovies.di.scopes.MainScreenScope
import com.opalynskyi.cleanmovies.SchedulerProvider
import com.opalynskyi.cleanmovies.data.user.LocalUserDataSource
import com.opalynskyi.cleanmovies.data.user.LocalUserDataSourceImpl
import com.opalynskyi.cleanmovies.data.user.RemoteUserDataSource
import com.opalynskyi.cleanmovies.data.user.UserRepositoryImpl
import com.opalynskyi.cleanmovies.domain.UserInteractor
import com.opalynskyi.cleanmovies.domain.UserInteractorImpl
import com.opalynskyi.cleanmovies.domain.UserRepository
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
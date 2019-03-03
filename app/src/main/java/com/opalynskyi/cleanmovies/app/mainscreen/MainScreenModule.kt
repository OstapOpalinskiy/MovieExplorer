package com.opalynskyi.cleanmovies.app.mainscreen

import android.content.SharedPreferences
import com.google.gson.Gson
import com.opalynskyi.cleanmovies.app.di.scopes.MainScreenScope
import com.opalynskyi.cleanmovies.app.mainscreen.datasource.LocalUserDataSourceImpl
import com.opalynskyi.cleanmovies.app.mainscreen.datasource.RemoteUserDataSourceImpl
import com.opalynskyi.cleanmovies.core.SchedulerProvider
import com.opalynskyi.cleanmovies.core.data.user.LocalUserDataSource
import com.opalynskyi.cleanmovies.core.data.user.RemoteUserDataSource
import com.opalynskyi.cleanmovies.core.data.user.UserRepositoryImpl
import com.opalynskyi.cleanmovies.core.domain.user.UserInteractor
import com.opalynskyi.cleanmovies.core.domain.user.UserInteractorImpl
import com.opalynskyi.cleanmovies.core.domain.user.UserRepository
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
    ): UserInteractor = UserInteractorImpl(userRepository, scheduler)

    @Provides
    fun provideUserRepository(
        localUserDataSource: LocalUserDataSource,
        remoteUserDataSource: RemoteUserDataSource
    ): UserRepository = UserRepositoryImpl(localUserDataSource, remoteUserDataSource)

    @Provides
    fun provideLocalUserDataStore(
        sharedPreferences: SharedPreferences,
        gson: Gson
    ): LocalUserDataSource = LocalUserDataSourceImpl(sharedPreferences, gson)

    @Provides
    fun provideRemoteUserDataStore(
    ): RemoteUserDataSource = RemoteUserDataSourceImpl()
}
package com.opalynskyi.cleanmovies.app.di.movies

import android.content.SharedPreferences
import com.google.gson.Gson
import com.opalynskyi.cleanmovies.app.user.datasource.LocalUserDataSourceImpl
import com.opalynskyi.cleanmovies.app.user.datasource.RemoteDataSourceImpl
import com.opalynskyi.cleanmovies.core.data.user.LocalUserDataSource
import com.opalynskyi.cleanmovies.core.data.user.RemoteUserDataSource
import com.opalynskyi.cleanmovies.core.data.user.UserRepositoryImpl
import com.opalynskyi.cleanmovies.core.domain.user.UserInteractor
import com.opalynskyi.cleanmovies.core.domain.user.UserInteractorImpl
import com.opalynskyi.cleanmovies.core.domain.user.UserRepository
import dagger.Module
import dagger.Provides


@Module
class UserModule {

    @Provides
    fun provideUserInteractor(userRepository: UserRepository): UserInteractor = UserInteractorImpl(userRepository)

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
    ): RemoteUserDataSource = RemoteDataSourceImpl()
}
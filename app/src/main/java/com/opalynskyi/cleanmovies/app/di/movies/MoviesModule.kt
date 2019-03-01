package com.opalynskyi.cleanmovies.app.di.movies

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.opalynskyi.cleanmovies.app.api.Constants
import com.opalynskyi.cleanmovies.app.api.MoviesApi
import com.opalynskyi.cleanmovies.app.api.RequestInterceptor
import com.opalynskyi.cleanmovies.app.di.scopes.MoviesActivityScope
import com.opalynskyi.cleanmovies.app.movies.MoviesContract
import com.opalynskyi.cleanmovies.app.movies.MoviesPresenter
import com.opalynskyi.cleanmovies.app.movies.datasource.RemoteMoviesDataSourceImpl
import com.opalynskyi.cleanmovies.app.movies.datasource.ResponseMoviesMapper
import com.opalynskyi.cleanmovies.core.data.movies.*
import com.opalynskyi.cleanmovies.core.domain.MoviesInteractorImpl
import com.opalynskyi.cleanmovies.core.domain.movies.MoviesInteractor
import com.opalynskyi.cleanmovies.core.domain.movies.MoviesRepository
import com.opalynskyi.cleanmovies.core.domain.user.UserInteractor
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.UnknownHostException


@Module
class MoviesModule {
    @MoviesActivityScope
    @Provides
    fun provideMoviesPresenter(
        userInteractor: UserInteractor,
        moviesInteractor: MoviesInteractor
    ): MoviesContract.Presenter =
        MoviesPresenter(userInteractor, moviesInteractor)


    @Provides
    @MoviesActivityScope
    fun provideLogRetrofit(
        baseUrl: HttpUrl,
        converterFactory: Converter.Factory,
        callAdapterFactory: CallAdapter.Factory,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(converterFactory)
        .addCallAdapterFactory(callAdapterFactory)
        .client(okHttpClient)
        .build()

    @Provides
    @MoviesActivityScope
    fun provideGsonConverterFactory(): Converter.Factory = GsonConverterFactory.create()

    @Provides
    @MoviesActivityScope
    fun provideRxJava2Adapter(): CallAdapter.Factory = RxJava2CallAdapterFactory.create()


    @Provides
    @MoviesActivityScope
    internal fun provideBaseUrl(): HttpUrl = HttpUrl.parse(Constants.BASE_URL)
        ?: throw UnknownHostException("Invalid host: " + Constants.BASE_URL)

    @Provides
    @MoviesActivityScope
    internal fun provideMoviesApi(retrofit: Retrofit): MoviesApi = retrofit.create(MoviesApi::class.java)

    @Provides
    @MoviesActivityScope
    internal fun provideHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        requestInterceptor: RequestInterceptor
    ): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.addInterceptor(requestInterceptor)
        httpClientBuilder.addInterceptor(loggingInterceptor)
        return httpClientBuilder.build()
    }

    @Provides
    @MoviesActivityScope
    internal fun provideLogHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.HEADERS
        return logging
    }

    @Provides
    @MoviesActivityScope
    internal fun provideRequestInterceptor(): RequestInterceptor = RequestInterceptor()


    @Provides
    @MoviesActivityScope
    internal fun provideMoviesInteractor(
        moviesRepository: MoviesRepository
    ): MoviesInteractor =
        MoviesInteractorImpl(moviesRepository)

    @Provides
    @MoviesActivityScope
    internal fun provideMoviesRepository(
        remoteMoviesDataSource: RemoteMoviesDataSource,
        localMoviesDataSource: LocalMoviesDataSource,
        moviesMapper: MoviesMapper
    ): MoviesRepository =
        MoviesRepositoryImpl(remoteMoviesDataSource, localMoviesDataSource, moviesMapper)

    @Provides
    @MoviesActivityScope
    internal fun provideRemoteMoviesDataSource(
        api: MoviesApi,
        mapper: ResponseMoviesMapper
    ): RemoteMoviesDataSource =
        RemoteMoviesDataSourceImpl(api, mapper)

    @Provides
    @MoviesActivityScope
    internal fun provideResponseMoviesMapper(): ResponseMoviesMapper = ResponseMoviesMapper()

    @Provides
    @MoviesActivityScope
    internal fun provideEntityMapper(): MoviesMapper = MoviesMapper()

    @Provides
    @MoviesActivityScope
    internal fun provideLocalMoviesDataSource(
    ): LocalMoviesDataSource {
        return LocalMoviesDataSourceImpl()
    }


}
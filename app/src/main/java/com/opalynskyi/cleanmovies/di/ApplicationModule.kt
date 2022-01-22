package com.opalynskyi.cleanmovies.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.google.gson.Gson
import com.opalynskyi.cleanmovies.data.*
import com.opalynskyi.cleanmovies.data.api.MoviesApi
import com.opalynskyi.cleanmovies.data.api.ServerMoviesMapper
import com.opalynskyi.cleanmovies.data.database.DbConstants
import com.opalynskyi.cleanmovies.data.database.MoviesDao
import com.opalynskyi.cleanmovies.data.database.MoviesDatabase
import com.opalynskyi.cleanmovies.data.paging.PagingSourceFactory
import com.opalynskyi.cleanmovies.di.scopes.ApplicationScope
import com.opalynskyi.cleanmovies.di.scopes.MainScreenScope
import com.opalynskyi.common.DispatcherProvider
import com.opalynskyi.movies_core.di.MoviesCoreComponentHolder
import com.opalynskyi.movies_core.di.MoviesCoreFeatureApi
import com.opalynskyi.movies_core.di.MoviesCoreFeatureDependencies
import com.opalynskyi.movies_core.domain.MoviesRepository
import com.opalynskyi.movies_core.domain.usecases.FavouritesUseCases
import com.opalynskyi.movies_core.domain.usecases.GetMoviesPagedUseCase
import com.opalynskyi.movies_list.MovieListMapper
import com.opalynskyi.utils.DateTimeHelper
import com.opalynskyi.utils.di.UtilsComponentHolder
import com.opalynskyi.utils.di.UtilsFeatureApi
import com.opalynskyi.utils.di.UtilsFeatureDependencies
import com.opalynskyi.utils.imageLoader.ImageLoader
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers


@Module
class ApplicationModule(private val context: Context) {

    @Provides
    @ApplicationScope
    fun provideContext() = context

    @Provides
    @ApplicationScope
    fun provideGson() = Gson()

    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(MOVIES_APP_PREFS, Context.MODE_PRIVATE)

    @Provides
    fun provideApi(): MoviesApi = MoviesApi.create()

    @Provides
    @ApplicationScope
    fun provideDatabase(context: Context): MoviesDatabase {
        return Room.databaseBuilder(
            context,
            MoviesDatabase::class.java, DbConstants.DB_NAME
        ).build()
    }

    @Provides
    fun provideMoviesDao(moviesDatabase: MoviesDatabase): MoviesDao =
        moviesDatabase.moviesDao()

    @Provides
    @ApplicationScope
    fun provideDateTimeHelper(utilsFeatureFeatureApi: UtilsFeatureApi): DateTimeHelper {
        return utilsFeatureFeatureApi.dateTimeHelper()
    }

    @Provides
    @ApplicationScope
    fun provideMoviesRepository(
        localMoviesDataSource: LocalMoviesDataSource,
        pagingSource: PagingSourceFactory,
        moviesMapper: DbMoviesMapper
    ): MoviesRepository =
        MoviesRepositoryImpl(
            localMoviesDataSource,
            pagingSource,
            moviesMapper
        )

    @Provides
    @ApplicationScope
    fun provideRemoteMoviesDataSource(
        api: MoviesApi,
        mapper: ServerMoviesMapper
    ): RemoteMoviesDataSource =
        RemoteMoviesDataSourceImpl(api, mapper)

    @Provides
    @ApplicationScope
    fun provideResponseMoviesMapper(dateTimeHelper: DateTimeHelper): ServerMoviesMapper =
        ServerMoviesMapper(dateTimeHelper)

    @Provides
    @ApplicationScope
    fun provideLocalMoviesDataSource(
        dao: MoviesDao,
        mapper: DbMoviesMapper
    ): LocalMoviesDataSource {
        return LocalMoviesDataSourceImpl(dao, mapper)
    }

    @Provides
    @ApplicationScope
    fun provideDbMoviesMapper(): DbMoviesMapper = DbMoviesMapper()

    @Provides
    @ApplicationScope
    fun provideMovieListMapper(dateTimeHelper: DateTimeHelper): MovieListMapper =
        MovieListMapper(dateTimeHelper)

    @Provides
    fun provideDispatchersProvider(): DispatcherProvider {
        return object : DispatcherProvider {
            override fun default() = Dispatchers.Default
            override fun io() = Dispatchers.IO
            override fun main() = Dispatchers.Main
            override fun unconfined() = Dispatchers.Unconfined
        }
    }

    @Provides
    fun provideUtilsFeatureApi(): UtilsFeatureApi {
        UtilsComponentHolder.init(object : UtilsFeatureDependencies {})
        return UtilsComponentHolder.get()
    }

    @Provides
    fun provideImageLoader(utilsFeatureFeatureApi: UtilsFeatureApi): ImageLoader {
        return utilsFeatureFeatureApi.imageLoader()
    }

    @Provides
    fun provideMoviesCoreFeatureApi(
        moviesRepository: MoviesRepository,
        dispatcherProvider: DispatcherProvider
    ): MoviesCoreFeatureApi {
        MoviesCoreComponentHolder.init(object : MoviesCoreFeatureDependencies {
            override fun moviesRepository() = moviesRepository

            override fun dispatchersProvider() = dispatcherProvider

        })
        return MoviesCoreComponentHolder.get()
    }

    @Provides
    fun provideFavouritesUseCases(moviesCoreFeatureApi: MoviesCoreFeatureApi): FavouritesUseCases {
        return moviesCoreFeatureApi.favouriteUseCases()
    }

    @Provides
    fun provideGetPagedMoviesUseCase(moviesCoreFeatureApi: MoviesCoreFeatureApi): GetMoviesPagedUseCase {
        return moviesCoreFeatureApi.getPagedMoviesUseCase()
    }

    companion object {
        private const val MOVIES_APP_PREFS = "movies_app_prefs"
    }
}
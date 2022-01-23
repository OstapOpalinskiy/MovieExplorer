package com.opalynskyi.cleanmovies.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.opalynskyi.cleanmovies.data.DbMoviesMapper
import com.opalynskyi.cleanmovies.data.LocalMoviesDataSource
import com.opalynskyi.cleanmovies.data.LocalMoviesDataSourceImpl
import com.opalynskyi.cleanmovies.data.MoviesRepositoryImpl
import com.opalynskyi.cleanmovies.data.database.DbConstants
import com.opalynskyi.cleanmovies.data.database.MoviesDao
import com.opalynskyi.cleanmovies.data.database.MoviesDatabase
import com.opalynskyi.cleanmovies.di.scopes.ApplicationScope
import com.opalynskyi.common.DispatcherProvider
import com.opalynskyi.movies_core.di.MoviesCoreComponentHolder
import com.opalynskyi.movies_core.di.MoviesCoreFeatureApi
import com.opalynskyi.movies_core.di.MoviesCoreFeatureDependencies
import com.opalynskyi.movies_core.domain.MoviesRepository
import com.opalynskyi.movies_core.domain.usecases.FavouritesUseCases
import com.opalynskyi.movies_list.MovieListMapper
import com.opalynskyi.movies_popular.MoviesPopularFeatureStarter
import com.opalynskyi.movies_popular.api.MoviesPopularFeatureApi
import com.opalynskyi.movies_popular.api.MoviesPopularFeatureDependencies
import com.opalynskyi.movies_popular.di.MoviesPopularFeatureComponentHolder
import com.opalynskyi.network.api.MoviesApi
import com.opalynskyi.network.api.ServerMoviesMapper
import com.opalynskyi.utils.DateTimeHelper
import com.opalynskyi.utils.api.UtilsFeatureApi
import com.opalynskyi.utils.api.UtilsFeatureDependencies
import com.opalynskyi.utils.di.UtilsComponentHolder
import com.opalynskyi.utils.imageLoader.ImageLoader
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

@Module
class ApplicationModule(private val context: Context) {

    @Provides
    @ApplicationScope
    fun provideContext() = context

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
        moviesMapper: DbMoviesMapper
    ): MoviesRepository =
        MoviesRepositoryImpl(
            localMoviesDataSource,
            moviesMapper
        )

    @Provides
    fun provideResponseMoviesMapper(dateTimeHelper: DateTimeHelper): ServerMoviesMapper =
        ServerMoviesMapper(dateTimeHelper)

    @Provides
    fun provideLocalMoviesDataSource(
        dao: MoviesDao,
        mapper: DbMoviesMapper
    ): LocalMoviesDataSource {
        return LocalMoviesDataSourceImpl(dao, mapper)
    }

    @Provides
    fun provideDbMoviesMapper(): DbMoviesMapper = DbMoviesMapper()

    @Provides
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
    fun provideMoviesPopularFeatureApi(
        imageLoader: ImageLoader,
        favouritesUseCases: FavouritesUseCases,
        movieListMapper: MovieListMapper,
        moviesApi: MoviesApi,
        serverMoviesMapper: ServerMoviesMapper
    ): MoviesPopularFeatureApi {
        MoviesPopularFeatureComponentHolder.init(object : MoviesPopularFeatureDependencies {
            override fun imageLoader() = imageLoader

            override fun favouritesUseCases() = favouritesUseCases

            override fun movieListMapper() = movieListMapper

            override fun moviesApi() = moviesApi

            override fun serverMoviesMapper() = serverMoviesMapper
        })
        return MoviesPopularFeatureComponentHolder.get()
    }

    @Provides
    fun providesMoviesPopularFeatureStarter(popularFeatureApi: MoviesPopularFeatureApi): MoviesPopularFeatureStarter {
        return popularFeatureApi.starter()
    }

    companion object {
        private const val MOVIES_APP_PREFS = "movies_app_prefs"
    }
}
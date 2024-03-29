package com.opalynskyi.movie_explorer.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.com.opalynskyi.favourites.api.MoviesFavouriteFeatureApi
import com.com.opalynskyi.favourites.api.MoviesFavouriteFeatureDependencies
import com.com.opalynskyi.favourites.di.MoviesFavouriteFeatureComponentHolder
import com.opalynskyi.movie_explorer.di.scopes.ApplicationScope
import com.opalynskyi.common.DispatcherProvider
import com.opalynskyi.db.DbConstants
import com.opalynskyi.db.MoviesDatabase
import com.opalynskyi.movies_core.api.MoviesCoreFeatureApi
import com.opalynskyi.movies_core.api.MoviesCoreFeatureDependencies
import com.opalynskyi.movies_core.di.MoviesCoreComponentHolder
import com.opalynskyi.movies_core.domain.usecases.FavouritesUseCases
import com.opalynskyi.movies_list.MovieListBuilder
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
    fun provideDateTimeHelper(utilsFeatureFeatureApi: UtilsFeatureApi): DateTimeHelper {
        return utilsFeatureFeatureApi.dateTimeHelper()
    }

    @Provides
    @ApplicationScope
    fun provideDatabase(context: Context): MoviesDatabase {
        return Room.databaseBuilder(
            context,
            MoviesDatabase::class.java, DbConstants.DB_NAME
        ).build()
    }

    @Provides
    fun provideResponseMoviesMapper(dateTimeHelper: DateTimeHelper): ServerMoviesMapper =
        ServerMoviesMapper(dateTimeHelper)


    @Provides
    fun provideMovieListMapper(dateTimeHelper: DateTimeHelper): MovieListBuilder =
        MovieListBuilder(dateTimeHelper)

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
        dispatcherProvider: DispatcherProvider,
        moviesDatabase: MoviesDatabase
    ): MoviesCoreFeatureApi {
        MoviesCoreComponentHolder.init(object : MoviesCoreFeatureDependencies {
            override fun dispatchersProvider() = dispatcherProvider
            override fun moviesDb() = moviesDatabase
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
        movieListBuilder: MovieListBuilder,
        moviesApi: MoviesApi,
        serverMoviesMapper: ServerMoviesMapper
    ): MoviesPopularFeatureApi {
        MoviesPopularFeatureComponentHolder.init(object : MoviesPopularFeatureDependencies {
            override fun imageLoader() = imageLoader

            override fun favouritesUseCases() = favouritesUseCases

            override fun movieListMapper() = movieListBuilder

            override fun moviesApi() = moviesApi

            override fun serverMoviesMapper() = serverMoviesMapper
        })
        return MoviesPopularFeatureComponentHolder.get()
    }

    @Provides
    fun providesMoviesPopularFeatureStarter(popularFeatureApi: MoviesPopularFeatureApi): MoviesPopularFeatureStarter {
        return popularFeatureApi.starter()
    }

    @Provides
    fun provideMoviesFavouriteFeatureApi(
        imageLoader: ImageLoader,
        favouritesUseCases: FavouritesUseCases,
        movieListBuilder: MovieListBuilder,
    ): MoviesFavouriteFeatureApi {
        MoviesFavouriteFeatureComponentHolder.init(object : MoviesFavouriteFeatureDependencies {
            override fun imageLoader(): ImageLoader = imageLoader

            override fun favouritesUseCases() = favouritesUseCases

            override fun movieListMapper() = movieListBuilder
        })
        return MoviesFavouriteFeatureComponentHolder.get()
    }

    companion object {
        private const val MOVIES_APP_PREFS = "movies_app_prefs"
    }
}
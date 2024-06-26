package com.opalynskyi.movieexplorer.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.com.opalynskyi.favourites.api.MoviesFavouriteFeatureApi
import com.com.opalynskyi.favourites.api.MoviesFavouriteFeatureDependencies
import com.com.opalynskyi.favourites.di.MoviesFavouriteFeatureComponentHolder
import com.opalynskyi.common.DispatcherProvider
import com.opalynskyi.core.api.MoviesCoreFeatureApi
import com.opalynskyi.core.api.MoviesCoreFeatureDependencies
import com.opalynskyi.core.di.MoviesCoreComponentHolder
import com.opalynskyi.core.domain.usecases.FavouritesUseCases
import com.opalynskyi.db.DbConstants
import com.opalynskyi.db.MoviesDatabase
import com.opalynskyi.movieexplorer.di.scopes.ApplicationScope
import com.opalynskyi.movies.MovieListBuilder
import com.opalynskyi.network.api.MoviesApi
import com.opalynskyi.network.api.ServerMoviesMapper
import com.opalynskyi.popular.MoviesPopularFeatureStarter
import com.opalynskyi.popular.api.MoviesPopularFeatureApi
import com.opalynskyi.popular.api.MoviesPopularFeatureDependencies
import com.opalynskyi.popular.di.MoviesPopularFeatureComponentHolder
import com.opalynskyi.utils.DateTimeHelper
import com.opalynskyi.utils.api.UtilsFeatureApi
import com.opalynskyi.utils.api.UtilsFeatureDependencies
import com.opalynskyi.utils.di.UtilsComponentHolder
import com.opalynskyi.utils.imageLoader.ImageLoader
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Suppress("TooManyFunctions")
@Module
class ApplicationModule(
    private val context: Context,
) {
    @Provides
    @ApplicationScope
    fun provideContext() = context

    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(
            MOVIES_APP_PREFS,
            Context.MODE_PRIVATE,
        )

    @Provides
    fun provideApi(): MoviesApi = MoviesApi.create()

    @Provides
    @ApplicationScope
    fun provideDateTimeHelper(utilsFeatureFeatureApi: UtilsFeatureApi): DateTimeHelper = utilsFeatureFeatureApi.dateTimeHelper()

    @Provides
    @ApplicationScope
    fun provideDatabase(context: Context): MoviesDatabase =
        Room
            .databaseBuilder(
                context,
                MoviesDatabase::class.java,
                DbConstants.DB_NAME,
            ).build()

    @Provides
    fun provideResponseMoviesMapper(dateTimeHelper: DateTimeHelper): ServerMoviesMapper = ServerMoviesMapper(dateTimeHelper)

    @Provides
    fun provideMovieListMapper(dateTimeHelper: DateTimeHelper): MovieListBuilder = MovieListBuilder(dateTimeHelper)

    @Provides
    fun provideDispatchersProvider(): DispatcherProvider =
        object : DispatcherProvider {
            override fun default() = Dispatchers.Default

            override fun io() = Dispatchers.IO

            override fun main() = Dispatchers.Main

            override fun unconfined() = Dispatchers.Unconfined
        }

    @Provides
    fun provideUtilsFeatureApi(): UtilsFeatureApi {
        UtilsComponentHolder.init(object : UtilsFeatureDependencies {})
        return UtilsComponentHolder.get()
    }

    @Provides
    fun provideImageLoader(utilsFeatureFeatureApi: UtilsFeatureApi): ImageLoader = utilsFeatureFeatureApi.imageLoader()

    @Provides
    fun provideMoviesCoreFeatureApi(
        dispatcherProvider: DispatcherProvider,
        moviesDatabase: MoviesDatabase,
    ): MoviesCoreFeatureApi {
        MoviesCoreComponentHolder.init(
            object : MoviesCoreFeatureDependencies {
                override fun dispatchersProvider() = dispatcherProvider

                override fun moviesDb() = moviesDatabase
            },
        )
        return MoviesCoreComponentHolder.get()
    }

    @Provides
    fun provideFavouritesUseCases(moviesCoreFeatureApi: MoviesCoreFeatureApi): FavouritesUseCases = moviesCoreFeatureApi.favouriteUseCases()

    @Provides
    fun provideMoviesPopularFeatureApi(
        imageLoader: ImageLoader,
        favouritesUseCases: FavouritesUseCases,
        movieListBuilder: MovieListBuilder,
        moviesApi: MoviesApi,
        serverMoviesMapper: ServerMoviesMapper,
    ): MoviesPopularFeatureApi {
        MoviesPopularFeatureComponentHolder.init(
            object : MoviesPopularFeatureDependencies {
                override fun imageLoader() = imageLoader

                override fun favouritesUseCases() = favouritesUseCases

                override fun movieListMapper() = movieListBuilder

                override fun moviesApi() = moviesApi

                override fun serverMoviesMapper() = serverMoviesMapper
            },
        )
        return MoviesPopularFeatureComponentHolder.get()
    }

    @Provides
    fun providesMoviesPopularFeatureStarter(popularFeatureApi: MoviesPopularFeatureApi): MoviesPopularFeatureStarter = popularFeatureApi.starter()

    @Provides
    fun provideMoviesFavouriteFeatureApi(
        imageLoader: ImageLoader,
        favouritesUseCases: FavouritesUseCases,
        movieListBuilder: MovieListBuilder,
    ): MoviesFavouriteFeatureApi {
        MoviesFavouriteFeatureComponentHolder.init(
            object : MoviesFavouriteFeatureDependencies {
                override fun imageLoader(): ImageLoader = imageLoader

                override fun favouritesUseCases() = favouritesUseCases

                override fun movieListMapper() = movieListBuilder
            },
        )
        return MoviesFavouriteFeatureComponentHolder.get()
    }

    companion object {
        private const val MOVIES_APP_PREFS = "movies_app_prefs"
    }
}

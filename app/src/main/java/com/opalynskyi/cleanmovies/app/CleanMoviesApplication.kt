package com.opalynskyi.cleanmovies.app

import android.app.Application
import com.facebook.stetho.Stetho
import com.opalynskyi.cleanmovies.app.di.ApplicationComponent
import com.opalynskyi.cleanmovies.app.di.ApplicationModule
import com.opalynskyi.cleanmovies.app.di.DaggerApplicationComponent
import com.opalynskyi.cleanmovies.app.login.LoginActivity
import com.opalynskyi.cleanmovies.app.login.LoginComponent
import com.opalynskyi.cleanmovies.app.login.LoginModule
import com.opalynskyi.cleanmovies.app.mainscreen.MainScreenComponent
import com.opalynskyi.cleanmovies.app.mainscreen.MainScreenModule
import com.opalynskyi.cleanmovies.app.mainscreen.movies.MoviesModule
import com.opalynskyi.cleanmovies.app.mainscreen.movies.all.AllMoviesComponent
import com.opalynskyi.cleanmovies.app.mainscreen.movies.all.AllMoviesModule
import com.opalynskyi.cleanmovies.app.mainscreen.movies.favourite.FavouriteMoviesComponent
import com.opalynskyi.cleanmovies.app.mainscreen.movies.favourite.FavouriteMoviesModule
import timber.log.Timber
import java.util.*

class CleanMoviesApplication : Application() {

    private val component: ApplicationComponent by lazy {
        DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    private var loginComponent: LoginComponent? = null
    private var mainScreenComponent: MainScreenComponent? = null
    private var allMoviesComponent: AllMoviesComponent? = null
    private var favouriteMoviesComponent: FavouriteMoviesComponent? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        component.inject(instance)
        setupTimber()
        Stetho.initializeWithDefaults(this)
    }

    private fun setupTimber() {
        Timber.plant(object : Timber.DebugTree() {
            override fun createStackElementTag(element: StackTraceElement): String {
                // adding file name and line number link to logs
                return String.format(
                    Locale.US,
                    "%s(%s:%d)",
                    super.createStackElementTag(element),
                    element.fileName,
                    element.lineNumber
                )
            }
        })
    }

    fun getLoginComponent(activity: LoginActivity): LoginComponent {
        if (loginComponent == null) {
            loginComponent = component.createLoginComponent(
                LoginModule(
                    activity
                )
            )
        }
        return loginComponent!!
    }

    fun releaseLoginComponent() {
        loginComponent = null
    }

    fun getMainScreenComponent(): MainScreenComponent {
        if (mainScreenComponent == null) {
            mainScreenComponent = component.createMainScreenComponent(
                MainScreenModule()
            )
        }
        return mainScreenComponent!!
    }

    fun releaseMainScreenComponent() {
        mainScreenComponent = null
    }

    fun getAllMoviesComponent(): AllMoviesComponent {
        if (allMoviesComponent == null) {
            allMoviesComponent = component.createAllMoviesComponent(AllMoviesModule(), MoviesModule())
        }
        return allMoviesComponent!!
    }

    fun releaseAllMoviesComponent() {
        allMoviesComponent = null
    }

    fun getFavouriteMoviesComponent(): FavouriteMoviesComponent {
        if (favouriteMoviesComponent == null) {
            favouriteMoviesComponent = component.createFavouriteMoviesComponent(FavouriteMoviesModule(), MoviesModule())
        }
        return favouriteMoviesComponent!!
    }

    fun releaseFavouriteMoviesComponent() {
        favouriteMoviesComponent = null
    }


    companion object {
        lateinit var instance: CleanMoviesApplication
            private set
    }
}
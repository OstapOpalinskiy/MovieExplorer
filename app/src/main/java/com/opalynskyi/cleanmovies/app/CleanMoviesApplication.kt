package com.opalynskyi.cleanmovies.app

import android.app.Application
import com.facebook.stetho.Stetho
import com.opalynskyi.cleanmovies.app.di.ApplicationComponent
import com.opalynskyi.cleanmovies.app.di.ApplicationModule
import com.opalynskyi.cleanmovies.app.di.DaggerApplicationComponent
import com.opalynskyi.cleanmovies.app.di.MoviesScreenComponent
import com.opalynskyi.cleanmovies.app.di.login.LoginComponent
import com.opalynskyi.cleanmovies.app.di.login.LoginModule
import com.opalynskyi.cleanmovies.app.di.movies.MoviesModule
import com.opalynskyi.cleanmovies.app.di.movies.UserModule
import com.opalynskyi.cleanmovies.app.login.LoginActivity
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
    private var moviesComponent: MoviesScreenComponent? = null

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

    fun getMoviesComponent(): MoviesScreenComponent {
        if (moviesComponent == null) {
            moviesComponent = component.createMoviesScreenComponent(
                UserModule(),
                MoviesModule()
            )
        }
        return moviesComponent!!
    }

    fun releaseMoviesComponent() {
        moviesComponent = null
    }


    companion object {
        lateinit var instance: CleanMoviesApplication
            private set
    }
}
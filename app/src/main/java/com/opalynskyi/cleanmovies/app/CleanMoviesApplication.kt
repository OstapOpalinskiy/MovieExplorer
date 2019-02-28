package com.opalynskyi.cleanmovies.app

import android.app.Application
import com.opalynskyi.cleanmovies.app.injection.ApplicationComponent
import com.opalynskyi.cleanmovies.app.injection.ApplicationModule
import com.opalynskyi.cleanmovies.app.injection.DaggerApplicationComponent
import com.opalynskyi.cleanmovies.app.injection.MoviesScreenComponent
import com.opalynskyi.cleanmovies.app.injection.login.LoginComponent
import com.opalynskyi.cleanmovies.app.injection.login.LoginModule
import com.opalynskyi.cleanmovies.app.injection.movies.MoviesModule
import com.opalynskyi.cleanmovies.app.injection.movies.UserModule
import com.opalynskyi.cleanmovies.app.login.LoginActivity

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
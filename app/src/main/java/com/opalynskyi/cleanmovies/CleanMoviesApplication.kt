package com.opalynskyi.cleanmovies

import android.app.Application
import androidx.navigation.NavController
import com.opalynskyi.cleanmovies.di.ApplicationComponent
import com.opalynskyi.cleanmovies.di.ApplicationModule
import com.opalynskyi.cleanmovies.di.DaggerApplicationComponent
import com.opalynskyi.cleanmovies.presentation.MainScreenComponent
import com.opalynskyi.cleanmovies.presentation.MainScreenModule
import com.opalynskyi.cleanmovies.presentation.movies.MoviesComponent
import com.opalynskyi.cleanmovies.presentation.movies.MoviesModule
import timber.log.Timber
import java.util.*

class CleanMoviesApplication : Application() {

    private val component: ApplicationComponent by lazy {
        DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    private var mainScreenComponent: MainScreenComponent? = null
    private var moviesComponent: MoviesComponent? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        component.inject(instance)
        setupTimber()
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

    fun getMainScreenComponent(navController: NavController): MainScreenComponent {
        if (mainScreenComponent == null) {
            mainScreenComponent =
                component.createMainScreenComponent(MainScreenModule(navController))
        }
        return mainScreenComponent!!
    }

    fun releaseMainScreenComponent() {
        mainScreenComponent = null
    }

    fun getMoviesComponent(): MoviesComponent {
        if (moviesComponent == null) {
            moviesComponent = component.createMoviesComponent(MoviesModule())
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
package com.opalynskyi.cleanmovies

import android.app.Application
import androidx.navigation.NavController
import com.opalynskyi.cleanmovies.di.ApplicationComponent
import com.opalynskyi.cleanmovies.di.ApplicationModule
import com.opalynskyi.cleanmovies.di.DaggerApplicationComponent
import com.opalynskyi.cleanmovies.presentation.MainScreenComponent
import com.opalynskyi.cleanmovies.presentation.MainScreenModule
import timber.log.Timber

class CleanMoviesApplication : Application() {

    private val component: ApplicationComponent by lazy {
        DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    private var mainScreenComponent: MainScreenComponent? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        component.inject(instance)
        Timber.plant()
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

    companion object {
        lateinit var instance: CleanMoviesApplication
            private set
    }
}
package com.opalynskyi.cleanmovies.app

import android.app.Application
import com.opalynskyi.cleanmovies.app.injection.*

class CleanMoviesApplication : Application() {

    private val component: ApplicationComponent by lazy {
        DaggerApplicationComponent
            .builder()
            .androidModule(AndroidModule(this))
            .build()
    }

    private var loginComponent: LoginSubComponent? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        component.inject(instance)
    }

    fun getLoginComponent(activity: LoginActivity): LoginSubComponent {
        if (loginComponent == null) {
            loginComponent = component.createLoginSubComponent(LoginModule(activity))
        }
        return loginComponent!!
    }

    fun releaseLoginComponent() {
        loginComponent = null
    }


    companion object {
        lateinit var instance: CleanMoviesApplication
            private set
    }
}
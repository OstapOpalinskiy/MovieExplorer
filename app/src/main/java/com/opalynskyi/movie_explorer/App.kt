package com.opalynskyi.movie_explorer

import android.app.Application
import com.opalynskyi.movie_explorer.di.AppComponent
import com.opalynskyi.movie_explorer.di.ApplicationModule
import com.opalynskyi.movie_explorer.di.DaggerAppComponent
import timber.log.Timber
import java.util.*

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppComponent.init(
            DaggerAppComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()
        )
        setupTimber()
    }

    private fun setupTimber() {
        Timber.plant(object : Timber.DebugTree() {
            override fun createStackElementTag(element: StackTraceElement): String {
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
}
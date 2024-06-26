package com.opalynskyi.movieexplorer

import android.app.Application
import com.opalynskyi.movieexplorer.di.AppComponent
import com.opalynskyi.movieexplorer.di.ApplicationModule
import com.opalynskyi.movieexplorer.di.DaggerAppComponent
import timber.log.Timber
import java.util.*

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppComponent.init(
            DaggerAppComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build(),
        )
        setupTimber()
    }

    private fun setupTimber() {
        Timber.plant(
            object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String {
                    return String.format(
                        Locale.US,
                        "%s(%s:%d)",
                        super.createStackElementTag(element),
                        element.fileName,
                        element.lineNumber,
                    )
                }
            },
        )
    }
}

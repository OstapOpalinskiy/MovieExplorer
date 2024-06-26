package com.opalynskyi.movieexplorer.di

import com.opalynskyi.movieexplorer.App
import com.opalynskyi.movieexplorer.di.scopes.ApplicationScope
import com.opalynskyi.movieexplorer.presentation.MainScreenComponent
import com.opalynskyi.movieexplorer.presentation.MainScreenModule
import dagger.Component
import dagger.internal.Preconditions

@ApplicationScope
@Component(
    modules = [
        ApplicationModule::class,
    ],
)
abstract class AppComponent {
    abstract fun inject(application: App)

    abstract fun mainScreenComponent(module: MainScreenModule): MainScreenComponent

    companion object {
        private var instance: AppComponent? = null

        fun get(): AppComponent {
            return Preconditions.checkNotNull(
                instance,
                "AppComponent is not initialized yet. Call init first.",
            )!!
        }

        fun init(component: AppComponent) {
            require(instance == null) { "AppComponent is already initialized." }
            instance = component
        }
    }
}

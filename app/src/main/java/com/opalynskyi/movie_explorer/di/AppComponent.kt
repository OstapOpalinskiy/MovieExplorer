package com.opalynskyi.movie_explorer.di

import com.opalynskyi.movie_explorer.App
import com.opalynskyi.movie_explorer.di.scopes.ApplicationScope
import com.opalynskyi.movie_explorer.presentation.MainScreenComponent
import com.opalynskyi.movie_explorer.presentation.MainScreenModule
import dagger.Component
import dagger.internal.Preconditions

@ApplicationScope
@Component(
    modules = [
        ApplicationModule::class
    ]
)
abstract class AppComponent {

    abstract fun inject(application: App)

    abstract fun mainScreenComponent(module: MainScreenModule): MainScreenComponent

    companion object {
        private var instance: AppComponent? = null

        fun get(): AppComponent {
            return Preconditions.checkNotNull(
                instance,
                "AppComponent is not initialized yet. Call init first."
            )!!
        }

        fun init(component: AppComponent) {
            require(instance == null) { "AppComponent is already initialized." }
            instance = component
        }
    }
}
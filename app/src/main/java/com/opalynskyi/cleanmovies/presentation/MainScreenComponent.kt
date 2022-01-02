package com.opalynskyi.cleanmovies.presentation

import com.opalynskyi.cleanmovies.di.scopes.MainScreenScope
import dagger.Subcomponent

@MainScreenScope
@Subcomponent(
    modules = [
        MainScreenModule::class
    ]
)
interface MainScreenComponent {
    fun inject(activity: MainActivity)
}
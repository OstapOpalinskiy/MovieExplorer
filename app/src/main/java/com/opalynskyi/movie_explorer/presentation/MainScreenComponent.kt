package com.opalynskyi.movie_explorer.presentation

import com.opalynskyi.movie_explorer.di.scopes.MainScreenScope
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
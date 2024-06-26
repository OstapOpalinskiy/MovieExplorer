package com.opalynskyi.movieexplorer.presentation

import com.opalynskyi.movieexplorer.di.scopes.MainScreenScope
import dagger.Subcomponent

@MainScreenScope
@Subcomponent(
    modules = [
        MainScreenModule::class,
    ],
)
interface MainScreenComponent {
    fun inject(activity: MainActivity)
}

package com.opalynskyi.cleanmovies.app.mainscreen

import com.opalynskyi.cleanmovies.app.di.scopes.MainScreenScope
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
package com.opalynskyi.movie_explorer.presentation

import androidx.navigation.NavController
import dagger.Module
import dagger.Provides


@Module
class MainScreenModule(private val navController: NavController) {

    @Provides
    fun provideNavController() = navController
}
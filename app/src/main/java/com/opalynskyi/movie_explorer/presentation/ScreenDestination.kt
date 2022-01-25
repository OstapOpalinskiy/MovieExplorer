package com.opalynskyi.movie_explorer.presentation

sealed class ScreenDestination {
    object Popular : ScreenDestination()
    object Favourite : ScreenDestination()
}
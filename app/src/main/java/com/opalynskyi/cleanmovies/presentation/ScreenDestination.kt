package com.opalynskyi.cleanmovies.presentation

sealed class ScreenDestination {
    object Popular : ScreenDestination()
    object Favourite : ScreenDestination()
}
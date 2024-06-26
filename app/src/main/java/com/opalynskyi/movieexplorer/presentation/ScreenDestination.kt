package com.opalynskyi.movieexplorer.presentation

sealed class ScreenDestination {
    object Popular : ScreenDestination()

    object Favourite : ScreenDestination()
}

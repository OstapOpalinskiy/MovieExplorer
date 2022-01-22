package com.opalynskyi.movies_core.domain.usecases

class FavouritesUseCases(
    val addToFavouritesUseCase: AddToFavouritesUseCase,
    val removeFromFavouritesUseCase: RemoveFromFavouritesUseCase,
    val observeFavouritesUseCase: ObserveFavouriteMoviesUseCase,
)
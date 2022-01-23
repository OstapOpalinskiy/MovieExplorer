package com.opalynskyi.movies_core.api

import com.opalynskyi.module_injector.BaseAPI
import com.opalynskyi.movies_core.domain.usecases.FavouritesUseCases

interface MoviesCoreFeatureApi : BaseAPI {
    fun favouriteUseCases(): FavouritesUseCases
}
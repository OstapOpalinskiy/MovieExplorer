package com.opalynskyi.core.api

import com.opalynskyi.core.domain.usecases.FavouritesUseCases
import com.opalynskyi.injector.BaseAPI

interface MoviesCoreFeatureApi : BaseAPI {
    fun favouriteUseCases(): FavouritesUseCases
}

package com.com.opalynskyi.favourites.api

import com.com.opalynskyi.favourites.MoviesFavouriteFeatureStarter
import com.opalynskyi.module_injector.BaseAPI

interface MoviesFavouriteFeatureApi : BaseAPI {
    fun starter(): MoviesFavouriteFeatureStarter
}
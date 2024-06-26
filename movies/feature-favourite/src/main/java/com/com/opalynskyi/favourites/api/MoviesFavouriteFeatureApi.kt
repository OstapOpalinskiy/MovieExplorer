package com.com.opalynskyi.favourites.api

import com.com.opalynskyi.favourites.MoviesFavouriteFeatureStarter
import com.opalynskyi.injector.BaseAPI

interface MoviesFavouriteFeatureApi : BaseAPI {
    fun starter(): MoviesFavouriteFeatureStarter
}

package com.opalynskyi.popular.api

import com.opalynskyi.injector.BaseAPI
import com.opalynskyi.popular.MoviesPopularFeatureStarter

interface MoviesPopularFeatureApi : BaseAPI {
    fun starter(): MoviesPopularFeatureStarter
}

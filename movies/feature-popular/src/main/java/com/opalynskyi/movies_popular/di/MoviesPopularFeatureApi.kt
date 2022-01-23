package com.opalynskyi.movies_popular.di

import com.opalynskyi.module_injector.BaseAPI
import com.opalynskyi.movies_popular.MoviesPopularFeatureStarter

interface MoviesPopularFeatureApi : BaseAPI {
    fun starter(): MoviesPopularFeatureStarter
}
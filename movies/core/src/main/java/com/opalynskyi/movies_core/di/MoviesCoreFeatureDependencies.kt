package com.opalynskyi.movies_core.di

import com.opalynskyi.common.DispatcherProvider
import com.opalynskyi.module_injector.BaseDependencies
import com.opalynskyi.movies_core.domain.MoviesRepository

interface MoviesCoreFeatureDependencies: BaseDependencies {
    fun moviesRepository(): MoviesRepository
    fun dispatchersProvider(): DispatcherProvider
}
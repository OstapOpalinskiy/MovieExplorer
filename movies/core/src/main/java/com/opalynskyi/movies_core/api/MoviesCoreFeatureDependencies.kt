package com.opalynskyi.movies_core.api

import com.opalynskyi.common.DispatcherProvider
import com.opalynskyi.db.MoviesDatabase
import com.opalynskyi.module_injector.BaseDependencies

interface MoviesCoreFeatureDependencies : BaseDependencies {
    fun dispatchersProvider(): DispatcherProvider
    fun moviesDb(): MoviesDatabase
}
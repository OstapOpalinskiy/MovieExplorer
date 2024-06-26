package com.opalynskyi.core.api

import com.opalynskyi.common.DispatcherProvider
import com.opalynskyi.db.MoviesDatabase
import com.opalynskyi.injector.BaseDependencies

interface MoviesCoreFeatureDependencies : BaseDependencies {
    fun dispatchersProvider(): DispatcherProvider

    fun moviesDb(): MoviesDatabase
}

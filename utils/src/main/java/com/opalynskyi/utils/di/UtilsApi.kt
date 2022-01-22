package com.opalynskyi.utils.di

import com.gal.module_injector.BaseAPI
import com.opalynskyi.utils.imageLoader.ImageLoader

interface UtilsApi : BaseAPI {
    fun imageLoader(): ImageLoader
}
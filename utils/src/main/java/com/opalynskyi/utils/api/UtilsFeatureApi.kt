package com.opalynskyi.utils.api

import com.opalynskyi.injector.BaseAPI
import com.opalynskyi.utils.DateTimeHelper
import com.opalynskyi.utils.imageLoader.ImageLoader

interface UtilsFeatureApi : BaseAPI {
    fun imageLoader(): ImageLoader

    fun dateTimeHelper(): DateTimeHelper
}

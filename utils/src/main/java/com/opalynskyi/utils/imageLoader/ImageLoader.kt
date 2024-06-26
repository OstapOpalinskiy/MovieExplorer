package com.opalynskyi.utils.imageLoader

import android.widget.ImageView

interface ImageLoader {
    fun load(
        url: String,
        target: ImageView,
    )
}

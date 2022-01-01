package com.opalynskyi.cleanmovies.app.imageLoader

import android.widget.ImageView

interface ImageLoader {
    fun load(url: String, target: ImageView)
}
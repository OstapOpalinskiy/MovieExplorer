package com.opalynskyi.cleanmovies.presentation.imageLoader

import android.widget.ImageView

interface ImageLoader {
    fun load(url: String, target: ImageView)
}
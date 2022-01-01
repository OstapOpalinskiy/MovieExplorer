package com.opalynskyi.cleanmovies.app.imageLoader

import android.widget.ImageView
import com.squareup.picasso.Picasso

class PicassoImageLoader(private val picasso: Picasso) : ImageLoader {
    override fun load(url: String, target: ImageView) {
        picasso.load(url).into(target)
    }
}
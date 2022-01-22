package com.opalynskyi.utils.imageLoader

import android.widget.ImageView
import com.squareup.picasso.Picasso

internal class PicassoImageLoader(private val picasso: Picasso) : ImageLoader {
    override fun load(url: String, target: ImageView) {
        picasso.load(url).into(target)
    }
}
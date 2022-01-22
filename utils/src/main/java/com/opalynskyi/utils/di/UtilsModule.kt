package com.opalynskyi.utils.di

import com.opalynskyi.utils.imageLoader.ImageLoader
import com.opalynskyi.utils.imageLoader.PicassoImageLoader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides

@Module
class UtilsModule {
    @Provides
    fun providesPicasso(): Picasso {
        return Picasso.get()
    }

    @Provides
    fun provideImageLoader(picasso: Picasso): ImageLoader {
        return PicassoImageLoader(picasso)
    }
}
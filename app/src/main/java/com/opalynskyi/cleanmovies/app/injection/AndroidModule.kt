package com.opalynskyi.cleanmovies.app.injection

import android.content.Context
import com.opalynskyi.cleanmovies.app.injection.scopes.ApplicationScope
import dagger.Module
import dagger.Provides

/**
 * Android Module used for injecting general Android objects.
 */
@Module
class AndroidModule(val context: Context) {

    @Provides
    @ApplicationScope
    fun provideContext() = context
}
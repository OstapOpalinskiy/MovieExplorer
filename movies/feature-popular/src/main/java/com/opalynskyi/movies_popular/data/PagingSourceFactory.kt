package com.opalynskyi.movies_popular.data

import com.opalynskyi.network.api.MoviesApi
import com.opalynskyi.network.api.ServerMoviesMapper
import javax.inject.Inject

internal class PagingSourceFactory @Inject constructor(
    private val moviesApi: MoviesApi,
    private val moviesMapper: ServerMoviesMapper
) {
    fun newInstance(): MoviesPagingSource {
        return MoviesPagingSource(moviesApi, moviesMapper)
    }
}
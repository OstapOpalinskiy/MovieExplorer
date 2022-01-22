package com.opalynskyi.cleanmovies.data.paging

import com.opalynskyi.cleanmovies.data.api.MoviesApi
import com.opalynskyi.cleanmovies.data.api.ServerMoviesMapper
import javax.inject.Inject

class PagingSourceFactory @Inject constructor(
    private val moviesApi: MoviesApi,
    private val moviesMapper: ServerMoviesMapper
) {
    fun newInstance(): MoviesPagingSource {
        return MoviesPagingSource(moviesApi, moviesMapper)
    }
}
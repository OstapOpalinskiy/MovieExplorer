package com.opalynskyi.movies_popular

import androidx.paging.PagingData
import com.opalynskyi.movies_core.domain.entities.Movie
import com.opalynskyi.movies_core.domain.entities.MoviePage

internal data class PagingDataWrapper(private val pagingData: PagingData<Movie>) : MoviePage() {
    override fun getPage(): PagingData<Movie> {
        return pagingData
    }
}
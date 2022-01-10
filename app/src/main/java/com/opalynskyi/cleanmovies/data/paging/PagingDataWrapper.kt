package com.opalynskyi.cleanmovies.data.paging

import androidx.paging.PagingData
import com.opalynskyi.cleanmovies.domain.entities.Movie
import com.opalynskyi.cleanmovies.domain.entities.MoviePage

data class PagingDataWrapper(private val pagingData: PagingData<Movie>) : MoviePage() {
    override fun getPage(): PagingData<Movie> {
        return pagingData
    }
}
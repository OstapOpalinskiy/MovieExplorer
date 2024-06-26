package com.opalynskyi.popular

import androidx.paging.PagingData
import com.opalynskyi.core.domain.entities.Movie
import com.opalynskyi.core.domain.entities.MoviePage

internal data class PagingDataWrapper(private val pagingData: PagingData<Movie>) : MoviePage() {
    override fun getPage(): PagingData<Movie> {
        return pagingData
    }
}

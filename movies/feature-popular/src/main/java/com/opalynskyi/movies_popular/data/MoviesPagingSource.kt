package com.opalynskyi.movies_popular.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.opalynskyi.movies_core.domain.entities.Movie
import com.opalynskyi.network.api.MoviesApi
import com.opalynskyi.network.api.ServerMoviesMapper
import retrofit2.HttpException
import javax.inject.Inject

internal class MoviesPagingSource @Inject constructor(
    private val moviesApi: MoviesApi,
    private val moviesMapper: ServerMoviesMapper
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val pageNumber = params.key ?: INITIAL_PAGE_NUMBER
            val response = moviesApi.getPopular(pageNumber.toString())
            val nextPageNumber = if (pageNumber == response.totalPages) null else pageNumber + 1
            val prevPageNumber = if (pageNumber > 1) pageNumber - 1 else null
            if (response.movies.isNullOrEmpty()) {
                LoadResult.Page(emptyList(), prevPageNumber, nextPageNumber)
            } else {
                LoadResult.Page(response.movies?.map { moviesMapper.mapFromEntity(it) }
                    ?: emptyList(), prevPageNumber, nextPageNumber)
            }
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    companion object {
        const val MAX_PAGE_SIZE = 20
        const val INITIAL_PAGE_NUMBER = 1
    }
}

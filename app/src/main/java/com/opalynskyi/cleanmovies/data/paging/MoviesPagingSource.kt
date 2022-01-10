package com.opalynskyi.cleanmovies.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.opalynskyi.cleanmovies.data.api.MoviesApi
import com.opalynskyi.cleanmovies.data.api.ServerMoviesMapper
import com.opalynskyi.cleanmovies.domain.entities.Movie
import retrofit2.HttpException
import javax.inject.Inject

class MoviesPagingSource @Inject constructor(
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
                LoadResult.Page(response.movies.map { moviesMapper.mapFromEntity(it)}, prevPageNumber, nextPageNumber)
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

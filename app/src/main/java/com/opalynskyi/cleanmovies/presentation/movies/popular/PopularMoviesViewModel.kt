package com.opalynskyi.cleanmovies.presentation.movies.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import com.opalynskyi.cleanmovies.data.paging.PagingDataWrapper
import com.opalynskyi.cleanmovies.domain.Either
import com.opalynskyi.cleanmovies.domain.entities.Movie
import com.opalynskyi.cleanmovies.domain.usecases.AddToFavouritesUseCase
import com.opalynskyi.cleanmovies.domain.usecases.GetMoviesPagedUseCase
import com.opalynskyi.cleanmovies.domain.usecases.RemoveFromFavouritesUseCase
import com.opalynskyi.cleanmovies.presentation.movies.MovieListMapper
import com.opalynskyi.cleanmovies.presentation.movies.UiAction
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PopularMoviesViewModel @Inject constructor(
    private val addToFavouritesUseCase: AddToFavouritesUseCase,
    private val removeFromFavouritesUseCase: RemoveFromFavouritesUseCase,
    private val movieListMapper: MovieListMapper,
    getMoviesPagedUseCase: GetMoviesPagedUseCase
) : ViewModel() {

    private val actionsChannel = Channel<UiAction>()
    val uiActionsFlow = actionsChannel.receiveAsFlow()

    val movies = getMoviesPagedUseCase(
        pageSize = PAGE_SIZE,
        prefetchDistance = PREFETCH_DISTANCE,
        maxCachedPagesSize = CACHED_PAGES_LIMIT
    ).map { moviePage ->
        val pagingData = moviePage as PagingDataWrapper
        pagingData.getPage().map { movie -> mapToMovieItem(movie) }
    }

    private fun mapToMovieItem(movie: Movie) =
        movieListMapper.mapToMovieItem(
            movie = movie,
            btnFavouriteAction = if (movie.isFavourite) {
                { onRemoveFromFavourite(movie.id) }
            } else {
                { onAddToFavourite(movie.id) }
            },
            btnShareAction = { share("${movie.title} \n ${movie.overview}") })

    private fun onAddToFavourite(id: Int) {
        viewModelScope.launch {
            when (addToFavouritesUseCase(id)) {
                is Either.Error -> showError("Failed add to favourite")
                else -> {
                    /* Do nothing here */
                }
            }
        }
    }

    private fun onRemoveFromFavourite(id: Int) {
        viewModelScope.launch {
            when (removeFromFavouritesUseCase(id)) {
                is Either.Error -> showError("Failed to remove from favourite")
                else -> {
                    /* Do nothing here */
                }
            }
        }
    }

    private fun showError(errorMsg: String) {
        viewModelScope.launch {
            actionsChannel.send(UiAction.ShowError(errorMsg))
        }
    }

    private fun share(text: String) {
        viewModelScope.launch {
            actionsChannel.send(UiAction.Share(text))
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val PREFETCH_DISTANCE = 3
        private const val CACHED_PAGES_LIMIT = 300
    }

    class Factory @Inject constructor(private val viewModel: PopularMoviesViewModel) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PopularMoviesViewModel::class.java)) {
                return viewModel as T
            }
            throw RuntimeException("Can't construct view model")
        }
    }
}
package com.opalynskyi.cleanmovies.presentation.movies.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.opalynskyi.cleanmovies.R
import com.opalynskyi.common.Either
import com.opalynskyi.cleanmovies.domain.entities.Movie
import com.opalynskyi.cleanmovies.domain.usecases.ObserveMoviesUseCase
import com.opalynskyi.cleanmovies.domain.usecases.RemoveFromFavouritesUseCase
import com.opalynskyi.cleanmovies.presentation.movies.MovieListMapper
import com.opalynskyi.cleanmovies.presentation.movies.ScreenState
import com.opalynskyi.cleanmovies.presentation.movies.UiAction
import com.opalynskyi.cleanmovies.presentation.movies.movies_adapter.MovieItem
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteMoviesViewModel @Inject constructor(
    private val removeFromFavouritesUseCase: RemoveFromFavouritesUseCase,
    private val observeMoviesUseCase: ObserveMoviesUseCase,
    private val movieListMapper: MovieListMapper
) : ViewModel() {
    private val _screenStateFlow = MutableStateFlow(ScreenState(isEmpty = true))
    val uiStateFlow: StateFlow<ScreenState> = _screenStateFlow

    private val currentState
        get() = _screenStateFlow.value

    private val actionsChannel = Channel<UiAction>()
    val uiActionsFlow = actionsChannel.receiveAsFlow()

    fun onViewReady() {
        viewModelScope.launch {
            observeMoviesUseCase().collect { movies ->
                updateMoviesList(movies)
            }
        }
    }

    private fun updateMoviesList(movies: List<Movie>) {
        if (movies.isEmpty()) {
            updateUiState(
                currentState.copy(
                    items = emptyList(),
                    isLoading = false,
                    isEmpty = true
                )
            )
        } else {
            val items = movies.map { movie ->
                movie.mapToItem(movie.isFavourite)
            }
            updateUiState(
                currentState.copy(
                    items = items,
                    isLoading = false,
                    isEmpty = false
                )
            )
        }
    }

    private fun Movie.mapToItem(isFavourite: Boolean): MovieItem {
        val btnFavouriteTextRes = if (this.isFavourite) {
            R.string.remove_from_favourites
        } else {
            R.string.add_to_favourites
        }
        return movieListMapper.mapToMovieItem(
            movie = this.copy(isFavourite = isFavourite),
            btnFavouriteTextRes = btnFavouriteTextRes,
            btnFavouriteAction = { isFavouriteStatus ->
                onFavouriteClick(isFavouriteStatus, this)
            },
            btnShareAction = { share("${this.title} \n ${this.overview}") })
    }

    private fun onFavouriteClick(isFavourite: Boolean, movie: Movie) {
        if (isFavourite) {
            onRemoveFromFavourite(movie)
        }
    }

    private fun onRemoveFromFavourite(movie: Movie) {
        viewModelScope.launch {
            when (removeFromFavouritesUseCase(movie)) {
                is Either.Error -> showError("Failed to remove from favourite")
                else -> { /* Do nothing here */
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

    private fun updateUiState(state: ScreenState) {
        _screenStateFlow.value = state
    }

    class Factory @Inject constructor(private val viewModel: FavouriteMoviesViewModel) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FavouriteMoviesViewModel::class.java)) {
                return viewModel as T
            }
            throw RuntimeException("Can't construct view model")
        }
    }
}
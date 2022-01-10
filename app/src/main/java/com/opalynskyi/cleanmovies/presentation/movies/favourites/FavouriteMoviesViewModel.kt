package com.opalynskyi.cleanmovies.presentation.movies.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.opalynskyi.cleanmovies.domain.Either
import com.opalynskyi.cleanmovies.domain.entities.Movie
import com.opalynskyi.cleanmovies.domain.usecases.ObserveMoviesUseCase
import com.opalynskyi.cleanmovies.domain.usecases.RemoveFromFavouritesUseCase
import com.opalynskyi.cleanmovies.presentation.movies.MovieListMapper
import com.opalynskyi.cleanmovies.presentation.movies.ScreenState
import com.opalynskyi.cleanmovies.presentation.movies.UiAction
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
                updateMoviesList(movies.filter { it.isFavourite })
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
                movieListMapper.mapToMovieItem(
                    movie = movie,
                    btnFavouriteAction = if (movie.isFavourite) {
                        { onRemoveFromFavourite(movie.id) }
                    } else {
                        { /* should not happen */ }
                    },
                    btnShareAction = { share("${movie.title} \n ${movie.overview}") }
                )
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

    private fun onRemoveFromFavourite(id: Int) {
        viewModelScope.launch {
            when (removeFromFavouritesUseCase(id)) {
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
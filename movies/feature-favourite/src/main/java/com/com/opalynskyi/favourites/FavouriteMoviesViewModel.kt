package com.com.opalynskyi.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.com.opalynskyi.favourite.R
import com.opalynskyi.common.Either
import com.opalynskyi.movies_core.domain.entities.Movie
import com.opalynskyi.movies_core.domain.usecases.FavouritesUseCases
import com.opalynskyi.movies_list.MovieItem
import com.opalynskyi.movies_list.MovieListBuilder
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteMoviesViewModel @Inject constructor(
    private val favouritesUseCases: FavouritesUseCases,
    private val movieListBuilder: MovieListBuilder
) : ViewModel() {
    private val _screenStateFlow = MutableStateFlow(ScreenState(isLoading = true))
    val uiStateFlow: StateFlow<ScreenState> = _screenStateFlow

    private val currentState
        get() = _screenStateFlow.value

    private val actionsChannel = Channel<UiAction>()
    val uiActionsFlow = actionsChannel.receiveAsFlow()

    fun onViewReady() {
        viewModelScope.launch {
            favouritesUseCases.observeFavouritesUseCase().collect { movies ->
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
        return movieListBuilder.mapToItem(
            movie = this,
            isFavourite = isFavourite,
            onFavouriteAction = { isFavouriteStatus ->
                onFavouriteClick(isFavouriteStatus, this)
            },
            onShareAction = {
                share("${this.title} \n ${this.overview}")
            }
        )
    }

    private fun onFavouriteClick(isFavourite: Boolean, movie: Movie) {
        if (isFavourite) {
            onRemoveFromFavourite(movie)
        }
    }

    private fun onRemoveFromFavourite(movie: Movie) {
        viewModelScope.launch {
            when (favouritesUseCases.removeFromFavouritesUseCase(movie)) {
                is Either.Error -> showError("Failed to remove from favourite")
                else -> {
                    /*
                     Do nothing here, list will be updated
                     automatically by observeFavouritesUseCase()
                    **/
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

    sealed class UiAction {
        class ShowError(val errorMsg: String) : UiAction()
        class ShowMsg(val msg: String) : UiAction()
        class Share(val text: String) : UiAction()
        object ShowLoader : UiAction()
        object HideLoader : UiAction()
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
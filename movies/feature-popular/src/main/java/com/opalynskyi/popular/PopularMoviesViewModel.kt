package com.opalynskyi.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import com.opalynskyi.common.Either
import com.opalynskyi.core.domain.entities.Movie
import com.opalynskyi.core.domain.usecases.FavouritesUseCases
import com.opalynskyi.movies.MovieItem
import com.opalynskyi.movies.MovieListBuilder
import com.opalynskyi.popular.domain.GetMoviesPagedUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PopularMoviesViewModel
    @Inject
    constructor(
        private val favouritesUseCases: FavouritesUseCases,
        private val movieListBuilder: MovieListBuilder,
        getMoviesPagedUseCase: GetMoviesPagedUseCase,
    ) : ViewModel() {
        private val actionsChannel = Channel<UiAction>()
        val uiActionsFlow = actionsChannel.receiveAsFlow()
        private val favourites = mutableListOf<Movie>()

        val moviesPagedFlow =
            getMoviesPagedUseCase(
                pageSize = PAGE_SIZE,
                prefetchDistance = PREFETCH_DISTANCE,
                maxCachedPagesSize = CACHED_PAGES_LIMIT,
            ).map { moviePage ->
                val pagingData = moviePage as PagingDataWrapper
                pagingData.getPage().map { movie ->
                    val isFavourite = favourites.any { it.id == movie.id }
                    movie.mapToItem(isFavourite)
                }
            }

        init {
            viewModelScope.launch {
                favouritesUseCases.observeFavouritesUseCase().collectLatest { list ->
                    favourites.clear()
                    favourites.addAll(list)
                }
            }
        }

        private fun onAddToFavourite(movie: Movie) {
            viewModelScope.launch {
                when (favouritesUseCases.addToFavouritesUseCase(movie)) {
                    is Either.Error -> showError("Failed add to favourite")
                    else -> {
                        // Do nothing here
                    }
                }
            }
        }

        private fun onRemoveFromFavourite(movie: Movie) {
            viewModelScope.launch {
                when (favouritesUseCases.removeFromFavouritesUseCase(movie)) {
                    is Either.Error -> showError("Failed to remove from favourite")
                    else -> {
                        // Do nothing here
                    }
                }
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
                },
            )
        }

        private fun onFavouriteClick(
            isFavourite: Boolean,
            movie: Movie,
        ) {
            if (isFavourite) {
                onRemoveFromFavourite(movie)
            } else {
                onAddToFavourite(movie)
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

        sealed class UiAction {
            class ShowError(val errorMsg: String) : UiAction()

            class ShowMsg(val msg: String) : UiAction()

            class Share(val text: String) : UiAction()
        }

        companion object {
            private const val PAGE_SIZE = 20
            private const val PREFETCH_DISTANCE = 3
            private const val CACHED_PAGES_LIMIT = 300
        }

        class Factory
            @Inject
            constructor(private val viewModel: PopularMoviesViewModel) :
            ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(PopularMoviesViewModel::class.java)) {
                        return viewModel as T
                    }
                    throw RuntimeException("Can't construct view model")
                }
            }
    }

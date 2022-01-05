package com.opalynskyi.cleanmovies.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.opalynskyi.cleanmovies.DateTimeHelper
import com.opalynskyi.cleanmovies.domain.Either
import com.opalynskyi.cleanmovies.domain.entities.Movie
import com.opalynskyi.cleanmovies.domain.usecases.*
import com.opalynskyi.cleanmovies.presentation.movies.movies_adapter.MovieHeaderItem
import com.opalynskyi.cleanmovies.presentation.movies.movies_adapter.MovieItem
import com.opalynskyi.cleanmovies.presentation.movies.movies_adapter.MovieItemComparator
import com.opalynskyi.cleanmovies.presentation.movies.movies_adapter.MoviesListItem
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LatestMoviesViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getFavouritesUseCase: GetFavouritesUseCase,
    private val addToFavouritesUseCase: AddToFavouritesUseCase,
    private val removeFromFavouritesUseCase: RemoveFromFavouritesUseCase,
    private val observeMoviesUseCase: ObserveMoviesUseCase,
    private val dateTimeHelper: DateTimeHelper,
    private val movieListMapper: MovieListMapper,
) : ViewModel() {
    private val _screenStateFlow = MutableStateFlow(ScreenState(isEmpty = true))
    val uiStateFlow: StateFlow<ScreenState> = _screenStateFlow

    private val currentState
        get() = _screenStateFlow.value

    private val actionsChannel = Channel<UiAction>()
    val uiActionsFlow = actionsChannel.receiveAsFlow()

    private var loadStrategy: LoadMoviesStrategy = LoadLatestStrategy()

    fun setMode(mode: Mode) {
        loadStrategy = if (mode == Mode.LATEST) LoadLatestStrategy() else LoadFavouriteStrategy()
    }

    fun onRefresh() {
        loadStrategy.refresh()
    }

    fun onViewReady() {
        loadStrategy.load()
        loadStrategy.observe()
    }

    private interface LoadMoviesStrategy {
        fun load() {}
        fun observe()
        fun refresh()
    }

    private inner class LoadLatestStrategy : LoadMoviesStrategy {
        override fun load() {
            loadLatest()
        }

        override fun observe() {
            viewModelScope.launch {
                observeMoviesUseCase().collect { movies ->
                    updateMoviesList(movies)
                }
            }
        }

        override fun refresh() {
            loadLatest()
        }
    }

    private inner class LoadFavouriteStrategy : LoadMoviesStrategy {

        override fun observe() {
            viewModelScope.launch {
                // TODO observe on IO
                observeMoviesUseCase().collect { movies ->
                    updateMoviesList(movies.filter { it.isFavourite })
                }
            }
        }

        override fun refresh() {
            getFavouriteMovies()
        }

        private fun getFavouriteMovies() {
            viewModelScope.launch {
                when (val result = getFavouritesUseCase()) {
                    is Either.Value -> updateMoviesList(result.value)
                    is Either.Error -> {
                        updateUiState(
                            currentState.copy(isLoading = false, isRefreshing = false)
                        )
                        showError("Failed to load movies: ${result.error.message}")
                    }
                }
            }
        }
    }

    private fun loadLatest() {
        val dateRange = getStartEndDate()
        viewModelScope.launch {
            when (val result = getMoviesUseCase(dateRange.first, dateRange.second)) {
                is Either.Value -> updateMoviesList(result.value)
                is Either.Error -> {
                    updateUiState(
                        currentState.copy(isLoading = false, isRefreshing = false)
                    )
                    showError("Failed to load movies: ${result.error.message}")
                }
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
            val items = createListWithHeaders(
                movies.map { movie ->
                    movieListMapper.mapToMovieItem(
                        movie = movie,
                        btnFavouriteAction = if (movie.isFavourite) {
                            { onRemoveFromFavourite(movie.id) }
                        } else {
                            { onAddToFavourite(movie.id) }
                        },
                        btnShareAction = { share("${movie.title} \n ${movie.overview}") }
                    )
                })
            updateUiState(
                currentState.copy(
                    items = items,
                    isLoading = false,
                    isEmpty = false
                )
            )
        }
    }

    private fun createListWithHeaders(
        movieItems: List<MovieItem>
    ): List<MoviesListItem> {
        val listWithHeaders = mutableListOf<MoviesListItem>()
        val sortedMovieItems = movieItems.sortedWith(MovieItemComparator)
        var headerMonth = -1

        for (element in sortedMovieItems) {
            if (headerMonth == -1 || headerMonth != element.month) {
                headerMonth = element.month
                val header = MovieHeaderItem(dateTimeHelper.getHeaderDate(element.releaseDate))
                listWithHeaders.add(header)
            }
            listWithHeaders.add(element)
        }
        return listWithHeaders
    }

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

    private fun getStartEndDate(): Pair<String, String> {
        val startDateMillis = dateTimeHelper.getMonthFromToday(NUMBER_MONTH_FROM_NOW)
        val startDate = dateTimeHelper.getServerDate(startDateMillis)
        val endDate = dateTimeHelper.getServerDate(System.currentTimeMillis())
        return startDate to endDate
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

    companion object {
        private const val NUMBER_MONTH_FROM_NOW = 3
    }

    class Factory @Inject constructor(private val viewModel: LatestMoviesViewModel) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LatestMoviesViewModel::class.java)) {
                return viewModel as T
            }
            throw RuntimeException("Can't construct view model")
        }
    }
}
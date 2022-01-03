package com.opalynskyi.cleanmovies.presentation.moviesList

import androidx.lifecycle.*
import com.opalynskyi.cleanmovies.DateTimeHelper
import com.opalynskyi.cleanmovies.DispatcherProvider
import com.opalynskyi.cleanmovies.Either
import com.opalynskyi.cleanmovies.domain.entities.Movie
import com.opalynskyi.cleanmovies.domain.usecases.AddToFavouritesUseCase
import com.opalynskyi.cleanmovies.domain.usecases.GetMoviesUseCase
import com.opalynskyi.cleanmovies.domain.usecases.ObserveMoviesUseCase
import com.opalynskyi.cleanmovies.domain.usecases.RemoveFromFavouritesUseCase
import com.opalynskyi.cleanmovies.presentation.MovieListMapper
import com.opalynskyi.cleanmovies.presentation.createListWithHeaders
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class AllMoviesViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val getMoviesUseCase: GetMoviesUseCase,
    private val addToFavouritesUseCase: AddToFavouritesUseCase,
    private val removeFromFavouritesUseCase: RemoveFromFavouritesUseCase,
    private val observeMoviesUseCase: ObserveMoviesUseCase,
    private val dateTimeHelper: DateTimeHelper,
    private val movieListMapper: MovieListMapper,
) : ViewModel() {
    val screenStateLiveData: LiveData<MoviesScreenState>
        get() = _screenStateLiveData
    private val _screenStateLiveData = MutableLiveData<MoviesScreenState>().apply {
        value = MoviesScreenState(
            items = emptyList(),
            isLoading = false,
            isRefreshing = false,
            isEmpty = true
        )
    }
    private val currentState
        get() = _screenStateLiveData.value

    init {
        viewModelScope.launch {
            observeMoviesUseCase().collect { movies ->
                Timber.d("Movies from observable: ${movies.map { it.title }}")
                updateMoviesList(movies)
            }
        }
    }

    fun onRefresh() {
        onViewReady()
    }

    fun onViewReady() {
        val dateRange = getStartEndDate()
        Timber.d("Date range: $dateRange")
        viewModelScope.launch {
            when (val result = getMoviesUseCase(dateRange.first, dateRange.second)) {
                is Either.Value -> {
                    val movies = result.value
                    updateMoviesList(movies)
                }
                is Either.Error -> {
                    updateScreenState(
                        currentState?.copy(
                            isLoading = false
                        )
                    )
//                    view?.showError("Failed to load movies: ${result.error.message}")
                }
            }
        }
    }

    private fun updateMoviesList(movies: List<Movie>) {
        if (movies.isEmpty()) {
            updateScreenState(
                currentState?.copy(
                    items = emptyList(),
                    isLoading = false,
                    isEmpty = true
                )
            )
        } else {
            val items = createListWithHeaders(
                dateTimeHelper,
                movies.map { movieListMapper.mapToMovieItem(it) })
            updateScreenState(
                currentState?.copy(
                    items = items,
                    isLoading = false,
                    isEmpty = false
                )
            )
        }
    }

    fun onAddToFavourite(id: Int) {
        viewModelScope.launch {
            when (addToFavouritesUseCase(id)) {
//                is Either.Error -> view?.showError("Failed add to favourite")
            }
        }
    }

    fun onRemoveFromFavourite(id: Int) {
        viewModelScope.launch {
            when (removeFromFavouritesUseCase(id)) {
//                is Either.Value -> view?.notifyIsRemoved(id)
//                is Either.Error -> view?.showError("Failed to remove from favourite")
            }
        }
    }

    private fun getStartEndDate(): Pair<String, String> {
        val startDateMillis = dateTimeHelper.getMonthFromToday(NUMBER_MONTH_FROM_NOW)
        val startDate = dateTimeHelper.getServerDate(startDateMillis)
        val endDate = dateTimeHelper.getServerDate(System.currentTimeMillis())
        return startDate to endDate
    }

    private fun updateScreenState(state: MoviesScreenState?) {
        _screenStateLiveData.value = state
    }

    companion object {
        private const val NUMBER_MONTH_FROM_NOW = 3
    }

    class Factory @Inject constructor(private val viewModel: AllMoviesViewModel) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AllMoviesViewModel::class.java)) {
                return viewModel as T
            }
            throw RuntimeException("Can't construct view model")
        }
    }
}
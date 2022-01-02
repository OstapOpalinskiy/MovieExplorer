package com.opalynskyi.cleanmovies.presentation.moviesList

import com.opalynskyi.cleanmovies.DateTimeHelper
import com.opalynskyi.cleanmovies.DispatcherProvider
import com.opalynskyi.cleanmovies.Either
import com.opalynskyi.cleanmovies.domain.usecases.AddToFavouritesUseCase
import com.opalynskyi.cleanmovies.domain.usecases.GetMoviesUseCase
import com.opalynskyi.cleanmovies.domain.usecases.RemoveFromFavouritesUseCase
import com.opalynskyi.cleanmovies.presentation.MovieListMapper
import com.opalynskyi.cleanmovies.presentation.createListWithHeaders
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber

class AllMoviesPresenter(
    private val dispatcherProvider: DispatcherProvider,
    private val getMoviesUseCase: GetMoviesUseCase,
    private val addToFavouritesUseCase: AddToFavouritesUseCase,
    private val removeFromFavouritesUseCase: RemoveFromFavouritesUseCase,
    private val dateTimeHelper: DateTimeHelper,
    private val movieListMapper: MovieListMapper,
) : AllMoviesContract.Presenter {

    private val uiScope = CoroutineScope(dispatcherProvider.main() + SupervisorJob())
    override var view: AllMoviesContract.View? = null

    override fun onRefresh() {
        getStartEndDate()
    }

    override fun getMovies() {
        val dateRange = getStartEndDate()
        Timber.d("Date range: $dateRange")
        uiScope.launch {
            when (val result = getMoviesUseCase(dateRange.first, dateRange.second)) {
                is Either.Value -> {
                    view?.hideProgress()
                    val movies = result.value
                    if (movies.isEmpty()) {
                        view?.showEmptyState()
                    } else {
                        view?.showMovies(createListWithHeaders(
                            dateTimeHelper,
                            movies.map { movieListMapper.mapToMovieItem(it) }
                        ))
                    }
                }
                is Either.Error -> {
                    view?.hideProgress()
                    view?.showError("Failed to load movies: ${result.error.message}")
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

    override fun addToFavourite(id: Int) {
        uiScope.launch {
            when (addToFavouritesUseCase(id)) {
                is Either.Value -> view?.notifyItemIsFavourite(id)
                is Either.Error -> view?.showError("Failed add to favourite")
            }
        }
    }

    override fun removeFromFavourite(id: Int) {
        uiScope.launch {
            when (removeFromFavouritesUseCase(id)) {
                is Either.Value -> view?.notifyIsRemoved(id)
                is Either.Error -> view?.showError("Failed to remove from favourite")
            }
        }
    }

    companion object {
        private const val NUMBER_MONTH_FROM_NOW = 3
    }
}
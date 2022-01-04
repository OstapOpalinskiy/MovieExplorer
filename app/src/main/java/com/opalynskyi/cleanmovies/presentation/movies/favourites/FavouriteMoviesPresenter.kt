package com.opalynskyi.cleanmovies.presentation.movies.favourites

import com.opalynskyi.cleanmovies.DateTimeHelper
import com.opalynskyi.cleanmovies.DispatcherProvider
import com.opalynskyi.cleanmovies.domain.Either
import com.opalynskyi.cleanmovies.domain.entities.Movie
import com.opalynskyi.cleanmovies.domain.usecases.GetFavouritesUseCase
import com.opalynskyi.cleanmovies.domain.usecases.ObserveMoviesUseCase
import com.opalynskyi.cleanmovies.domain.usecases.RemoveFromFavouritesUseCase
import com.opalynskyi.cleanmovies.presentation.MovieListMapper
import com.opalynskyi.cleanmovies.presentation.createListWithHeaders
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class FavouriteMoviesPresenter(
    private val dispatcherProvider: DispatcherProvider,
    private val removeFromFavouritesUseCase: RemoveFromFavouritesUseCase,
    private val getFavouritesUseCase: GetFavouritesUseCase,
    private val observeMoviesUseCase: ObserveMoviesUseCase,
    private val movieListMapper: MovieListMapper,
    private val dateTimeHelper: DateTimeHelper
) : FavouriteMoviesContract.Presenter {

    override var view: FavouriteMoviesContract.View? = null
    private val uiScope = CoroutineScope(dispatcherProvider.main() + SupervisorJob())

    init {
        uiScope.launch {
            observeMoviesUseCase().collect { movies ->
                Timber.d("Movies from observable: ${movies.map { it.title }}")
                showMovies(movies.filter { it.isFavourite })
            }
        }
    }

    override fun onRefresh() {
        getFavouriteMovies()
    }

    override fun getFavouriteMovies() {
        uiScope.launch {
            when (val result = getFavouritesUseCase()) {
                is Either.Value -> {
                    view?.hideProgress()
                    val movies = result.value
                    showMovies(movies)
                }
                is Either.Error -> {
                    view?.hideProgress()
                    view?.showError("Failed to load movies: ${result.error.message}")
                }
            }
        }
    }

    private fun showMovies(movies: List<Movie>) {
        if (movies.isEmpty()) {
            view?.showEmptyState()
        }
        view?.showMovies(createListWithHeaders(
            dateTimeHelper,
            movies.map { movieListMapper.mapToMovieItem(it) }
        ))
    }

    override fun removeFromFavourite(id: Int) {
        uiScope.launch {
            when (removeFromFavouritesUseCase(id)) {
                is Either.Value -> Timber.d("Removed from favourite: $id")
                is Either.Error -> view?.showError("Failed to remove from favourite")
            }
        }
    }
}
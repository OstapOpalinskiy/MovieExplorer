package com.opalynskyi.cleanmovies.presentation.favourites

import com.opalynskyi.cleanmovies.DateTimeHelper
import com.opalynskyi.cleanmovies.DispatcherProvider
import com.opalynskyi.cleanmovies.Either
import com.opalynskyi.cleanmovies.domain.usecases.GetFavouritesUseCase
import com.opalynskyi.cleanmovies.domain.usecases.RemoveFromFavouritesUseCase
import com.opalynskyi.cleanmovies.presentation.MovieListMapper
import com.opalynskyi.cleanmovies.presentation.createListWithHeaders
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber

class FavouriteMoviesPresenter(
    private val dispatcherProvider: DispatcherProvider,
    private val removeFromFavouritesUseCase: RemoveFromFavouritesUseCase,
    private val getFavouritesUseCase: GetFavouritesUseCase,
    private val movieListMapper: MovieListMapper,
    private val dateTimeHelper: DateTimeHelper
) : FavouriteMoviesContract.Presenter {

    override var view: FavouriteMoviesContract.View? = null
    private val uiScope = CoroutineScope(dispatcherProvider.main() + SupervisorJob())

    override fun onRefresh() {
        getFavouriteMovies()
    }

    override fun getFavouriteMovies() {
        uiScope.launch {
            when (val result = getFavouritesUseCase()) {
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

    override fun removeFromFavourite(id: Int) {
        uiScope.launch {
            when (removeFromFavouritesUseCase(id)) {
                is Either.Value -> Timber.d("Removed from favourite: $id")
                is Either.Error -> view?.showError("Failed to remove from favourite")
            }
        }
    }
}
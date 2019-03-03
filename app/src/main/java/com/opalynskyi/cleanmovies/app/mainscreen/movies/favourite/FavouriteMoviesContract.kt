package com.opalynskyi.cleanmovies.app.mainscreen.movies.favourite

import com.opalynskyi.cleanmovies.app.BasePresenter
import com.opalynskyi.cleanmovies.app.mainscreen.movies.adapter.ListItem
import com.opalynskyi.cleanmovies.app.mainscreen.movies.adapter.MovieItem

class FavouriteMoviesContract {
    interface Presenter : BasePresenter<View> {
        fun getMovies()
    }

    interface View {
        fun showProgress()
        fun hideProgress()
        fun showEmptyState()
        fun showError(errorMsg: String)
        fun showMovies(movies: List<ListItem>)
    }
}
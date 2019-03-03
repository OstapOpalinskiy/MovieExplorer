package com.opalynskyi.cleanmovies.app.mainscreen.movies.all

import com.opalynskyi.cleanmovies.app.BasePresenter
import com.opalynskyi.cleanmovies.app.mainscreen.movies.adapter.MovieItem

interface AllMoviesContract {

    interface Presenter : BasePresenter<View> {
        fun getMovies()
    }

    interface View {
        fun showProgress()
        fun hideProgress()
        fun showEmptyState()
        fun showError(errorMsg: String)
        fun showMovies(movies: List<MovieItem>)
    }

}
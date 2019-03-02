package com.opalynskyi.cleanmovies.app.movies

import com.opalynskyi.cleanmovies.app.base.BasePresenter
import com.opalynskyi.cleanmovies.app.movies.adapter.MovieItem

interface MoviesContract {

    interface Presenter : BasePresenter<View> {
        fun loadUserPhoto()
        fun getAllMovies()
        fun getFavouriteMovies()
    }

    interface View {
        fun showPhoto(photoUrl: String)
        fun showError(errorMsg: String)
        fun showAll(movies: List<MovieItem>)
        fun showFavourite(movies: List<MovieItem>)
    }
}

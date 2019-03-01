package com.opalynskyi.cleanmovies.app.movies

import com.opalynskyi.cleanmovies.app.base.BasePresenter
import com.opalynskyi.cleanmovies.core.domain.movies.entities.MovieModel

interface MoviesContract {

    interface Presenter : BasePresenter<View> {
        fun loadUserPhoto()
        fun getMovies()
    }

    interface View {
        fun showPhoto(photoUrl: String)
        fun showError(errorMsg: String)
        fun showMovies(movies: List<MovieModel>)
    }
}

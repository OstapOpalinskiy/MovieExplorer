package com.opalynskyi.cleanmovies.presentation.moviesList

import com.opalynskyi.cleanmovies.presentation.BasePresenter
import com.opalynskyi.cleanmovies.presentation.BaseView
import com.opalynskyi.cleanmovies.presentation.adapter.MoviesListItem

interface AllMoviesContract {

    interface View: BaseView {
        fun renderEmptyState()
        fun renderMovies(movies: List<MoviesListItem>)
        fun showMessage(msg: String)
        fun showError(errorMsg: String)
        fun hideProgress()
        fun notifyItemIsFavourite(id: Int)
        fun notifyIsAdded(id: Int)
        fun notifyIsRemoved(id: Int)
    }
}
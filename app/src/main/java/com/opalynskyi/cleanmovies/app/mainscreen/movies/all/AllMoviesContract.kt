package com.opalynskyi.cleanmovies.app.mainscreen.movies.all

import com.opalynskyi.cleanmovies.app.BasePresenter
import com.opalynskyi.cleanmovies.app.mainscreen.movies.adapter.ListItem

interface AllMoviesContract {

    interface Presenter : BasePresenter<View> {
        fun getMovies()
        fun addToFavourite(id: Int)
        fun share()
    }

    interface View {
        fun showProgress()
        fun hideProgress()
        fun showEmptyState()
        fun showMessage(msg: String)
        fun notifyItemIsFavourite(id: Int)
        fun showError(errorMsg: String)
        fun showMovies(movies: List<ListItem>)
    }
}
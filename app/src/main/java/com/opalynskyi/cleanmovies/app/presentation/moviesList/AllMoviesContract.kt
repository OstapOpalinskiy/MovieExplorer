package com.opalynskyi.cleanmovies.app.presentation.moviesList

import com.opalynskyi.cleanmovies.app.presentation.BasePresenter
import com.opalynskyi.cleanmovies.app.presentation.BaseView
import com.opalynskyi.cleanmovies.app.presentation.adapter.ListItem

interface AllMoviesContract {

    interface Presenter : BasePresenter<View> {
        fun getMovies()
        fun addToFavourite(id: Int)
        fun removeFromFavourite(id: Int)
        fun subscribeForEvents()
        fun onRefresh()
    }

    interface View: BaseView {
        fun showEmptyState()
        fun showMovies(movies: List<ListItem>)
        fun showMessage(msg: String)
        fun showError(errorMsg: String)
        fun hideProgress()
        fun notifyItemIsFavourite(id: Int)
        fun notifyIsAdded(id: Int)
        fun notifyIsRemoved(id: Int)
    }
}
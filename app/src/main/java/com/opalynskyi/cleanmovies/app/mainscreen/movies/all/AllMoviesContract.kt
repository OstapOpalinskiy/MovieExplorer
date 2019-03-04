package com.opalynskyi.cleanmovies.app.mainscreen.movies.all

import com.opalynskyi.cleanmovies.app.BasePresenter
import com.opalynskyi.cleanmovies.app.mainscreen.movies.adapter.ListItem

interface AllMoviesContract {

    interface Presenter : BasePresenter<View> {
        fun getMovies()
        fun addToFavourite(id: Int)
        fun removeFromFavourite(id: Int)
        fun subscribeForEvents()
        fun onRefresh()
    }

    interface View {
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
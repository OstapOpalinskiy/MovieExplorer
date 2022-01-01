package com.opalynskyi.cleanmovies.app.presentation.favourites

import com.opalynskyi.cleanmovies.app.presentation.BasePresenter
import com.opalynskyi.cleanmovies.app.presentation.BaseView
import com.opalynskyi.cleanmovies.app.presentation.adapter.ListItem

class FavouriteMoviesContract {
    interface Presenter : BasePresenter<View> {
        fun getFavouriteMovies()
        fun removeFromFavourite(id: Int)
        fun subscribeForEvents()
        fun onRefresh()
    }

    interface View: BaseView {
        fun hideProgress()
        fun showEmptyState()
        fun showMessage(msg: String)
        fun showError(errorMsg: String)
        fun showMovies(movies: List<ListItem>)
        fun removeItem(id: Int)
    }
}
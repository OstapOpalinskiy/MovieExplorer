package com.opalynskyi.cleanmovies.presentation.favourites

import com.opalynskyi.cleanmovies.presentation.BasePresenter
import com.opalynskyi.cleanmovies.presentation.BaseView
import com.opalynskyi.cleanmovies.presentation.adapter.ListItem

class FavouriteMoviesContract {
    interface Presenter : BasePresenter<View> {
        fun getFavouriteMovies()
        fun removeFromFavourite(id: Int)
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
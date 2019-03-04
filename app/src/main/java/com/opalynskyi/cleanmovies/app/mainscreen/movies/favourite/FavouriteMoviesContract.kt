package com.opalynskyi.cleanmovies.app.mainscreen.movies.favourite

import com.opalynskyi.cleanmovies.app.BasePresenter
import com.opalynskyi.cleanmovies.app.mainscreen.movies.adapter.ListItem

class FavouriteMoviesContract {
    interface Presenter : BasePresenter<View> {
        fun getFavouriteMovies()
        fun removeFromFavourite(id: Int)
        fun subscribeForEvents()
        fun share()
    }

    interface View {
        fun showProgress()
        fun hideProgress()
        fun showEmptyState()
        fun showMessage(msg: String)
        fun showError(errorMsg: String)
        fun showMovies(movies: List<ListItem>)
        fun removeItem(id: Int)
    }
}
package com.opalynskyi.cleanmovies.app.movies

import com.opalynskyi.cleanmovies.app.base.BasePresenter

interface MoviesContract {

    interface Presenter : BasePresenter<View> {
        fun loadUserPhoto()
    }

    interface View {
        fun showPhoto(photoUrl: String)
        fun showError(errorMsg: String)
    }
}

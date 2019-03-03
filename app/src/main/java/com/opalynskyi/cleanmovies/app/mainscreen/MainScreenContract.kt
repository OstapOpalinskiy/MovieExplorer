package com.opalynskyi.cleanmovies.app.mainscreen

import com.opalynskyi.cleanmovies.app.BasePresenter

interface MainScreenContract {
    interface Presenter : BasePresenter<View> {
        fun loadUserPhoto()
    }

    interface View {
        fun showProgress()
        fun showPhoto(photoUrl: String)
        fun showError(errorMsg: String)
    }
}
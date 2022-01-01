package com.opalynskyi.cleanmovies.app.presentation

interface MainScreenContract {
    interface Presenter : BasePresenter<View> {
        fun loadUserPhoto()
    }

    interface View: BaseView {
        fun showProgress()
        fun showPhoto(photoUrl: String)
        fun showError(errorMsg: String)
    }
}
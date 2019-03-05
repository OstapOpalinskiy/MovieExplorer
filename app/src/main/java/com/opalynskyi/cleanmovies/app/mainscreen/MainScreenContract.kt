package com.opalynskyi.cleanmovies.app.mainscreen

import com.opalynskyi.cleanmovies.app.BasePresenter
import com.opalynskyi.cleanmovies.app.BaseView

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
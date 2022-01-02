package com.opalynskyi.cleanmovies.login

import com.opalynskyi.cleanmovies.presentation.BasePresenter
import com.opalynskyi.cleanmovies.presentation.BaseView

class LoginContract {

    interface Presenter : BasePresenter<View> {
        fun login()
        fun onActivityResult(result: LoginResultWrapper)
        fun isLoggedIn(): Boolean
    }

    interface View : BaseView {
        fun continueFlow()
        fun showLoginError(errorMsg: String)
    }
}
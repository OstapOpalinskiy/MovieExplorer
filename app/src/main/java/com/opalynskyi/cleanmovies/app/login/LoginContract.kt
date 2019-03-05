package com.opalynskyi.cleanmovies.app.login

import com.opalynskyi.cleanmovies.app.BasePresenter
import com.opalynskyi.cleanmovies.app.BaseView

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
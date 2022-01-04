package com.opalynskyi.cleanmovies.presentation.movies

sealed class UiAction {
    class ShowError(val errorMsg: String): UiAction()
    class ShowMsg(val msg: String): UiAction()
    class Share(val text: String): UiAction()
}
package com.opalynskyi.cleanmovies.core.ui.base

interface BasePresenter {
    fun bind(view: BaseView)
    fun unbind()
}
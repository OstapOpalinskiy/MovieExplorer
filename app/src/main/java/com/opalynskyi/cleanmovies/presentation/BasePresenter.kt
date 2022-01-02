package com.opalynskyi.cleanmovies.presentation

interface BasePresenter<V> {
    var view: V?

    fun bind(view: V) {
        this.view = view
    }

    fun unbind() {
        view = null
    }
}
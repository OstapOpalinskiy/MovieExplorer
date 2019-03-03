package com.opalynskyi.cleanmovies.app

import io.reactivex.disposables.CompositeDisposable

interface BasePresenter<V> {
    var view: V?
    val compositeDisposable: CompositeDisposable

    fun bind(view: V) {
        this.view = view
    }

    fun unbind() {
        compositeDisposable.dispose()
        view = null
    }
}
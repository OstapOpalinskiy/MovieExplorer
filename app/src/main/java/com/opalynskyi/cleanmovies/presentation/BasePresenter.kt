package com.opalynskyi.cleanmovies.presentation

import io.reactivex.disposables.CompositeDisposable

interface BasePresenter<V> {
    var view: V?
    var compositeDisposable: CompositeDisposable?

    fun bind(view: V) {
        this.view = view
        compositeDisposable = CompositeDisposable()
    }

    fun unbind() {
        compositeDisposable?.dispose()
        view = null
    }
}
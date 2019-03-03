package com.opalynskyi.cleanmovies.app.mainscreen

import com.opalynskyi.cleanmovies.core.SchedulerProvider
import com.opalynskyi.cleanmovies.core.domain.user.UserInteractor
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy

class MainScreenPresenter(
    private val userInteractor: UserInteractor,
    private val scheduler: SchedulerProvider
) : MainScreenContract.Presenter {

    override var view: MainScreenContract.View? = null

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun loadUserPhoto() {
        compositeDisposable += userInteractor
            .getUser()
            .observeOn(scheduler.mainThread())
            .subscribeBy(
                onSuccess = { view?.showPhoto(it.photoUrl) },
                onError = { view?.showError(it.message!!) }
            )
    }

//
//    override fun getFavouriteMovies() {
//        compositeDisposable += moviesInteractor
//            .getFavourites()
//            .subscribeOn(Schedulers.io())
//            .observeOn(scheduler.mainThread())
//            .subscribeBy(
//                onSuccess = { view?.showFavourite(it.map { movie -> MovieItem.fromMovie(movie) }) },
//                onError = { view?.showError(it.message!!) }
//            )
//    }
}
package com.opalynskyi.cleanmovies.app.movies

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.opalynskyi.cleanmovies.R
import com.opalynskyi.cleanmovies.app.CleanMoviesApplication
import com.opalynskyi.cleanmovies.app.api.MoviesApi
import com.opalynskyi.cleanmovies.app.di.movies.PagerAdapter
import com.opalynskyi.cleanmovies.core.domain.movies.entities.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movies.*
import timber.log.Timber
import javax.inject.Inject


class MoviesActivity : AppCompatActivity(), MoviesContract.View {

    @Inject
    lateinit var presenter: MoviesContract.Presenter

    @Inject
    lateinit var api: MoviesApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        CleanMoviesApplication.instance.getMoviesComponent().inject(this)
        presenter.bind(this)
        presenter.loadUserPhoto()
        presenter.getMovies()
        viewPager.adapter = PagerAdapter(supportFragmentManager)
        tabs.setupWithViewPager(viewPager)
    }

    override fun showPhoto(photoUrl: String) {
        Picasso.get().load(photoUrl).into(profilePhoto)
    }

    override fun showMovies(movies: List<Movie>) {
        Timber.d("Movies size: ${movies.size}")
        movies.forEach { Timber.d("title: ${it.title}") }
    }

    override fun showError(errorMsg: String) {
        Timber.e(errorMsg)
    }

    companion object {
        fun intent(context: Context) =
            Intent(context, MoviesActivity::class.java)
    }
}

package com.opalynskyi.cleanmovies.app.movies

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentStatePagerAdapter
import com.opalynskyi.cleanmovies.R
import com.opalynskyi.cleanmovies.app.CleanMoviesApplication
import com.opalynskyi.cleanmovies.app.api.MoviesApi
import com.opalynskyi.cleanmovies.app.movies.adapter.MovieItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movies.*
import timber.log.Timber
import javax.inject.Inject


class MoviesActivity : AppCompatActivity(), MoviesContract.View {

    @Inject
    lateinit var presenter: MoviesContract.Presenter
    private lateinit var pagerAdapter: FragmentStatePagerAdapter
//    @Inject
//    lateinit var presenter: MoviesContract.Presenter

    @Inject
    lateinit var api: MoviesApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        CleanMoviesApplication.instance.getMoviesComponent().inject(this)
        presenter.bind(this)
        presenter.loadUserPhoto()
        pagerAdapter = PagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter
        tabs.setupWithViewPager(viewPager)
//        Thread {
//            val movie = dao.getById(332562)
//            dao.insert(
//                MovieDbEntity(
//                    movie.id,
//                    movie.overview,
//                    movie.releaseDate,
//                    movie.posterPath,
//                    movie.title,
//                    movie.voteAverage,
//                    true
//
//
//                )
//            )
//        }.start()

    }

    override fun showPhoto(photoUrl: String) {
        Picasso.get().load(photoUrl).into(profilePhoto)
    }

    override fun showAll(movies: List<MovieItem>) {
        (pagerAdapter.getItem(ALL_POSITION) as AllMoviesFragment).showMovies(movies)
    }

    override fun showFavourite(movies: List<MovieItem>) {
        (pagerAdapter.getItem(FAVOURITE_POSITION) as FavouriteMoviesFragment).showMovies(movies)
    }


    override fun showError(errorMsg: String) {
        Timber.e(errorMsg)
    }

    companion object {
        fun intent(context: Context) =
            Intent(context, MoviesActivity::class.java)

        private const val ALL_POSITION = 0
        private const val FAVOURITE_POSITION = 1
    }
}

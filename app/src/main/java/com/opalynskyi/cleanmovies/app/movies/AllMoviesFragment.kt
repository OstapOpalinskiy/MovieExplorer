package com.opalynskyi.cleanmovies.app.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.opalynskyi.cleanmovies.R
import com.opalynskyi.cleanmovies.app.CleanMoviesApplication
import com.opalynskyi.cleanmovies.app.movies.adapter.MovieItem
import kotlinx.android.synthetic.main.movies_fragment_layout.*
import timber.log.Timber
import javax.inject.Inject

class AllMoviesFragment : Fragment() {
    @Inject
    lateinit var presenter: MoviesContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.movies_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CleanMoviesApplication.instance.getMoviesComponent().injectAllMoviesFragment(this)
        Timber.d("On view created, recycler view: $recyclerView")
//        presenter.getAllMovies()
        swipeRefreshLayout.setOnRefreshListener { presenter.getAllMovies() }
    }

    fun showMovies(movies: List<MovieItem>) {

    }

    companion object {
        @JvmStatic
        fun newInstance() = AllMoviesFragment()
    }
}
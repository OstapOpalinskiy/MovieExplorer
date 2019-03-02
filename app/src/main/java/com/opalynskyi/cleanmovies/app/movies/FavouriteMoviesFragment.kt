package com.opalynskyi.cleanmovies.app.movies

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.opalynskyi.cleanmovies.R
import com.opalynskyi.cleanmovies.app.CleanMoviesApplication
import com.opalynskyi.cleanmovies.app.movies.adapter.MovieItem
import com.opalynskyi.cleanmovies.app.movies.adapter.MoviesAdapter
import kotlinx.android.synthetic.main.movies_fragment_layout.*
import timber.log.Timber
import javax.inject.Inject

class FavouriteMoviesFragment : Fragment() {
    @Inject
    lateinit var presenter: MoviesContract.Presenter
    private var adapter: MoviesAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.movies_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CleanMoviesApplication.instance.getMoviesComponent().injectFavouriteMoviesFragment(this)
        Timber.d("On view created, recycler view: $recyclerView")
        swipeRefreshLayout.setOnRefreshListener { presenter.getFavouriteMovies() }
        recyclerView.layoutManager = LinearLayoutManager(context)
        if (adapter == null) {
            adapter = MoviesAdapter(mutableListOf())
            recyclerView?.adapter = adapter
        }
       Handler().postDelayed({presenter.getFavouriteMovies()}, 3000)
    }

    override fun onResume() {
        super.onResume()
        Timber.d("onResume, activity: $activity")
    }


    fun showMovies(movies: List<MovieItem>) {
//        swipeRefreshLayout.isRefreshing = false
        Timber.d("showMovies, recycler view: $recyclerView")
        Timber.d("showMovies, activity: $activity")
        val mutableList = movies.toMutableList()
        adapter?.refreshList(mutableList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.d("onDestroyView, recycler view: $recyclerView")
    }


    companion object {
        @JvmStatic
        fun newInstance() = FavouriteMoviesFragment()
    }
}
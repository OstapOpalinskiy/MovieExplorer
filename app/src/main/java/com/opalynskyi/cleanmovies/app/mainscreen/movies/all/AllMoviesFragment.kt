package com.opalynskyi.cleanmovies.app.mainscreen.movies.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.opalynskyi.cleanmovies.R
import com.opalynskyi.cleanmovies.app.CleanMoviesApplication
import com.opalynskyi.cleanmovies.app.mainscreen.movies.adapter.ListItem
import com.opalynskyi.cleanmovies.app.mainscreen.movies.adapter.MoviesAdapter
import kotlinx.android.synthetic.main.movies_fragment_layout.*
import timber.log.Timber
import javax.inject.Inject

class AllMoviesFragment : Fragment(), AllMoviesContract.View {

    @Inject
    lateinit var presenter: AllMoviesContract.Presenter
    private var adapter: MoviesAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.movies_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CleanMoviesApplication.instance.getAllMoviesComponent().inject(this)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = MoviesAdapter(
            mutableListOf(),
            { id -> id?.let { presenter.addToFavourite(id) } },
            { Toast.makeText(context, "SHARE", Toast.LENGTH_SHORT).show() })
        recyclerView?.adapter = adapter
        presenter.bind(this)
        swipeRefreshLayout.setOnRefreshListener { presenter.getMovies() }
        presenter.getMovies()
    }

    override fun showProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMessage(msg: String) {
        Snackbar.make(root, msg, Snackbar.LENGTH_SHORT).show()
    }

    override fun showEmptyState() {

    }

    override fun showError(errorMsg: String) {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun showMovies(movies: List<ListItem>) {
        Timber.d("List of movies: ${movies.size}")
        swipeRefreshLayout.isRefreshing = false
        adapter?.refreshList(movies.toMutableList())
    }


    companion object {
        @JvmStatic
        fun newInstance() = AllMoviesFragment()
    }
}
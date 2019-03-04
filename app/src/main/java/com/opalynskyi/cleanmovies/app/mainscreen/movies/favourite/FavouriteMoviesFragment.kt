package com.opalynskyi.cleanmovies.app.mainscreen.movies.favourite

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.opalynskyi.cleanmovies.R
import com.opalynskyi.cleanmovies.app.CleanMoviesApplication
import com.opalynskyi.cleanmovies.app.mainscreen.movies.adapter.ListItem
import com.opalynskyi.cleanmovies.app.mainscreen.movies.adapter.MoviesAdapter
import com.opalynskyi.cleanmovies.app.mainscreen.movies.share
import kotlinx.android.synthetic.main.movies_fragment_layout.*
import timber.log.Timber
import javax.inject.Inject

class FavouriteMoviesFragment : Fragment(), FavouriteMoviesContract.View {

    @Inject
    lateinit var presenter: FavouriteMoviesContract.Presenter
    private var adapter: MoviesAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.movies_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CleanMoviesApplication.instance.getFavouriteMoviesComponent().inject(this)

        generalProgress.isVisible = false
        emptyText.text = getString(R.string.no_favoutites_yet)
        swipeRefreshLayout.setOnRefreshListener { presenter.getFavouriteMovies() }
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = MoviesAdapter(
            mutableListOf(),
            { id -> Timber.d("Add action is ignored for: $id") },
            { id -> id?.let { presenter.removeFromFavourite(id) } },
            { text -> context?.let { share(it, text) } })
        recyclerView?.adapter = adapter

        presenter.bind(this)
        presenter.subscribeForEvents()
        presenter.getFavouriteMovies()
    }

    override fun hideProgress() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun showEmptyState() {
        generalProgress.isVisible = false
        emptyText.isVisible = true
    }

    override fun showError(errorMsg: String) {
        Snackbar.make(root, errorMsg, Snackbar.LENGTH_SHORT).setActionTextColor(Color.RED).show()
    }

    override fun removeItem(id: Int) {
        adapter?.remove(id)
        // TODO: move logic to presenter
        if (adapter?.items?.isEmpty() == true) {
            showEmptyState()
        }
    }

    override fun showMessage(msg: String) {
        Snackbar.make(root, msg, Snackbar.LENGTH_SHORT).show()
    }

    override fun showMovies(movies: List<ListItem>) {
        generalProgress.isVisible = false
        emptyText.isVisible = false
        swipeRefreshLayout.isRefreshing = false
        Timber.d("List of movies: ${movies.size}")
        adapter?.refreshList(movies.toMutableList())
    }


    companion object {
        @JvmStatic
        fun newInstance() = FavouriteMoviesFragment()
    }
}
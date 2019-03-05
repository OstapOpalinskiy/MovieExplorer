package com.opalynskyi.cleanmovies.app.mainscreen.movies.all

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
import com.opalynskyi.cleanmovies.app.mainscreen.movies.startAnimation
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

        CleanMoviesApplication.instance.getMoviesComponent().inject(this)

        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = MoviesAdapter(
            mutableListOf(),
            { id -> id?.let { presenter.addToFavourite(id) } },
            { id -> id?.let { presenter.removeFromFavourite(id) } },
            { text -> context?.let { share(it, text) } })
        recyclerView?.adapter = adapter
        swipeRefreshLayout.setOnRefreshListener { presenter.getMovies() }

        presenter.bind(this)
        presenter.subscribeForEvents()
        presenter.getMovies()
    }

    override fun showEmptyState() {
        generalProgress.isVisible = false
        emptyText.isVisible = true
    }

    override fun showMovies(movies: List<ListItem>) {
        startAnimation(recyclerView)
        Timber.d("List of movies: ${movies.size}")
        generalProgress.isVisible = false
        emptyText.isVisible = false
        adapter?.refreshList(movies.toMutableList())
    }

    override fun hideProgress() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun showMessage(msg: String) {
        Snackbar.make(root, msg, Snackbar.LENGTH_SHORT).show()
    }

    override fun notifyItemIsFavourite(id: Int) {
        adapter?.notifyItemIsFavourite(id)
    }

    override fun showError(errorMsg: String) {
        Snackbar.make(root, errorMsg, Snackbar.LENGTH_SHORT).setActionTextColor(Color.RED).show()
    }

    override fun notifyIsAdded(id: Int) {
        adapter?.notifyItemIsFavourite(id)
    }

    override fun notifyIsRemoved(id: Int) {
        adapter?.notifyItemIsRemoved(id)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbind()
    }

    companion object {
        @JvmStatic
        fun newInstance() = AllMoviesFragment()
    }
}
package com.opalynskyi.cleanmovies.app.presentation.moviesList

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
import com.opalynskyi.cleanmovies.app.presentation.imageLoader.ImageLoader
import com.opalynskyi.cleanmovies.app.presentation.adapter.ListItem
import com.opalynskyi.cleanmovies.app.presentation.adapter.MoviesAdapter
import com.opalynskyi.cleanmovies.app.presentation.share
import com.opalynskyi.cleanmovies.app.presentation.startAnimation
import kotlinx.android.synthetic.main.movies_fragment_layout.*
import timber.log.Timber
import javax.inject.Inject

class AllMoviesFragment : Fragment(), AllMoviesContract.View {

    @Inject
    lateinit var presenter: AllMoviesContract.Presenter

    @Inject
    lateinit var imageLoader: ImageLoader

    private val moviesAdapter: MoviesAdapter by lazy {
        MoviesAdapter(
            imageLoader = imageLoader,
            addToFavouriteAction = { id -> id?.let { presenter.addToFavourite(id) } },
            removeFromFavoriteAction = { id -> id?.let { presenter.removeFromFavourite(id) } },
            shareAction = { text -> context?.let { share(it, text) } }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movies_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CleanMoviesApplication.instance.getMoviesComponent().inject(this)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = moviesAdapter
        }
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
        moviesAdapter.refreshList(movies)
    }

    override fun hideProgress() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun showMessage(msg: String) {
        Snackbar.make(root, msg, Snackbar.LENGTH_SHORT).show()
    }

    override fun notifyItemIsFavourite(id: Int) {
        moviesAdapter.notifyItemIsFavourite(id)
    }

    override fun showError(errorMsg: String) {
        Snackbar.make(root, errorMsg, Snackbar.LENGTH_SHORT).setActionTextColor(Color.RED).show()
    }

    override fun notifyIsAdded(id: Int) {
        moviesAdapter.notifyItemIsFavourite(id)
    }

    override fun notifyIsRemoved(id: Int) {
        moviesAdapter.notifyItemIsRemoved(id)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbind()
    }

    companion object {
        fun newInstance() = AllMoviesFragment()
    }
}
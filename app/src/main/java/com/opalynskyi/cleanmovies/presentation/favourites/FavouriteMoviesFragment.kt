package com.opalynskyi.cleanmovies.presentation.favourites

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.opalynskyi.cleanmovies.CleanMoviesApplication
import com.opalynskyi.cleanmovies.R
import com.opalynskyi.cleanmovies.databinding.MoviesFragmentLayoutBinding
import com.opalynskyi.cleanmovies.presentation.adapter.ListItem
import com.opalynskyi.cleanmovies.presentation.adapter.MoviesAdapter
import com.opalynskyi.cleanmovies.presentation.imageLoader.ImageLoader
import com.opalynskyi.cleanmovies.presentation.share
import com.opalynskyi.cleanmovies.presentation.startAnimation
import timber.log.Timber
import javax.inject.Inject

class FavouriteMoviesFragment : Fragment(), FavouriteMoviesContract.View {

    @Inject
    lateinit var presenter: FavouriteMoviesContract.Presenter

    @Inject
    lateinit var imageLoader: ImageLoader

    private val binding get() = _binding!!
    private var _binding: MoviesFragmentLayoutBinding? = null

    private val moviesAdapter: MoviesAdapter by lazy {
        MoviesAdapter(
            imageLoader = imageLoader,
            addToFavouriteAction = { id -> Timber.d("Add action is ignored for: $id") },
            removeFromFavoriteAction = { id -> id?.let { presenter.removeFromFavourite(id) } },
            shareAction = { text -> context?.let { share(it, text) } }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MoviesFragmentLayoutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CleanMoviesApplication.instance.getMoviesComponent().inject(this)

        binding.generalProgress.isVisible = false
        binding.emptyText.text = getString(R.string.no_favoutites_yet)
        binding.swipeRefreshLayout.setOnRefreshListener { presenter.onRefresh() }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = moviesAdapter
        }

        presenter.bind(this)
        presenter.getFavouriteMovies()
    }

    override fun hideProgress() {
        binding.swipeRefreshLayout.isRefreshing = false
    }

    override fun showEmptyState() {
        binding.generalProgress.isVisible = false
        binding.emptyText.isVisible = true
    }

    override fun showError(errorMsg: String) {
        Snackbar.make(binding.root, errorMsg, Snackbar.LENGTH_SHORT).setActionTextColor(Color.RED)
            .show()
    }

    override fun removeItem(id: Int) {
        moviesAdapter.remove(id)
        if (moviesAdapter.items.isEmpty()) {
            showEmptyState()
        }
    }

    override fun showMessage(msg: String) {
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
    }

    override fun showMovies(movies: List<ListItem>) {
        binding.generalProgress.isVisible = false
        binding.emptyText.isVisible = false
        binding.swipeRefreshLayout.isRefreshing = false
        Timber.d("List of movies: ${movies.size}")
        startAnimation(binding.recyclerView)
        moviesAdapter.refreshList(movies.toMutableList())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        presenter.unbind()
    }

    companion object {
        fun newInstance() = FavouriteMoviesFragment()
    }
}
package com.opalynskyi.cleanmovies.presentation.moviesList

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.opalynskyi.cleanmovies.CleanMoviesApplication
import com.opalynskyi.cleanmovies.databinding.MoviesFragmentLayoutBinding
import com.opalynskyi.cleanmovies.presentation.adapter.MoviesAdapter
import com.opalynskyi.cleanmovies.presentation.adapter.MoviesListItem
import com.opalynskyi.cleanmovies.presentation.imageLoader.ImageLoader
import com.opalynskyi.cleanmovies.presentation.share
import javax.inject.Inject

class AllMoviesFragment : Fragment() {

    @Inject
    lateinit var imageLoader: ImageLoader

    private val binding get() = _binding!!
    private var _binding: MoviesFragmentLayoutBinding? = null

    @Inject
    lateinit var viewModelFactory: AllMoviesViewModel.Factory

    private val viewModel: AllMoviesViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[AllMoviesViewModel::class.java]
    }

    private val moviesAdapter: MoviesAdapter by lazy {
        MoviesAdapter(
            imageLoader = imageLoader,
            addToFavouriteAction = { id -> id?.let { viewModel.onAddToFavourite(id) } },
            removeFromFavoriteAction = { id -> id?.let { viewModel.onRemoveFromFavourite(id) } },
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
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = moviesAdapter
        }
        binding.swipeRefreshLayout.setOnRefreshListener { viewModel.onViewReady() }
        viewModel.screenStateLiveData.observe(viewLifecycleOwner) { state ->
            renderState(state)
        }
        viewModel.onViewReady()
    }

    private fun renderState(state: MoviesScreenState) {
        if (state.isEmpty) {
            renderEmptyState()
        } else {
            renderMovies(state.items)
        }
        binding.loader.isVisible = state.isLoading
        binding.swipeRefreshLayout.isRefreshing = state.isRefreshing
    }

    private fun renderEmptyState() {
        binding.loader.isVisible = false
        binding.emptyText.isVisible = true
    }

    private fun renderMovies(movies: List<MoviesListItem>) {
        binding.loader.isVisible = false
        binding.emptyText.isVisible = false
        moviesAdapter.submitList(movies)
    }

     private fun hideProgress() {
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun showMessage(msg: String) {
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
    }

    private fun showError(errorMsg: String) {
        Snackbar.make(binding.root, errorMsg, Snackbar.LENGTH_SHORT).setActionTextColor(Color.RED)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = AllMoviesFragment()
    }
}
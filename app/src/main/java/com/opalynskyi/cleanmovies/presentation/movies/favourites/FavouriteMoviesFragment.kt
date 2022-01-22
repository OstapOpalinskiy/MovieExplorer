package com.opalynskyi.cleanmovies.presentation.movies.favourites

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.opalynskyi.cleanmovies.CleanMoviesApplication
import com.opalynskyi.cleanmovies.data.share
import com.opalynskyi.cleanmovies.databinding.MoviesListFragmentBinding
import com.opalynskyi.movies_list.MoviesListItem
import com.opalynskyi.utils.imageLoader.ImageLoader
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteMoviesFragment : Fragment() {

    @Inject
    lateinit var imageLoader: ImageLoader

    private val binding get() = _binding!!
    private var _binding: MoviesListFragmentBinding? = null

    @Inject
    lateinit var viewModelFactory: FavouriteMoviesViewModel.Factory

    private val viewModel:FavouriteMoviesViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[FavouriteMoviesViewModel::class.java]
    }

    private val favouriteMoviesAdapter: FavouriteMoviesAdapter by lazy {
        FavouriteMoviesAdapter(imageLoader = imageLoader)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MoviesListFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CleanMoviesApplication.instance.getMoviesComponent().inject(this)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favouriteMoviesAdapter
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiStateFlow.collect { state ->
                    renderState(state)
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiActionsFlow.collect { action ->
                    when (action) {
                        is FavouriteMoviesViewModel.UiAction.ShowError -> showError(action.errorMsg)
                        is FavouriteMoviesViewModel.UiAction.ShowMsg -> showMessage(action.msg)
                        is FavouriteMoviesViewModel.UiAction.Share -> share(action.text)
                    }
                }
            }
        }
        if (savedInstanceState == null) {
            viewModel.onViewReady()
        }
    }

    private fun renderState(state: ScreenState) {
        if (state.isEmpty) {
            renderEmptyState()
        } else {
            renderMovies(state.items)
        }
        binding.loader.isVisible = state.isLoading
    }

    private fun renderEmptyState() {
        binding.loader.isVisible = false
        binding.emptyText.isVisible = true
    }

    private fun renderMovies(movies: List<MoviesListItem>) {
        binding.loader.isVisible = false
        binding.emptyText.isVisible = false
        favouriteMoviesAdapter.submitList(movies)
    }

    private fun showMessage(msg: String) =
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT)
            .show()

    private fun showError(errorMsg: String) {
        Snackbar.make(binding.root, errorMsg, Snackbar.LENGTH_SHORT).apply {
            setTextColor(Color.RED)
            show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = FavouriteMoviesFragment()
    }
}
package com.com.opalynskyi.favourites

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
import com.com.opalynskyi.favourites.di.MoviesFavouriteFeatureComponent
import com.com.opalynskyi.favourites.di.MoviesFavouriteFeatureComponentHolder
import com.google.android.material.snackbar.Snackbar
import com.opalynskyi.movies_list.MoviesListItem
import com.opalynskyi.movies_list.databinding.MoviesListFragmentBinding
import com.opalynskyi.movies_list.share
import com.opalynskyi.utils.imageLoader.ImageLoader
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteMoviesFragment : Fragment() {

    private val injector by lazy {
        MoviesFavouriteFeatureComponentHolder
    }

    @Inject
    lateinit var imageLoader: ImageLoader

    private val binding get() = _binding!!
    private var _binding: MoviesListFragmentBinding? = null

    @Inject
    lateinit var viewModelFactory: FavouriteMoviesViewModel.Factory

    private val viewModel: FavouriteMoviesViewModel by lazy {
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
        (injector.get() as MoviesFavouriteFeatureComponent).inject(this)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favouriteMoviesAdapter
        }
        binding.swipeRefresh.isEnabled = false
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
                        FavouriteMoviesViewModel.UiAction.HideLoader -> {
                            binding.loader.isVisible = false
                        }
                        FavouriteMoviesViewModel.UiAction.ShowLoader -> {
                            binding.loader.isVisible = true
                        }
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
        with(binding) {
            loader.isVisible = false
            emptyText.isVisible = true
        }
        favouriteMoviesAdapter.submitList(emptyList())
    }

    private fun renderMovies(movies: List<MoviesListItem>) {
        with(binding) {
            loader.isVisible = false
            emptyText.isVisible = false
        }
        favouriteMoviesAdapter.submitList(movies)
    }

    private fun showMessage(msg: String) =
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT)
            .show()

    private fun showError(errorMsg: String) {
        Snackbar.make(binding.root, errorMsg, Snackbar.LENGTH_SHORT).apply {
            setTextColor(Color.RED)
        }.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        injector.reset()
        _binding = null
    }
}
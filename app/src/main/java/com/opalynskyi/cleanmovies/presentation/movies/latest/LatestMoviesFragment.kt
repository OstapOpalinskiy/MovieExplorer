package com.opalynskyi.cleanmovies.presentation.movies.latest

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
import com.opalynskyi.cleanmovies.databinding.MoviesFragmentLayoutBinding
import com.opalynskyi.cleanmovies.presentation.imageLoader.ImageLoader
import com.opalynskyi.cleanmovies.presentation.movies.ScreenState
import com.opalynskyi.cleanmovies.presentation.movies.UiAction
import com.opalynskyi.cleanmovies.presentation.movies.movies_adapter.MoviesAdapter
import com.opalynskyi.cleanmovies.presentation.movies.movies_adapter.MoviesListItem
import com.opalynskyi.cleanmovies.presentation.movies.share
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class LatestMoviesFragment : Fragment() {

    @Inject
    lateinit var imageLoader: ImageLoader

    private val binding get() = _binding!!
    private var _binding: MoviesFragmentLayoutBinding? = null

    @Inject
    lateinit var viewModelFactory: LatestMoviesViewModel.Factory

    private val viewModel: LatestMoviesViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[LatestMoviesViewModel::class.java]
    }

    private val moviesAdapter: MoviesAdapter by lazy {
        MoviesAdapter(imageLoader = imageLoader)
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
        binding.swipeRefreshLayout.setOnRefreshListener { viewModel.onRefresh() }
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
                        is UiAction.ShowError -> showError(action.errorMsg)
                        is UiAction.ShowMsg -> showMessage(action.msg)
                        is UiAction.Share -> share(action.text)
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
        fun newInstance(mode: Mode): LatestMoviesFragment {
            val fragment = LatestMoviesFragment().apply {
                val bundle = Bundle()
                bundle.putSerializable(MODE_KEY, mode)
                arguments = 
            }
        }
        private const val MODE_KEY = "fragment_mode_key"
    }
}
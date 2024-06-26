package com.opalynskyi.popular

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
import androidx.paging.PagingData
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.opalynskyi.movies.MovieItem
import com.opalynskyi.movies.share
import com.opalynskyi.movieslist.databinding.MoviesListFragmentBinding
import com.opalynskyi.popular.di.MoviesPopularFeatureComponent
import com.opalynskyi.popular.di.MoviesPopularFeatureComponentHolder
import com.opalynskyi.utils.imageLoader.ImageLoader
import kotlinx.coroutines.launch
import javax.inject.Inject

class PopularMoviesFragment : Fragment() {
    private val injector by lazy {
        MoviesPopularFeatureComponentHolder
    }

    @Inject
    lateinit var imageLoader: ImageLoader

    private val binding get() = _binding!!
    private var _binding: MoviesListFragmentBinding? = null

    @Inject
    lateinit var viewModelFactory: PopularMoviesViewModel.Factory

    private val viewModel: PopularMoviesViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[PopularMoviesViewModel::class.java]
    }

    private val popularAdapter: PopularMoviesAdapter by lazy {
        PopularMoviesAdapter(imageLoader = imageLoader)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = MoviesListFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        (injector.get() as MoviesPopularFeatureComponent).inject(this)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter =
                popularAdapter.withLoadStateHeaderAndFooter(
                    header = MoviesPagingStateAdapter(),
                    footer = MoviesPagingStateAdapter(),
                )
        }
        binding.swipeRefresh.setOnRefreshListener {
            popularAdapter.refresh()
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.moviesPagedFlow.collect { movies ->
                    renderMovies(movies)
                }
            }
        }

        popularAdapter.addLoadStateListener { state ->
            with(binding) {
                swipeRefresh.isRefreshing = false
                state.decideOnState(
                    popularAdapter.itemCount,
                    showLoading = { visible -> loader.isVisible = visible },
                    showEmptyState = { visible -> emptyText.isVisible = visible },
                    showError = { message -> showError(message) },
                )
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiActionsFlow.collect { action ->
                    when (action) {
                        is PopularMoviesViewModel.UiAction.ShowError -> showError(action.errorMsg)
                        is PopularMoviesViewModel.UiAction.ShowMsg -> showMessage(action.msg)
                        is PopularMoviesViewModel.UiAction.Share -> share(action.text)
                    }
                }
            }
        }
    }

    private suspend fun renderMovies(movies: PagingData<MovieItem>) {
        popularAdapter.submitData(movies.map { it })
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
        _binding = null
        injector.reset()
    }
}

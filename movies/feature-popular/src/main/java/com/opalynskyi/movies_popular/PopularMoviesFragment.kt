package com.opalynskyi.movies_popular

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
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.opalynskyi.movies_list.MovieItem
import com.opalynskyi.movies_list.databinding.MoviesListFragmentBinding
import com.opalynskyi.movies_list.share
import com.opalynskyi.movies_popular.di.MoviesPopularFeatureComponent
import com.opalynskyi.movies_popular.di.MoviesPopularFeatureComponentHolder
import com.opalynskyi.utils.imageLoader.ImageLoader
import kotlinx.coroutines.flow.collect
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
        savedInstanceState: Bundle?
    ): View {
        _binding = MoviesListFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (injector.get() as MoviesPopularFeatureComponent).inject(this)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = popularAdapter.withLoadStateHeaderAndFooter(
                header = MoviesLoaderStateAdapter(),
                footer = MoviesLoaderStateAdapter()
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
        // Simple ui actions, no need to pass to VM
        popularAdapter.addLoadStateListener { state ->
            with(binding) {
                swipeRefresh.isRefreshing = false
                state.decideOnState(
                    showLoading = { visible ->
                        loader.isVisible = visible
                    },
                    showEmptyState = { visible ->
                        emptyText.isVisible = visible
                    },
                    showError = { message ->
                        showError(message)
                    }
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

    private inline fun CombinedLoadStates.decideOnState(
        showLoading: (Boolean) -> Unit,
        showEmptyState: (Boolean) -> Unit,
        showError: (String) -> Unit
    ) {
        showLoading(refresh is LoadState.Loading)

        showEmptyState(
            source.append is LoadState.NotLoading
                    && source.append.endOfPaginationReached
                    && popularAdapter.itemCount == 0
        )

        val errorState = source.append as? LoadState.Error
            ?: source.prepend as? LoadState.Error
            ?: source.refresh as? LoadState.Error
            ?: append as? LoadState.Error
            ?: prepend as? LoadState.Error
            ?: refresh as? LoadState.Error

        errorState?.let { showError(it.error.toString()) }
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
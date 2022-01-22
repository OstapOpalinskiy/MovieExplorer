package com.opalynskyi.cleanmovies.presentation.movies.popular

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
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.opalynskyi.cleanmovies.CleanMoviesApplication
import com.opalynskyi.cleanmovies.data.share
import com.opalynskyi.cleanmovies.databinding.MoviesFragmentLayoutBinding
import com.opalynskyi.cleanmovies.presentation.imageLoader.ImageLoader
import com.opalynskyi.cleanmovies.presentation.movies.MoviesLoaderStateAdapter
import com.opalynskyi.cleanmovies.presentation.movies.UiAction
import com.opalynskyi.cleanmovies.presentation.movies.movies_adapter.MovieItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class PopularMoviesFragment : Fragment() {

    @Inject
    lateinit var imageLoader: ImageLoader

    private val binding get() = _binding!!
    private var _binding: MoviesFragmentLayoutBinding? = null

    @Inject
    lateinit var viewModelFactory: PopularMoviesViewModel.Factory

    private val viewModel: PopularMoviesViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[PopularMoviesViewModel::class.java]
    }

    private val popularMoviesAdapter: PopularMoviesAdapter by lazy {
        PopularMoviesAdapter(imageLoader = imageLoader)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MoviesFragmentLayoutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        popularMoviesAdapter.refresh()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CleanMoviesApplication.instance.getMoviesComponent().inject(this)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = popularMoviesAdapter.withLoadStateHeaderAndFooter(
                header = MoviesLoaderStateAdapter(),
                footer = MoviesLoaderStateAdapter()
            )
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.moviesPagedFlow.collect { movies ->
                    renderMovies(movies)
                }
            }
        }
        popularMoviesAdapter.addLoadStateListener { state ->
            with(binding) {
                recyclerView.isVisible = state.refresh != LoadState.Loading
                loader.isVisible = state.refresh == LoadState.Loading
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
    }

    private suspend fun renderMovies(movies: PagingData<MovieItem>) {
        binding.recyclerView.isVisible = true
        binding.loader.isVisible = false
        binding.emptyText.isVisible = false
        popularMoviesAdapter.submitData(movies.map { it })
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
        fun newInstance() = PopularMoviesFragment()
    }
}
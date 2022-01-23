package com.opalynskyi.movies_popular

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.opalynskyi.movies_list.databinding.MoviesListItemHeaderBinding
import com.opalynskyi.movies_list.databinding.MoviesListMovieItemBinding
import com.opalynskyi.movies_list.BaseViewHolder
import com.opalynskyi.movies_list.MovieHeaderItem
import com.opalynskyi.movies_list.MovieItem
import com.opalynskyi.movies_list.MoviesListItem
import com.opalynskyi.utils.imageLoader.ImageLoader

internal class PopularMoviesAdapter(
    private val imageLoader: ImageLoader
) : PagingDataAdapter<MoviesListItem, BaseViewHolder>(MoviesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            HEADER_VIEW_TYPE -> {
                val binding = MoviesListItemHeaderBinding.inflate(inflater, parent, false)
                HeaderViewHolder(binding)
            }
            MOVIE_VIEW_TYPE -> {
                val binding = MoviesListMovieItemBinding.inflate(inflater, parent, false)
                MovieViewHolder(binding)
            }
            else -> {
                throw RuntimeException("Unknown view type: $viewType")
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        val item = getItem(position)
        if (payloads.isEmpty()) {
            holder.bind(item)
        } else {
            val movieItem = item as MovieItem
            val movieViewHolder = holder as MovieViewHolder
            val context = movieViewHolder.binding.root.context
            movieViewHolder.bindFavourite(movieItem, context)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MovieHeaderItem -> HEADER_VIEW_TYPE
            is MovieItem -> MOVIE_VIEW_TYPE
            else -> {
                throw RuntimeException("Unknown view type for item at position $position")
            }
        }
    }

    class HeaderViewHolder(binding: MoviesListItemHeaderBinding) : BaseViewHolder(binding.root) {
        private val tvTitle: TextView = binding.tvTitle

        override fun bind(item: MoviesListItem?) {
            tvTitle.text = (item as MovieHeaderItem).title
        }
    }

    inner class MovieViewHolder(val binding: MoviesListMovieItemBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(item: MoviesListItem?) {
            val movie = (item as MovieItem)
            movie.imageUrl.let { url ->
                imageLoader.load(url, binding.ivCover)
            }
            binding.tvTitle.text = movie.title
            binding.tvOverview.text = movie.overview
            binding.tvRating.text = movie.rating.toString()
            binding.btnShare.setOnClickListener {
                movie.btnShareAction()
            }
            bindFavourite(movie, binding.root.context)
        }

        fun bindFavourite(
            item: MovieItem,
            context: Context
        ) {
            updateFavouriteView(item, context)
            binding.btnFavourites.setOnClickListener {
                item.btnFavouriteAction(item.isFavourite)
                item.isFavourite = !item.isFavourite
                // Quick solution to change individual item in paging adapter,
                // should not be needed after using remote mediator api
                updateFavouriteView(item, context)
            }
        }

        private fun updateFavouriteView(
            item: MovieItem,
            context: Context
        ) {
            if (item.isFavourite) {
                binding.btnFavourites.text = context.getString(R.string.movies_popular_remove_from_favourites)
            } else {
                binding.btnFavourites.text = context.getString(R.string.movies_popular_add_to_favourites)
            }
            binding.ivFavourite.isVisible = item.isFavourite
        }
    }

    companion object {
        private const val MOVIE_VIEW_TYPE = 0
        private const val HEADER_VIEW_TYPE = 1
    }

    private class MoviesDiffCallback : DiffUtil.ItemCallback<MoviesListItem>() {

        override fun areItemsTheSame(oldItem: MoviesListItem, newItem: MoviesListItem): Boolean {
            return if (oldItem is MovieHeaderItem && newItem is MovieHeaderItem) {
                oldItem.title == newItem.title
            } else if (oldItem is MovieItem && newItem is MovieItem) {
                oldItem.id == newItem.id
            } else {
                false
            }
        }

        override fun areContentsTheSame(oldItem: MoviesListItem, newItem: MoviesListItem): Boolean {
            return if (oldItem is MovieHeaderItem && newItem is MovieHeaderItem) {
                oldItem.title == newItem.title
            } else if (oldItem is MovieItem && newItem is MovieItem) {
                oldItem == newItem
            } else {
                false
            }
        }

        override fun getChangePayload(oldItem: MoviesListItem, newItem: MoviesListItem): Any? {
            return if (oldItem is MovieItem && newItem is MovieItem) {
                oldItem.isFavourite != newItem.isFavourite
                true
            } else {
                null
            }
        }
    }
}
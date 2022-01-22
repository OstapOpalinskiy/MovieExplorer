package com.opalynskyi.cleanmovies.presentation.movies.favourites

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.opalynskyi.movies_list.databinding.MoviesListItemHeaderBinding
import com.opalynskyi.movies_list.databinding.MoviesListMovieItemBinding
import com.opalynskyi.movies_list.BaseViewHolder
import com.opalynskyi.movies_list.MovieHeaderItem
import com.opalynskyi.movies_list.MovieItem
import com.opalynskyi.movies_list.MoviesListItem
import com.opalynskyi.utils.imageLoader.ImageLoader

class FavouriteMoviesAdapter(
    private val items: MutableList<MoviesListItem> = mutableListOf(),
    private val imageLoader: ImageLoader
) : RecyclerView.Adapter<BaseViewHolder>() {

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

    fun submitList(newItems: List<MoviesListItem>) {
        val diffCallback = MoviesDiffCallback(items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
        items.clear()
        items.addAll(newItems)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        val item = items[position]
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
        return when (items[position]) {
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
            binding.btnShare.setOnClickListener { movie.btnShareAction() }
            bindFavourite(movie, binding.root.context)
        }

        fun bindFavourite(
            movie: MovieItem,
            context: Context
        ) {
            binding.btnFavourites.setOnClickListener { movie.btnFavouriteAction(movie.isFavourite) }
            binding.btnFavourites.text = context.getString(movie.btnFavouriteTextRes)
            binding.ivFavourite.isVisible = movie.isFavourite
        }
    }

    companion object {
        private const val MOVIE_VIEW_TYPE = 0
        private const val HEADER_VIEW_TYPE = 1
    }

    private class MoviesDiffCallback(
        val oldItems: List<MoviesListItem>,
        val newItems: List<MoviesListItem>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldItems.size

        override fun getNewListSize() = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldItems[oldItemPosition]
            val newItem = newItems[newItemPosition]
            return if (oldItem is MovieHeaderItem && newItem is MovieHeaderItem) {
                oldItem.title == newItem.title
            } else if (oldItem is MovieItem && newItem is MovieItem) {
                oldItem.id == newItem.id
            } else {
                false
            }
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldItems[oldItemPosition]
            val newItem = newItems[newItemPosition]
            return if (oldItem is MovieHeaderItem && newItem is MovieHeaderItem) {
                oldItem.title == newItem.title
            } else if (oldItem is MovieItem && newItem is MovieItem) {
                oldItem == newItem
            } else {
                false
            }
        }

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            val oldItem = oldItems[oldItemPosition]
            val newItem = newItems[newItemPosition]
            return if (oldItem is MovieItem && newItem is MovieItem) {
                oldItem.isFavourite != newItem.isFavourite
                true
            } else {
                null
            }
        }
    }
}
package com.opalynskyi.popular

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.opalynskyi.movies.BaseViewHolder
import com.opalynskyi.movies.MovieItem
import com.opalynskyi.movies.MoviesListItem
import com.opalynskyi.movieslist.databinding.MoviesListMovieItemBinding
import com.opalynskyi.utils.imageLoader.ImageLoader

internal class PopularMoviesAdapter(
    private val imageLoader: ImageLoader,
) : PagingDataAdapter<MoviesListItem, BaseViewHolder>(MoviesDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MoviesListMovieItemBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        payloads: MutableList<Any>,
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
            context: Context,
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
            context: Context,
        ) {
            if (item.isFavourite) {
                binding.btnFavourites.text =
                    context.getString(com.opalynskyi.movieslist.R.string.movies_list_remove_from_favourites)
            } else {
                binding.btnFavourites.text =
                    context.getString(com.opalynskyi.movieslist.R.string.movies_list_add_to_favourites)
            }
            binding.ivFavourite.isVisible = item.isFavourite
        }
    }

    private class MoviesDiffCallback : DiffUtil.ItemCallback<MoviesListItem>() {
        override fun areItemsTheSame(
            oldItem: MoviesListItem,
            newItem: MoviesListItem,
        ): Boolean {
            return if (oldItem is MovieItem && newItem is MovieItem) {
                oldItem.id == newItem.id
            } else {
                false
            }
        }

        override fun areContentsTheSame(
            oldItem: MoviesListItem,
            newItem: MoviesListItem,
        ): Boolean {
            return if (oldItem is MovieItem && newItem is MovieItem) {
                oldItem == newItem
            } else {
                false
            }
        }

        override fun getChangePayload(
            oldItem: MoviesListItem,
            newItem: MoviesListItem,
        ): Any? {
            return if (oldItem is MovieItem && newItem is MovieItem) {
                oldItem.isFavourite != newItem.isFavourite
                true
            } else {
                null
            }
        }
    }
}

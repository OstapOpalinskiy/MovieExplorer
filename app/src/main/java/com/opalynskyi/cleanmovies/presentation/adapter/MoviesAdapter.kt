package com.opalynskyi.cleanmovies.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.opalynskyi.cleanmovies.R
import com.opalynskyi.cleanmovies.databinding.HeaderLayoutBinding
import com.opalynskyi.cleanmovies.databinding.MovieItemBinding
import com.opalynskyi.cleanmovies.presentation.imageLoader.ImageLoader
import timber.log.Timber

class MoviesAdapter(
    val items: MutableList<MoviesListItem> = mutableListOf(),
    private val imageLoader: ImageLoader,
    private var addToFavouriteAction: (Int?) -> Unit,
    private var removeFromFavoriteAction: (Int?) -> Unit,
    private var shareAction: (String) -> Unit

) : RecyclerView.Adapter<MoviesAdapter.BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (ItemType.fromInt(viewType)) {
            ItemType.HEADER -> {
                val binding = HeaderLayoutBinding.inflate(inflater, parent, false)
                HeaderViewHolder(binding)
            }
            ItemType.ITEM -> {
                val binding = MovieItemBinding.inflate(inflater, parent, false)
                MovieViewHolder(binding)
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
        holder.bind(item, addToFavouriteAction, removeFromFavoriteAction, shareAction)
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        val item = items[position]
        if (payloads.isEmpty()) {
            holder.bind(item, addToFavouriteAction, removeFromFavoriteAction, shareAction)
        } else {
            val movieItem = item as MovieItem
            (holder as MovieViewHolder).bindFavourite(movieItem.id, movieItem.isFavourite)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is MovieHeaderItem -> ItemType.HEADER.value
            is MovieItem -> ItemType.ITEM.value
            else -> {
                throw RuntimeException("Unknown view for item with position $position")
            }
        }
    }

    abstract class BaseViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(
            item: MoviesListItem,
            addToFavouriteAction: (Int?) -> Unit,
            removeFromFavoriteAction: (Int?) -> Unit,
            shareAction: (String) -> Unit
        )
    }

    class HeaderViewHolder(binding: HeaderLayoutBinding) : BaseViewHolder(binding.root) {
        private val title: TextView = binding.title

        override fun bind(
            item: MoviesListItem,
            addToFavouriteAction: (Int?) -> Unit,
            removeFromFavoriteAction: (Int?) -> Unit,
            shareAction: (String) -> Unit
        ) {
            title.text = (item as MovieHeaderItem).title
        }
    }


    inner class MovieViewHolder(binding: MovieItemBinding) : BaseViewHolder(binding.root) {
        private val cover: ImageView = binding.cover
        private val title: TextView = binding.title
        private val overview: TextView = binding.overview
        private val rating: TextView = binding.rating
        private val btnFavourites: TextView = binding.btnFavourites
        private val btnShare: View = binding.btnShare
        private val star: View = binding.star

        override fun bind(
            item: MoviesListItem,
            addToFavouriteAction: (Int?) -> Unit,
            removeFromFavoriteAction: (Int?) -> Unit,
            shareAction: (String) -> Unit
        ) {
            val movie = (item as MovieItem)
            movie.cover.let { url ->
                imageLoader.load(url, cover)
            }
            Timber.d("Cover: ${movie.cover}")
            title.text = movie.title
            overview.text = movie.overview
            rating.text = movie.rating.toString()
            btnShare.setOnClickListener { shareAction.invoke("${movie.title} \n ${movie.overview}") }
            bindFavourite(movie.id, movie.isFavourite)
        }

        fun bindFavourite(
            movieId: Int,
            isFavourite: Boolean
        ) {
            val action = if (isFavourite) {
                removeFromFavoriteAction
            } else {
                addToFavouriteAction
            }
            btnFavourites.setOnClickListener { action.invoke(movieId) }
            btnFavourites.text = if (isFavourite) {
                view.context.getString(R.string.remove_from_favourites)
            } else {
                view.context.getString(R.string.add_to_favourites)
            }
            star.isVisible = isFavourite
        }
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
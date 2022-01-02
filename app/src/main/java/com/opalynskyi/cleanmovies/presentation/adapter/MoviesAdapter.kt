package com.opalynskyi.cleanmovies.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.opalynskyi.cleanmovies.R
import com.opalynskyi.cleanmovies.databinding.HeaderLayoutBinding
import com.opalynskyi.cleanmovies.databinding.MovieItemBinding
import com.opalynskyi.cleanmovies.presentation.imageLoader.ImageLoader
import timber.log.Timber

class MoviesAdapter(
    val items: MutableList<ListItem> = mutableListOf(),
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

    fun refreshList(dataSource: List<ListItem>) {
        items.clear()
        items.addAll(dataSource)
        notifyDataSetChanged()
    }

    fun remove(id: Int) {
        val item = getBy(id)
        val position = items.indexOf(item)
        removeHeaderIfNoChildren(item)
        items.remove(item)
        notifyItemRemoved(position)
    }

    fun notifyItemIsFavourite(id: Int) {
        val item = items.firstOrNull { it.movie?.id == id }
        item?.movie?.isFavourite = true
        notifyItemChanged(items.indexOf(item))
    }

    fun notifyItemIsRemoved(id: Int) {
        val item = items.firstOrNull { it.movie?.id == id }
        item?.movie?.isFavourite = false
        notifyItemChanged(items.indexOf(item))
    }

    private fun removeHeaderIfNoChildren(itemToRemove: ListItem?) {
        itemToRemove.let {
            val header = it?.header
            val children = header?.children
            children?.remove(it)
            if (children?.size == 0) {
                items.remove(header)
                val headerPos = items.indexOf(header)
                notifyItemRemoved(headerPos)
            }
        }
    }

    private fun getBy(id: Int): ListItem? {
        return items.firstOrNull { item -> item.movie?.id == id }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, addToFavouriteAction, removeFromFavoriteAction, shareAction)
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].type.value
    }

    abstract class BaseViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(
            item: ListItem,
            addToFavouriteAction: (Int?) -> Unit,
            removeFromFavoriteAction: (Int?) -> Unit,
            shareAction: (String) -> Unit
        )
    }

    class HeaderViewHolder(binding: HeaderLayoutBinding) : BaseViewHolder(binding.root) {
        private val title: TextView = binding.title

        override fun bind(
            item: ListItem,
            addToFavouriteAction: (Int?) -> Unit,
            removeFromFavoriteAction: (Int?) -> Unit,
            shareAction: (String) -> Unit
        ) {
            title.text = item.headerTitle
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
            item: ListItem,
            addToFavouriteAction: (Int?) -> Unit,
            removeFromFavoriteAction: (Int?) -> Unit,
            shareAction: (String) -> Unit
        ) {
            val movie = item.movie
            movie?.cover?.let { url ->
                imageLoader.load(url, cover)
            }
            Timber.d("Cover: ${item.movie?.cover}")
            title.text = movie?.title
            overview.text = movie?.overview
            rating.text = movie?.rating.toString()
            val action = if (movie?.isFavourite == true) {
                removeFromFavoriteAction
            } else {
                addToFavouriteAction
            }
            btnFavourites.setOnClickListener { action.invoke(movie?.id) }
            btnShare.setOnClickListener { shareAction.invoke("${movie?.title} \n ${movie?.overview}") }
            btnFavourites.text = if (item.movie?.isFavourite == true) {
                view.context.getString(R.string.remove_from_favourites)
            } else {
                view.context.getString(R.string.add_to_favourites)
            }
            star.isVisible = movie?.isFavourite ?: false
        }
    }
}
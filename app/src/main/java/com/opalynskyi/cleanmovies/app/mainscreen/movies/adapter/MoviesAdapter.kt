package com.opalynskyi.cleanmovies.app.mainscreen.movies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.opalynskyi.cleanmovies.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_item.view.*
import timber.log.Timber

class MoviesAdapter(
    private var items: MutableList<ListItem>,
    private var addToFavouriteAction: (Int?) -> Unit,
    private var shareAction: () -> Unit
) : RecyclerView.Adapter<MoviesAdapter.BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (ItemType.fromInt(viewType)) {
            ItemType.HEADER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.header_layout, parent, false)
                HeaderViewHolder(view)
            }
            ItemType.ITEM -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.movie_item, parent, false)
                MovieViewHolder(view)
            }
        }
    }

    fun refreshList(dataSource: List<ListItem>) {
        items.clear()
        items.addAll(dataSource)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, addToFavouriteAction, shareAction)
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].type.value
    }

    abstract class BaseViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(
            item: ListItem,
            addToFavouriteAction: (Int?) -> Unit,
            shareAction: () -> Unit
        )
    }

    class HeaderViewHolder(view: View) : BaseViewHolder(view) {
        private val title: TextView = view.title

        override fun bind(
            item: ListItem,
            addToFavouriteAction: (Int?) -> Unit,
            shareAction: () -> Unit
        ) {
            title.text = item.headerTitle
        }
    }


    class MovieViewHolder(view: View) : BaseViewHolder(view) {
        private val cover: ImageView = view.cover
        private val title: TextView = view.title
        private val overview: TextView = view.overview
        private val rating: TextView = view.rating
        private val btnFavourites: View = view.btnFavourites
        private val btnShare: View = view.btnShare

        override fun bind(item: ListItem, addToFavouriteAction: (Int?) -> Unit, shareAction: () -> Unit) {
            Picasso.get().load(item.movie?.cover).into(cover)
            Timber.d("Cover: ${item.movie?.cover}")
            title.text = item.movie?.title
            overview.text = item.movie?.overview
            rating.text = item.movie?.rating.toString()
            btnFavourites.setOnClickListener { addToFavouriteAction.invoke(item.movie?.id) }
            btnShare.setOnClickListener { shareAction.invoke() }
        }
    }
}
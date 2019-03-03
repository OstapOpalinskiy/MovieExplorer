package com.opalynskyi.cleanmovies.app.mainscreen.movies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.opalynskyi.cleanmovies.R
import kotlinx.android.synthetic.main.movie_item.view.*

class MoviesAdapter(
    private var items: MutableList<MovieItem>
) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view)
    }

    fun refreshList(dataSource: List<MovieItem>) {
        items.clear()
        items.addAll(dataSource)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
//        holder.cover = item.posterPath
        holder.title.text = item.title
        holder.overview.text = item.overview
        holder.rating.text = item.voteAverage.toString()
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].type.value
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val cover: ImageView = view.cover
        val title: TextView = view.title
        val overview: TextView = view.overview
        val rating: TextView = view.rating
    }
}
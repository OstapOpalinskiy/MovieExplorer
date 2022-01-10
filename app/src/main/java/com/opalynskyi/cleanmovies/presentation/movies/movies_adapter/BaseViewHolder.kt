package com.opalynskyi.cleanmovies.presentation.movies.movies_adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(item: MoviesListItem?)
}
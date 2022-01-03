package com.opalynskyi.cleanmovies.presentation

import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.opalynskyi.cleanmovies.DateTimeHelper
import com.opalynskyi.cleanmovies.R
import com.opalynskyi.cleanmovies.presentation.adapter.*

fun createListWithHeaders(dateTimeHelper: DateTimeHelper, movieItems: List<MovieItem>): List<MoviesListItem> {
    val listWithHeaders = mutableListOf<MoviesListItem>()
    val sortedMovieItems = movieItems.sortedWith(MovieItemComparator)
    var headerMonth = -1

    for (element in sortedMovieItems) {
        if (headerMonth == -1 || headerMonth != element.month) {
            headerMonth = element.month
            val header = MovieHeaderItem(dateTimeHelper.getHeaderDate(element.releaseDate))
            listWithHeaders.add(header)
        }
        listWithHeaders.add(element)
    }
    return listWithHeaders
}

fun share(context: Context, text: String) {
    val sharingIntent = Intent(Intent.ACTION_SEND)
    sharingIntent.type = "text/plain"
    sharingIntent.putExtra(Intent.EXTRA_TEXT, text)
    context.startActivity(Intent.createChooser(sharingIntent, "Share favourite movie"))
}

fun startAnimation(viewGroup: ViewGroup) {
    val resId = R.anim.layout_animation_right_to_left
    val animation = AnimationUtils.loadLayoutAnimation(viewGroup.context, resId)
    viewGroup.layoutAnimation = animation
}
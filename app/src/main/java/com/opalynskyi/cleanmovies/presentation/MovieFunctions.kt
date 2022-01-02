package com.opalynskyi.cleanmovies.presentation

import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.opalynskyi.cleanmovies.R
import com.opalynskyi.cleanmovies.DateTimeHelper
import com.opalynskyi.cleanmovies.presentation.adapter.ItemType
import com.opalynskyi.cleanmovies.presentation.adapter.ListItem
import com.opalynskyi.cleanmovies.presentation.adapter.MovieItem
import com.opalynskyi.cleanmovies.presentation.adapter.MovieItemComparator

fun createListWithHeaders(dateTimeHelper: DateTimeHelper, movieItems: List<MovieItem>): List<ListItem> {
    val listWithHeaders = mutableListOf<ListItem>()
    val sortedMovieItems = movieItems.sortedWith(MovieItemComparator)
    var headerMonth = -1
    var header: ListItem? = null
    for (element in sortedMovieItems) {
        if (headerMonth == -1 || headerMonth != element.month) {
            headerMonth = element.month
            header = ListItem(
                type = ItemType.HEADER,
                headerTitle = dateTimeHelper.getHeaderDate(element.releaseDate)
            )
            listWithHeaders.add(header)
        }
        val item = ListItem(type = ItemType.ITEM, movie = element, header = header)
        header?.children?.add(item)
        listWithHeaders.add(item)
    }
    return listWithHeaders
}

fun share(context: Context, text: String) {
    val sharingIntent = Intent(Intent.ACTION_SEND)
    sharingIntent.type = "text/plain"
    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, text)
    context.startActivity(Intent.createChooser(sharingIntent, "Share favourite movie"))
}

fun startAnimation(viewGroup: ViewGroup) {
    val resId = R.anim.layout_animation_right_to_left
    val animation = AnimationUtils.loadLayoutAnimation(viewGroup.context, resId)
    viewGroup.layoutAnimation = animation
}
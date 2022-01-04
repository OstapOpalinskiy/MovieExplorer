package com.opalynskyi.cleanmovies.presentation

import android.content.Intent
import androidx.fragment.app.Fragment
import com.opalynskyi.cleanmovies.DateTimeHelper
import com.opalynskyi.cleanmovies.R
import com.opalynskyi.cleanmovies.presentation.movies.movies_adapter.MovieHeaderItem
import com.opalynskyi.cleanmovies.presentation.movies.movies_adapter.MovieItem
import com.opalynskyi.cleanmovies.presentation.movies.movies_adapter.MovieItemComparator
import com.opalynskyi.cleanmovies.presentation.movies.movies_adapter.MoviesListItem

fun createListWithHeaders(
    dateTimeHelper: DateTimeHelper,
    movieItems: List<MovieItem>
): List<MoviesListItem> {
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

fun Fragment.share(text: String) {
    val sharingIntent = Intent(Intent.ACTION_SEND)
    sharingIntent.type = "text/plain"
    sharingIntent.putExtra(Intent.EXTRA_TEXT, text)
    requireActivity().startActivity(
        Intent.createChooser(
            sharingIntent,
            getString(R.string.share_chooser_title)
        )
    )
}
package com.opalynskyi.cleanmovies.app.mainscreen.movies.adapter

object MovieItemComparator : Comparator<MovieItem> {

    override fun compare(o1: MovieItem, o2: MovieItem): Int {
        val compareResult = o1.year.compareTo(o2.year)
        return if (compareResult == 0) {
            o1.month.compareTo(o2.month)
        } else {
            compareResult
        }
    }
}
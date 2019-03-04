package com.opalynskyi.cleanmovies.app.mainscreen.movies.adapter

object MovieItemComparator : Comparator<MovieItem> {

    override fun compare(o1: MovieItem, o2: MovieItem): Int {
        val yearCompareResult = o2.year.compareTo(o1.year)
        if (yearCompareResult != 0) {
            return yearCompareResult
        }

        val monthCompareResult = o2.month.compareTo(o1.month)

        if (monthCompareResult != 0) {
            return monthCompareResult
        }

        return o2.rating.compareTo(o1.rating)

    }
}
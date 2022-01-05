package com.opalynskyi.cleanmovies.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.opalynskyi.cleanmovies.presentation.movies.LatestMoviesFragment


class PagerAdapter constructor(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount() = PAGES_COUNT

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> LatestMoviesFragment.newInstanceLatest()
            1 -> LatestMoviesFragment.newInstanceFavourite()
            else -> throw RuntimeException("Unexpected position in view pager. Expected < $PAGES_COUNT, but was: $position")
        }
    }

    companion object {
        private const val PAGES_COUNT = 2
    }
}

package com.opalynskyi.cleanmovies.app.di.movies

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class PagerAdapter internal constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> AllMoviesFragment.newInstance()
            1 -> FavouriteMoviesFragment.newInstance()
            else -> throw RuntimeException("Unexpected position in view pager. Expected < $COUNT, but was: $position")
        }
    }

    override fun getCount(): Int {
        return COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "All"
            1 -> "Favourite"
            else -> throw RuntimeException("Unexpected position in view pager. Expected < $COUNT, but was: $position")
        }
    }

    companion object {
        private const val COUNT = 2
    }
}

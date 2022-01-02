package com.opalynskyi.cleanmovies.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.opalynskyi.cleanmovies.presentation.favourites.FavouriteMoviesFragment
import com.opalynskyi.cleanmovies.presentation.moviesList.AllMoviesFragment
import timber.log.Timber


class PagerAdapter constructor(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount() = PAGES_COUNT

    override fun createFragment(position: Int): Fragment {
        Timber.d("getItem")
        return when (position) {
            0 -> AllMoviesFragment.newInstance()
            1 -> FavouriteMoviesFragment.newInstance()
            else -> throw RuntimeException("Unexpected position in view pager. Expected < $PAGES_COUNT, but was: $position")
        }
    }

    companion object {
        private const val PAGES_COUNT = 2
    }
}

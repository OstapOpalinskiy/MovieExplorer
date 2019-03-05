package com.opalynskyi.cleanmovies.app.mainscreen

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.opalynskyi.cleanmovies.R
import com.opalynskyi.cleanmovies.app.CleanMoviesApplication
import com.opalynskyi.cleanmovies.app.mainscreen.movies.all.AllMoviesFragment
import com.opalynskyi.cleanmovies.app.mainscreen.movies.favourite.FavouriteMoviesFragment
import timber.log.Timber


class PagerAdapter internal constructor(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        Timber.d("getItem")
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
            0 -> CleanMoviesApplication.instance.getString(R.string.all)
            1 -> CleanMoviesApplication.instance.getString(R.string.favourite)
            else -> throw RuntimeException("Unexpected position in view pager. Expected < $COUNT, but was: $position")
        }
    }

    companion object {
        private const val COUNT = 2
    }
}

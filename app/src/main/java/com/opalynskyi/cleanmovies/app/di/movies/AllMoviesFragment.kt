package com.opalynskyi.cleanmovies.app.di.movies

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.opalynskyi.cleanmovies.R
import kotlinx.android.synthetic.main.movies_fragment_layout.*

class AllMoviesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.movies_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        root.setBackgroundColor(Color.BLUE)
    }

    companion object {
        @JvmStatic
        fun newInstance() = AllMoviesFragment()
    }
}
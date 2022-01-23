package com.opalynskyi.cleanmovies.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.opalynskyi.cleanmovies.databinding.EmptyFragmentLayoutBinding

class EmptyFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return EmptyFragmentLayoutBinding.inflate(inflater).root
    }
}
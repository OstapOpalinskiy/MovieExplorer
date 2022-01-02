package com.opalynskyi.cleanmovies.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.opalynskyi.cleanmovies.CleanMoviesApplication
import com.opalynskyi.cleanmovies.R
import com.opalynskyi.cleanmovies.databinding.ActivityMainBinding
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    private val binding get() = _binding!!
    private var _binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        CleanMoviesApplication.instance.getMainScreenComponent().inject(this)
        binding.viewPager.adapter = PagerAdapter(this)
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.all)
                1 -> tab.text = getString(R.string.favourite)
            }
        }.attach()
    }

    companion object {
        fun intent(context: Context) =
            Intent(context, MainActivity::class.java)
    }

    // is called when activity is closed, but is not when configuration changes
    override fun finish() {
        super.finish()
        CleanMoviesApplication.instance.releaseMainScreenComponent()
        CleanMoviesApplication.instance.releaseMoviesComponent()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

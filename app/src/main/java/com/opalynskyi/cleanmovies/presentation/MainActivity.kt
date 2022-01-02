package com.opalynskyi.cleanmovies.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.opalynskyi.cleanmovies.CleanMoviesApplication
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
        Timber.d("onCreate()")
        binding.viewPager.adapter = PagerAdapter(supportFragmentManager)
        binding.tabs.setupWithViewPager(binding.viewPager)
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
        Timber.d("onDestroy()")
        _binding = null
    }
}

package com.opalynskyi.cleanmovies.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.opalynskyi.cleanmovies.CleanMoviesApplication
import com.opalynskyi.cleanmovies.R
import com.opalynskyi.cleanmovies.databinding.ActivityMainBinding
import com.opalynskyi.cleanmovies.presentation.movies.favourites.FavouriteMoviesFragment
import com.opalynskyi.cleanmovies.presentation.movies.popular.PopularMoviesFragment


class MainActivity : AppCompatActivity() {

    private val binding get() = _binding!!
    private var _binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        CleanMoviesApplication.instance.getMainScreenComponent().inject(this)
        val popularFragment = PopularMoviesFragment.newInstance()
        val favouriteFragment = FavouriteMoviesFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, popularFragment)
            .commit()
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.popular -> supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, popularFragment)
                    .commit()
                R.id.favourite -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, favouriteFragment)
                        .commit()
                }
                R.id.settings -> {
                    Toast.makeText(this, "WIP", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
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

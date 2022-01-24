package com.opalynskyi.cleanmovies.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.opalynskyi.cleanmovies.CleanMoviesApplication
import com.opalynskyi.cleanmovies.R
import com.opalynskyi.cleanmovies.databinding.ActivityMainBinding
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var navigator: Navigator

    private val navigationController: NavController by lazy {
        val host: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        host.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        CleanMoviesApplication.instance.getMainScreenComponent(navigationController).inject(this)
        navigator.navigate(ScreenDestination.Popular)
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.popular -> {
                    navigator.navigate(ScreenDestination.Popular)
                }
                R.id.favourite -> {
                    navigator.navigate(ScreenDestination.Favourite)
                }
                R.id.settings -> {
                    Toast.makeText(this, "WIP", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
    }

    override fun onPause() {
        if (isFinishing) {
            CleanMoviesApplication.instance.releaseMainScreenComponent()
        }
        super.onPause()
    }
}

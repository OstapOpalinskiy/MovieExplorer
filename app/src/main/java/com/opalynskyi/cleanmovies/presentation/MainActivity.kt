package com.opalynskyi.cleanmovies.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.opalynskyi.cleanmovies.CleanMoviesApplication
import com.opalynskyi.cleanmovies.R
import com.opalynskyi.cleanmovies.databinding.ActivityMainBinding
import com.opalynskyi.cleanmovies.presentation.screen_navigation.Navigator
import com.opalynskyi.cleanmovies.presentation.screen_navigation.ScreenDestination
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private val binding get() = _binding!!
    private var _binding: ActivityMainBinding? = null

    @Inject
    lateinit var navigator: Navigator

    private val navigationController: NavController by lazy {
        val host: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        host.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        CleanMoviesApplication.instance.getMainScreenComponent(navigationController).inject(this)
        navigator.navigate(ScreenDestination.Popular)
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.popular -> {
                    navigator.navigate(ScreenDestination.Popular)
                }
                R.id.favourite -> {
                    navigationController.navigate(R.id.favourite_fragment)
                }
                R.id.settings -> {
                    Toast.makeText(this, "WIP", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
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

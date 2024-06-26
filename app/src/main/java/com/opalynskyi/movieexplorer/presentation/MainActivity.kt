package com.opalynskyi.movieexplorer.presentation

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.opalynskyi.movieexplorer.R
import com.opalynskyi.movieexplorer.databinding.ActivityMainBinding
import com.opalynskyi.movieexplorer.di.AppComponent
import javax.inject.Inject
import javax.inject.Provider

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var navigatorProvider: Provider<Navigator>

    private val navigator by lazy {
        navigatorProvider.get()
    }

    private val navigationController: NavController by lazy {
        val host: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        host.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppComponent.get()
            .mainScreenComponent(MainScreenModule(navigationController))
            .inject(this)
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            handleBottomNavigation(menuItem)
        }
        if (savedInstanceState == null) {
            navigator.navigate(ScreenDestination.Popular)
        }
    }

    private fun handleBottomNavigation(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.popular -> {
                navigator.navigate(ScreenDestination.Popular)
            }

            R.id.favourite -> {
                navigator.navigate(ScreenDestination.Favourite)
            }
        }
        return true
    }
}

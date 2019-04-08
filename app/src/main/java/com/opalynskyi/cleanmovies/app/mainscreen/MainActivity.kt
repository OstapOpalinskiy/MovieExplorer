package com.opalynskyi.cleanmovies.app.mainscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.opalynskyi.cleanmovies.R
import com.opalynskyi.cleanmovies.app.CleanMoviesApplication
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject


class MainActivity : AppCompatActivity(), MainScreenContract.View {

    @Inject
    lateinit var presenter: MainScreenContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CleanMoviesApplication.instance.getMainScreenComponent().inject(this)
        presenter.bind(this)
        Timber.d("onCreate()")
        presenter.loadUserPhoto()
        viewPager.adapter = PagerAdapter(supportFragmentManager)
        tabs.setupWithViewPager(viewPager)
        // test changes
        // test change
        // test change

    }

    override fun showProgress() {
    }

    override fun showPhoto(photoUrl: String) {
        Picasso.get().load(photoUrl).into(profilePhoto)
        Timber.d("showPhoto: $photoUrl")
    }

    override fun showError(errorMsg: String) {

    }

    companion object {
        fun intent(context: Context) =
            Intent(context, MainActivity::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbind()
        Timber.d("onDestroy()")
    }

    // is called when activity is closed, but is not when configuration changes
    override fun finish() {
        super.finish()
        CleanMoviesApplication.instance.releaseMainScreenComponent()
        CleanMoviesApplication.instance.releaseMoviesComponent()
    }
}

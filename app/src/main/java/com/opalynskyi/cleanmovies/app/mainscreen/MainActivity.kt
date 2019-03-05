package com.opalynskyi.cleanmovies.app.mainscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.opalynskyi.cleanmovies.R
import com.opalynskyi.cleanmovies.app.CleanMoviesApplication
import com.opalynskyi.cleanmovies.app.api.MoviesApi
import com.opalynskyi.cleanmovies.app.database.MoviesDao
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), MainScreenContract.View {

    @Inject
    lateinit var presenter: MainScreenContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CleanMoviesApplication.instance.getMainScreenComponent().inject(this)
        presenter.bind(this)
        presenter.loadUserPhoto()
        viewPager.adapter = PagerAdapter(supportFragmentManager)
        tabs.setupWithViewPager(viewPager)
        val c = Calendar.getInstance()
        c.timeInMillis = System.currentTimeMillis()
        Timber.d("Month: ${c.get(Calendar.MONTH)}")
    }

    override fun showProgress() {
    }

    override fun showPhoto(photoUrl: String) {
        Picasso.get().load(photoUrl).into(profilePhoto)
    }

    override fun showError(errorMsg: String) {

    }

    companion object {
        fun intent(context: Context) =
            Intent(context, MainActivity::class.java)
    }
}

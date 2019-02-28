package com.opalynskyi.cleanmovies.app.movies

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.opalynskyi.cleanmovies.R
import com.opalynskyi.cleanmovies.app.CleanMoviesApplication
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movies.*
import javax.inject.Inject

class MoviesActivity : AppCompatActivity(), MoviesContract.View {

    @Inject
    lateinit var presenter: MoviesContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        CleanMoviesApplication.instance.getMoviesComponent().inject(this)
        presenter.bind(this)
        presenter.loadUserPhoto()
    }

    override fun showPhoto(photoUrl: String) {
        Picasso.get().load(photoUrl).into(image)
    }

    override fun showError(errorMsg: String) {
    }

    companion object {
        fun intent(context: Context) =
            Intent(context, MoviesActivity::class.java)
    }
}

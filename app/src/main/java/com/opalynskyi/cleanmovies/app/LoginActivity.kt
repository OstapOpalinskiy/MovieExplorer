package com.opalynskyi.cleanmovies.app


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.opalynskyi.cleanmovies.app.login.FacebookHelper
import com.squareup.picasso.Picasso
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_login.*
import timber.log.Timber
import javax.inject.Inject


class LoginActivity : AppCompatActivity() {
    @Inject
    lateinit var fbHelper: FacebookHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.opalynskyi.cleanmovies.R.layout.activity_login)
        CleanMoviesApplication.instance.getLoginComponent(this).inject(this)
        btnLogin.setOnClickListener {
            fbHelper.login().subscribeBy(
                onComplete = {
                    fbHelper.getPhoto()
                        .subscribeBy(
                            onSuccess = { url ->
                                Timber.d("Picture url: $url")
                                Picasso.get().load(url).into(image)
                            },
                            onError = {
                                Timber.d("Picture url error: ${it.message}")
                            }
                        )
                },
                onError = { Timber.d("Login error: ${it.message}") }
            )
        }
    }

    // TODO: don't know how to do this in a cleaner way :(
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        fbHelper.notifyLoginResult(requestCode, resultCode, data)
    }
}

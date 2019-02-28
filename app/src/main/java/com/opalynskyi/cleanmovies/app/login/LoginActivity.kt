package com.opalynskyi.cleanmovies.app.login


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.opalynskyi.cleanmovies.app.CleanMoviesApplication
import com.opalynskyi.cleanmovies.app.movies.MoviesActivity
import com.opalynskyi.cleanmovies.core.domain.login.LoginResultWrapper
import kotlinx.android.synthetic.main.activity_login.*
import timber.log.Timber
import javax.inject.Inject


class LoginActivity : AppCompatActivity(), LoginContract.View {

    @Inject
    lateinit var presenter: LoginContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.opalynskyi.cleanmovies.R.layout.activity_login)
        CleanMoviesApplication.instance.getLoginComponent(this).inject(this)
        presenter.bind(this)
        btnLogin.setOnClickListener {
            presenter.login()
        }
    }

    override fun showLoginError(errorMsg: String) {
        Timber.d("Login error: $errorMsg")
    }

    override fun proceedFlow() {
        startActivity(MoviesActivity.intent(this))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.onActivityResult(
            LoginResultWrapper(
                requestCode,
                resultCode,
                data
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbind()
    }
}

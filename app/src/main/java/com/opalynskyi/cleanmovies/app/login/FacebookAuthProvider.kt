package com.opalynskyi.cleanmovies.app.login

import android.app.Activity
import android.widget.Toast
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.opalynskyi.cleanmovies.core.domain.login.AuthProvider
import com.opalynskyi.cleanmovies.core.domain.login.LoginResultWrapper
import com.opalynskyi.cleanmovies.core.domain.login.exception.LoginException
import io.reactivex.Completable
import io.reactivex.subjects.CompletableSubject
import timber.log.Timber
import java.util.*


class FacebookAuthProvider(val activity: Activity) : AuthProvider {

    private var callbackManager: CallbackManager? = null

    init {
        callbackManager = CallbackManager.Factory.create()
    }


    override fun login(): Completable {
        val resultSubject: CompletableSubject = CompletableSubject.create()
        if (FacebookSdk.isInitialized()) {
            performLogin(resultSubject)
        } else {
            FacebookSdk.sdkInitialize(activity) {
                performLogin(resultSubject)
            }
        }
        return resultSubject.hide()
    }

    override fun notifyResult(result: LoginResultWrapper) {
        callbackManager?.onActivityResult(result.requestCode, result.resultCode, result.data)
            ?: Timber.w("notifyLoginResult() called before login()")
    }

    override fun logout() {
        LoginManager.getInstance().logOut()
    }

    override fun isLoggedin(): Boolean {
        return AccessToken.getCurrentAccessToken() != null
    }

    private fun performLogin(resultSubject: CompletableSubject) {
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    resultSubject.onComplete()
                    Toast.makeText(activity, "success", Toast.LENGTH_LONG).show()

                }

                override fun onCancel() {
                    resultSubject.onError(LoginException("Login canceled"))
                    Toast.makeText(activity, "cancel", Toast.LENGTH_LONG).show()
                }

                override fun onError(exception: FacebookException) {
                    resultSubject.onError(
                        LoginException(
                            exception.message ?: "Login error occurred"
                        )
                    )
                    Toast.makeText(activity, exception.message, Toast.LENGTH_LONG).show()
                }
            })
        LoginManager.getInstance().logInWithReadPermissions(activity, PERMISSIONS_LIST)
    }

    companion object {
        private val PERMISSIONS_LIST = Arrays.asList("public_profile", "user_friends")
    }
}
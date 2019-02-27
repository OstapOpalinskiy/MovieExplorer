package com.opalynskyi.cleanmovies.app.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.subjects.CompletableSubject
import io.reactivex.subjects.SingleSubject
import timber.log.Timber
import java.util.*


class FacebookHelper(val activity: Activity) : AccountHelper {

    private var accessToken: AccessToken? = null
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

    fun notifyLoginResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager?.onActivityResult(requestCode, resultCode, data)
            ?: Timber.w("notifyLoginResult() called before login()")
    }

    override fun logout() {
        LoginManager.getInstance().logOut()
        accessToken = null
    }

    private fun performLogin(resultSubject: CompletableSubject) {
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    resultSubject.onComplete()
                    accessToken = loginResult.accessToken
                }

                override fun onCancel() {
                    resultSubject.onError(LoginException("Login canceled"))
                    Toast.makeText(activity, "Login Cancel", Toast.LENGTH_LONG).show()
                }

                override fun onError(exception: FacebookException) {
                    resultSubject.onError(LoginException(exception.message ?: "Login error occured"))
                    Toast.makeText(activity, exception.message, Toast.LENGTH_LONG).show()
                }
            })
        LoginManager.getInstance().logInWithReadPermissions(activity, PERMISSIONS_LIST)
    }


    override fun getPhoto(): Single<String> {
        val resultSubject = SingleSubject.create<String>()
        val params = Bundle()
        params.putString("fields", "picture.type(large)")
        GraphRequest(AccessToken.getCurrentAccessToken(), "me", params, HttpMethod.GET,
            GraphRequest.Callback { response ->
                if (response != null) {
                    try {
                        val data = response.jsonObject
                        if (data.has("picture")) {
                            val profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url")
                            Log.d("TAG1", "picture: $profilePicUrl")
                            resultSubject.onSuccess(profilePicUrl)
                        } else {
                            resultSubject.onError(Exception("Error loading user's photo"))
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        resultSubject.onError(e)
                    }

                }
            }).executeAsync()
        return resultSubject.hide()
    }

    companion object {
        private val PERMISSIONS_LIST = Arrays.asList("public_profile", "user_friends")
    }
}
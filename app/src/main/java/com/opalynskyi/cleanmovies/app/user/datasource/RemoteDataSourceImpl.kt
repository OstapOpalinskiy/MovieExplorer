package com.opalynskyi.cleanmovies.app.user.datasource

import android.os.Bundle
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.HttpMethod
import com.opalynskyi.cleanmovies.core.data.user.RemoteUserDataSource
import com.opalynskyi.cleanmovies.core.data.user.User
import io.reactivex.Single
import io.reactivex.subjects.SingleSubject
import timber.log.Timber

class RemoteDataSourceImpl : RemoteUserDataSource {

    override fun getUser(): Single<User> {
        val resultSubject = SingleSubject.create<User>()
        val params = Bundle()
        params.putString("fields", "picture.type(large)")
        GraphRequest(AccessToken.getCurrentAccessToken(), "me", params, HttpMethod.GET,
            GraphRequest.Callback { response ->
                if (response != null) {
                    try {
                        val data = response.jsonObject
                        if (data.has("picture")) {
                            val profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url")
                            Timber.d("profile photo: $profilePicUrl")
                            resultSubject.onSuccess(User(profilePicUrl))
                        } else {
                            resultSubject.onError(Exception("Error loading user's photo"))
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        resultSubject.onError(e)
                    }
                }
            }).executeAsync()
        return resultSubject
    }
}
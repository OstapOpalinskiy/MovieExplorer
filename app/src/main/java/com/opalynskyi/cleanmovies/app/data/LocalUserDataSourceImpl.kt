package com.opalynskyi.cleanmovies.app.data

import android.content.SharedPreferences
import com.google.gson.Gson
import com.opalynskyi.cleanmovies.app.domain.entities.User
import timber.log.Timber

class LocalUserDataSourceImpl(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : LocalUserDataSource {
    override var currentUser: User?
        get() {
            val userJson = sharedPreferences.getString(PREF_CURRENT_USER, "")
            Timber.d("get user Json  from local storage: $userJson")
            var user: User? = null
            try {
                user = gson.fromJson(userJson, User::class.java)
                Timber.d("parsed user: $user")
            } catch (ex: Exception) {
                Timber.d("Can not retrieve user from local storage: ${ex.message}")
            }
            return user
        }
        set(user) {
            sharedPreferences.edit().putString(PREF_CURRENT_USER, gson.toJson(user)).apply()
            Timber.d("User saved locally")
        }

    override fun clearCurrentUser() {
        sharedPreferences.edit().putString(PREF_CURRENT_USER, "").apply()
    }

    companion object {
        private const val PREF_CURRENT_USER = "pref_current_user"
    }
}
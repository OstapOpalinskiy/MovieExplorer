package com.opalynskyi.cleanmovies.core.domain.login

import android.content.Intent

data class LoginResultWrapper(val requestCode: Int, val resultCode: Int, val data: Intent?)
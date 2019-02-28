package com.opalynskyi.cleanmovies.app

import com.google.gson.Gson

fun Any.toJson(): String = Gson().toJson(this)


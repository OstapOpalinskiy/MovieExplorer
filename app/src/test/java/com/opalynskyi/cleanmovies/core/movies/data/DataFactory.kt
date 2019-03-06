package com.opalynskyi.cleanmovies.core.movies.data

import java.util.*
import java.util.concurrent.ThreadLocalRandom

object DataFactory {

    fun randomString(): String {
        return UUID.randomUUID().toString()
    }

    fun randomInt(): Int {
        return ThreadLocalRandom.current().nextInt(Int.MIN_VALUE, Int.MAX_VALUE)
    }

    fun randomLong(): Long {
        return ThreadLocalRandom.current().nextLong(Long.MIN_VALUE, Long.MAX_VALUE)
    }

    fun randomBoolean(): Boolean {
        return Math.random() < 0.5
    }
}
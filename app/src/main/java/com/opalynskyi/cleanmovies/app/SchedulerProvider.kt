package com.opalynskyi.cleanmovies.app

import io.reactivex.Scheduler

interface SchedulerProvider {

    fun mainThread(): Scheduler

    fun backgroundThread(): Scheduler
}
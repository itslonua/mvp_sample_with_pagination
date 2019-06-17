package com.ok7apps.farmdropexample.core

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object DefaultSchedulersProvider : SchedulerProvider {

    override fun io() = Schedulers.io()

    override fun mainThread(): Scheduler = AndroidSchedulers.mainThread()

}
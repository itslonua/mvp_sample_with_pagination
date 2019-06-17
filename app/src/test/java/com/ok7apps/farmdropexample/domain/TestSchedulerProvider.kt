package com.ok7apps.farmdropexample.domain

import com.ok7apps.farmdropexample.core.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

object TestSchedulersProvider : SchedulerProvider {

    override fun io() = Schedulers.trampoline()

    override fun mainThread(): Scheduler = Schedulers.trampoline()

}
package com.ok7apps.farmdropexample.core

import io.reactivex.Scheduler

interface SchedulerProvider {

    fun io(): Scheduler

    fun mainThread(): Scheduler

}
package com.ok7apps.farmdropexample

import android.app.Activity
import android.app.Application
import android.util.Log
import com.ok7apps.farmdropexample.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import javax.inject.Inject

class FarmDropApp : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        RxJavaPlugins.setErrorHandler {
            if (it is UndeliverableException) {
                Log.d("RxJavaPlugins", "on UndeliverableException + ${it.message}")
                return@setErrorHandler
            }
            Thread.currentThread().uncaughtExceptionHandler.uncaughtException(Thread.currentThread(), it)
        }

        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this)

    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityInjector
    }

}
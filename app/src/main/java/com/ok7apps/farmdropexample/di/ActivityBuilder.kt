package com.ok7apps.farmdropexample.di

import com.ok7apps.farmdropexample.ui.main.MainActivity
import com.ok7apps.farmdropexample.ui.main.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindMainActivity(): MainActivity

}
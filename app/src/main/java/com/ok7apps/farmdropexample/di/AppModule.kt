package com.ok7apps.farmdropexample.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.ok7apps.farmdropexample.FarmDropApp
import com.ok7apps.farmdropexample.core.DefaultSchedulersProvider
import com.ok7apps.farmdropexample.core.SchedulerProvider
import com.ok7apps.farmdropexample.core.image.DefaultImageLoaderProxy
import com.ok7apps.farmdropexample.core.image.ImageLoaderProxy
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Module(includes = [AndroidInjectionModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideApplication(app: FarmDropApp): Context = app

    @Provides
    fun providesSchedulesFactory(): SchedulerProvider = DefaultSchedulersProvider

    @Provides
    fun provideGlideRequest(context: Context) = Glide.with(context)

    @Provides
    fun provideImageLoaderProxy(requestManager: RequestManager): ImageLoaderProxy =
            DefaultImageLoaderProxy(requestManager)
}
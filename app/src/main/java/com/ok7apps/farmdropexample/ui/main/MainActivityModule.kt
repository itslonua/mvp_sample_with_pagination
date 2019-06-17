package com.ok7apps.farmdropexample.ui.main

import com.ok7apps.farmdropexample.core.SchedulerProvider
import com.ok7apps.farmdropexample.core.image.ImageLoaderProxy
import com.ok7apps.farmdropexample.domain.FetcherUseCase
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun provideMainPresenter(
            fetcherUseCase: FetcherUseCase,
            schedulers: SchedulerProvider) =
            MainPresenter(
                    fetcherUseCase,
                    schedulers
            )

    @Provides
    fun providerMainAdapter(imageLoaderProxy: ImageLoaderProxy) =
            MainActivityAdapter(imageLoaderProxy)
}

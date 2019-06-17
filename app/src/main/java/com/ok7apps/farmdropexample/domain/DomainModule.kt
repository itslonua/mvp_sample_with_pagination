package com.ok7apps.farmdropexample.domain

import com.ok7apps.farmdropexample.core.SchedulerProvider
import com.ok7apps.farmdropexample.domain.paginator.ProducerPaginator
import com.ok7apps.farmdropexample.domain.producer.ProducerRepository
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideFetcherPaginator(repository: ProducerRepository,
                                schedulerProvider: SchedulerProvider) =
            ProducerPaginator(repository, schedulerProvider)

    @Provides
    fun provideFetcherUseCase(paginator: ProducerPaginator) = FetcherUseCase(paginator)

}
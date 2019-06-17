package com.ok7apps.farmdropexample.domain.paginator

import com.ok7apps.farmdropexample.core.Resource
import com.ok7apps.farmdropexample.core.SchedulerProvider
import com.ok7apps.farmdropexample.data.local.EmptyCacheException
import com.ok7apps.farmdropexample.data.model.paginator.ResourceWithPaginationState
import com.ok7apps.farmdropexample.domain.producer.Producer
import com.ok7apps.farmdropexample.domain.producer.ProducerRepository
import io.reactivex.Flowable


class ProducerPaginator(
        private val repository: ProducerRepository,
        private val schedulerProvider: SchedulerProvider
) : AbstractSimplePaginator<Producer>(schedulerProvider) {

    override fun onFetchNextData(page: Int, limit: Int): Flowable<ResourceWithPaginationState<List<Producer>>> {
        return repository.getNetworkProducers(page, limit)
                .toFlowable()
                .publish { networkResponse ->
                    Flowable.merge(
                            networkResponse,
                            repository.getCachedProducers(page).takeUntil(networkResponse))
                }
                .onErrorResumeNext(
                        repository.getCachedProducers(page)
                                .map {
                                    if (it.resource.data?.isEmpty() == true) {
                                        ResourceWithPaginationState(
                                                FetcherSource.LOCAL,
                                                page,
                                                Resource.error(EmptyCacheException()))
                                    } else {
                                        it
                                    }
                                })
                .subscribeOn(schedulerProvider.io())
    }

}
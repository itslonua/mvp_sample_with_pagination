package com.ok7apps.farmdropexample.data.network

import com.ok7apps.farmdropexample.core.Resource
import com.ok7apps.farmdropexample.core.SchedulerProvider
import com.ok7apps.farmdropexample.data.model.paginator.ResourceWithPaginationState
import com.ok7apps.farmdropexample.data.model.producer.ProducerMapper
import com.ok7apps.farmdropexample.domain.paginator.FetcherSource
import com.ok7apps.farmdropexample.domain.producer.Producer
import io.reactivex.Single

class NetworkDataSourceImpl(
        private val producersApi: ProducersApi,
        private val schedulers: SchedulerProvider
) : RemoteDataSource {

    override fun getProducers(page: Int, limit: Int): Single<ResourceWithPaginationState<List<Producer>>> {
        return producersApi.getProducers(page, limit)
                .map { result ->
                    val items = ProducerMapper.fromNetwork(result.response, result.pagination.current)
                    val completed = result.pagination.next == null
                    ResourceWithPaginationState(FetcherSource.REMOTE, page, Resource.success(items), completed)
                }
                .subscribeOn(schedulers.io())
    }

}
package com.ok7apps.farmdropexample.data.model.producer


import com.ok7apps.farmdropexample.data.local.LocalDataSource
import com.ok7apps.farmdropexample.data.model.paginator.ResourceWithPaginationState
import com.ok7apps.farmdropexample.data.network.RemoteDataSource
import com.ok7apps.farmdropexample.domain.paginator.FetcherSource
import com.ok7apps.farmdropexample.domain.producer.Producer
import com.ok7apps.farmdropexample.domain.producer.ProducerRepository
import io.reactivex.Flowable
import io.reactivex.Single

class ProducerRepositoryImpl(
        private val remoteDataSource: RemoteDataSource,
        private val localDataSource: LocalDataSource
) : ProducerRepository {

    override fun getNetworkProducers(page: Int, limit: Int): Single<ResourceWithPaginationState<List<Producer>>> {
       return remoteDataSource.getProducers(page, limit)
               .doOnSuccess {
                   it.resource.data?.let { data -> updateLocalStorage(data, page) }
               }.map {
                   ResourceWithPaginationState(FetcherSource.REMOTE, page, it.resource,  it.completed)
               }
    }

    override fun getCachedProducers(page: Int): Flowable<ResourceWithPaginationState<List<Producer>>> {
        return localDataSource.getProducers(page)
    }

    private fun updateLocalStorage(items: List<Producer>, page: Int) {
        localDataSource.insertOrUpdate(items, page)
    }

}
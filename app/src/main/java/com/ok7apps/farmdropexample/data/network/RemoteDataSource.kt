package com.ok7apps.farmdropexample.data.network

import com.ok7apps.farmdropexample.data.model.paginator.ResourceWithPaginationState
import com.ok7apps.farmdropexample.domain.producer.Producer
import io.reactivex.Single

interface RemoteDataSource {

    fun getProducers(page: Int, count: Int): Single<ResourceWithPaginationState<List<Producer>>>

}
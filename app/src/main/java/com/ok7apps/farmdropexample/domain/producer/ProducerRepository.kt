package com.ok7apps.farmdropexample.domain.producer

import com.ok7apps.farmdropexample.data.model.paginator.ResourceWithPaginationState
import io.reactivex.Flowable
import io.reactivex.Single

interface ProducerRepository {

    fun getNetworkProducers(page: Int, limit: Int): Single<ResourceWithPaginationState<List<Producer>>>

    fun getCachedProducers(page: Int): Flowable<ResourceWithPaginationState<List<Producer>>>

}
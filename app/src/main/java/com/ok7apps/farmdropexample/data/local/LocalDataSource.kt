package com.ok7apps.farmdropexample.data.local

import com.ok7apps.farmdropexample.data.model.paginator.ResourceWithPaginationState
import com.ok7apps.farmdropexample.domain.producer.Producer
import io.reactivex.Flowable

interface LocalDataSource {

    fun getProducers(page: Int): Flowable<ResourceWithPaginationState<List<Producer>>>

    fun insertOrUpdate(items: List<Producer>, page: Int)

    fun clear()

}
package com.ok7apps.farmdropexample.data.local

import com.ok7apps.farmdropexample.core.Resource
import com.ok7apps.farmdropexample.core.SchedulerProvider
import com.ok7apps.farmdropexample.data.local.model.ProducerEntityMapper
import com.ok7apps.farmdropexample.data.model.paginator.ResourceWithPaginationState
import com.ok7apps.farmdropexample.domain.paginator.FetcherSource
import com.ok7apps.farmdropexample.domain.producer.Producer
import io.reactivex.Flowable

class LocalDataSourceImpl(
        private val applicationDataBase: ApplicationDataBase,
        private val schedulers: SchedulerProvider
) : LocalDataSource {

    override fun getProducers(page: Int): Flowable<ResourceWithPaginationState<List<Producer>>> {
        return applicationDataBase.dao()
                .getProducers(page)
                .map {
                    it.map(ProducerEntityMapper::fromDatabase)
                }
                .map {
                    ResourceWithPaginationState(FetcherSource.LOCAL, page, Resource.success(it))
                }
                .observeOn(schedulers.io())
    }

    override fun insertOrUpdate(items: List<Producer>, page: Int) {
        val entity = items.mapIndexed { order, producer ->
            ProducerEntityMapper.toDatabase(producer, page, order)
        }

        applicationDataBase.dao().updateProducerRange(entity, page)
    }

    override fun clear() {
        applicationDataBase.dao().clear()
    }

}
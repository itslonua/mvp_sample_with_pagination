package com.ok7apps.farmdropexample.data.model.paginator

import com.ok7apps.farmdropexample.core.Resource
import com.ok7apps.farmdropexample.domain.paginator.FetcherSource


data class ResourceWithPaginationState<T> (
        val source: FetcherSource = FetcherSource.IDLE,
        val page: Int = -1,
        val resource: Resource<T> = Resource.loading(),
        val completed: Boolean? = null
)
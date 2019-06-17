package com.ok7apps.farmdropexample.domain

import com.ok7apps.farmdropexample.domain.paginator.ProducerPaginator

class FetcherUseCase (private val paginator: ProducerPaginator) {

    fun observe() = paginator.subject()

    fun forward() {
        paginator.forward()
    }

    fun refresh() {
        paginator.refresh()
    }

    fun repeat() {
        paginator.repeat()
    }

    fun dispose() {
        paginator.dispose()
    }

}
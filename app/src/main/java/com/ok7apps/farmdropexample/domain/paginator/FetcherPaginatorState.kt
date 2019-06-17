package com.ok7apps.farmdropexample.domain.paginator

import com.ok7apps.farmdropexample.domain.producer.Producer


data class FetcherPaginatorState<T>(
        val nextPage: Int = 1,
        val limit: Int = LIMIT,
        val reload: Boolean = false,
        val results: List<T> = listOf(),
        val fetchCompleted: Boolean = false,
        val fetchError: Throwable? = null
) {
    companion object {

        const val LIMIT = 10

        fun <T> idle() =  FetcherPaginatorState<T>()

    }

    override fun toString(): String {
        return "FetcherPaginatorState(nextPage=$nextPage, limit=$limit, reload=$reload, " +
                "resultsSize=${results.size}, fetchCompleted=$fetchCompleted, fetchError=$fetchError)"
    }

}
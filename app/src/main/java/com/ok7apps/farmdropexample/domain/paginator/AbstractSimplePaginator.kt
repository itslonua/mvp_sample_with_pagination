package com.ok7apps.farmdropexample.domain.paginator

import com.ok7apps.farmdropexample.core.Resource
import com.ok7apps.farmdropexample.core.SchedulerProvider
import com.ok7apps.farmdropexample.data.model.paginator.ResourceWithPaginationState
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject

abstract class AbstractSimplePaginator<T>(
        private val schedulerProvider: SchedulerProvider
) {

    private val state = BehaviorSubject.createDefault(FetcherPaginatorState.idle<T>())
    private var compositeDisposable = CompositeDisposable()

    fun subject() = state.toSerialized()

    fun dispose() {
        compositeDisposable.dispose()
    }

    fun forward() {
        subject().take(1)
                .toFlowable(BackpressureStrategy.DROP)
                .compose(paginationTransformer())
                .take(2)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.io())
                .subscribe(state::onNext)
                .addTo(compositeDisposable)
    }

    fun refresh() {
        Flowable.just(FetcherPaginatorState.idle<T>())
                .compose(paginationTransformer())
                .take(2)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.io())
                .subscribe(state::onNext)
                .addTo(compositeDisposable)

    }

    fun repeat() {
        //stub
    }

    private fun paginationTransformer(): FlowableTransformer<FetcherPaginatorState<T>, FetcherPaginatorState<T>> {
        return FlowableTransformer { upstream ->
            upstream.flatMap({ state ->
                when {
                    !state.fetchCompleted -> onFetchNextData(state.nextPage, state.limit)
                    else -> Flowable.empty()
                }
            }) { statePagination, chunkPagination -> Pair(statePagination, chunkPagination) }
                    .map { paginationAndChunk ->
                        val pagination = paginationAndChunk.first
                        val chunk = paginationAndChunk.second
                        val resource = chunk.resource
                        when (resource.status) {
                            Resource.Status.SUCCESS ->
                                pagination.copy(
                                        nextPage = pagination.nextPage + 1,
                                        results = pagination.results + resource.data!!,
                                        fetchCompleted = chunk.completed ?: pagination.fetchCompleted,
                                        fetchError = null
                                )

                            Resource.Status.ERROR ->
                                pagination.copy(
                                        fetchError = resource.throwable,
                                        fetchCompleted = false
                                )

                            else -> pagination
                        }
                    }
                    .subscribeOn(schedulerProvider.io())
        }

    }

    abstract fun onFetchNextData(page: Int, limit: Int): Flowable<ResourceWithPaginationState<List<T>>>

}
package com.ok7apps.farmdropexample.ui.main

import android.annotation.SuppressLint
import com.ok7apps.farmdropexample.core.MvpPresenter
import com.ok7apps.farmdropexample.core.Resource
import com.ok7apps.farmdropexample.core.SchedulerProvider
import com.ok7apps.farmdropexample.domain.FetcherUseCase
import java.util.concurrent.atomic.AtomicBoolean

class MainPresenter(
        private val fetcherUseCase: FetcherUseCase,
        private val schedulers: SchedulerProvider
) : MvpPresenter<MainView>() {

    private var forceReload = AtomicBoolean(false)

    init {
        subscribeToPagination()
    }

    @SuppressLint("CheckResult")
    private fun subscribeToPagination() {
        fetcherUseCase.observe()
                .map {
                    val mappedItems = it.results.map(MainItemMapper::map)
                    val throwable = it.fetchError
                    MainViewState(Resource.success(mappedItems), throwable, forceReload.get())
                }
                .startWith(MainViewState(Resource.loading(), null))
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.mainThread())
                .compose(bindObservableLifecycleTransformer())
                .subscribe { resource -> viewState?.renderView(resource) }
    }

    fun forward() {
        forceReload.getAndSet(false)
        fetcherUseCase.forward()
    }

    fun refresh() {
        forceReload.getAndSet(true)
        fetcherUseCase.refresh()
    }

    fun onDestroy() {
        super.onDetach()
        fetcherUseCase.dispose()
    }

}
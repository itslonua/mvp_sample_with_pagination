package com.ok7apps.farmdropexample.core

import io.reactivex.BackpressureStrategy
import io.reactivex.FlowableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.subjects.PublishSubject

abstract class MvpPresenter<View : MvpView> {

    @Volatile
    protected var viewState: View? = null

    private var disposeEvent = PublishSubject.create<Boolean>()

    protected fun <T> bindFlowableLifecycleTransformer(): FlowableTransformer<T, T> {
        return FlowableTransformer { upstream ->
            upstream.takeUntil(disposeEvent.toFlowable(BackpressureStrategy.DROP))
        }
    }

    protected fun <T> bindObservableLifecycleTransformer(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.takeUntil(disposeEvent)
        }
    }


    fun attachView(view: View) {
        disposeEvent = PublishSubject.create<Boolean>()
        viewState?.let {
            throw RuntimeException("Previous View should be unbinded :$view")
        } ?: run {
            viewState = view
            onFirstViewAttach()
        }
    }

    fun detachView(view: View) {
        if (viewState != view) {
            throw RuntimeException("View to bind and current View not equals :$view")
        } else {
            viewState = null
        }
        disposeEvent.onNext(true)
        disposeEvent.onComplete()
    }

    open fun onFirstViewAttach() {}

    open fun onDetach() {}

}
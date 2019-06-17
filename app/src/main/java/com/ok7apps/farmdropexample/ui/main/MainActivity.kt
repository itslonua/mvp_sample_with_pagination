package com.ok7apps.farmdropexample.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.jakewharton.rxbinding2.support.v7.widget.RecyclerViewScrollEvent
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView
import com.ok7apps.farmdropexample.R
import com.ok7apps.farmdropexample.core.bindToUi
import com.ok7apps.farmdropexample.core.recycler.RecyclerEmptyAnimation
import com.ok7apps.farmdropexample.domain.paginator.FetcherPaginatorState
import dagger.android.AndroidInjection
import io.reactivex.ObservableTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), MainView {

    companion object {
        const val RECYCLER_FLING_SPEED = 4_000
    }

    @Inject lateinit var mainPresenter: MainPresenter
    @Inject lateinit var mainAdapter: MainActivityAdapter

    private val compositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(activity_main_recycler) {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            itemAnimator = RecyclerEmptyAnimation()
            onFlingListener = onFlingRecyclerViewListener()
        }

        RxRecyclerView.scrollEvents(activity_main_recycler)
                .skip(1)
                .compose(recyclerRxViewTransformer())
                .subscribe { mainPresenter.forward() }
                .addTo(compositeDisposable)

        activity_swipe_refresh.setOnRefreshListener {
            mainPresenter.refresh()
        }

        mainPresenter.refresh()
    }

    override fun onStart() {
        super.onStart()
        mainPresenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        mainPresenter.detachView(this)
        compositeDisposable.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
        mainPresenter.onDestroy()
    }

    override fun renderView(viewState: MainViewState) {
        if (viewState.throwable != null) {
            viewState.throwable.bindToUi(
                    onEmptyCacheException = {
                        Toast.makeText(this, "Offline | Network Issues", Toast.LENGTH_SHORT).show()
                    },
                    onGeneralException = {
                        Toast.makeText(this, "Exception", Toast.LENGTH_SHORT).show()
                    }
            )
            activity_swipe_refresh.isRefreshing = false
        } else {
            viewState.resource.bindToUi(
                    onProgress = {
                        activity_swipe_refresh.isRefreshing = true
                    },
                    onSuccess = { data ->
                        if (viewState.forceReload) {
                            mainAdapter.clear()
                        }
                        mainAdapter.swap(data!!) { previous, new ->
                            MainItemDiffUtilsCallback(previous, new)
                        }
                        activity_swipe_refresh.isRefreshing = false
                    }
            )
        }
    }

    private fun recyclerRxViewTransformer() = ObservableTransformer<RecyclerViewScrollEvent, RecyclerViewScrollEvent> { upstream ->
        upstream.filter { recyclerEvent ->
            val layoutManager = recyclerEvent.view().layoutManager as LinearLayoutManager
            val itemCount = layoutManager.itemCount
            val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
            itemCount - FetcherPaginatorState.LIMIT <= lastVisibleItemPosition
        }.distinctUntilChanged<Int> { recyclerView ->
            if (recyclerView.view().adapter != null) {
                recyclerView.view().adapter!!.itemCount
            } else {
                RecyclerView.NO_POSITION
            }
        }
    }

    private fun RecyclerView.onFlingRecyclerViewListener(): RecyclerView.OnFlingListener {
        return object : RecyclerView.OnFlingListener() {
            override fun onFling(velocityX: Int, velocityY: Int): Boolean {
                if (Math.abs(velocityY) > RECYCLER_FLING_SPEED) {
                    fling(velocityX, RECYCLER_FLING_SPEED * Math.signum(velocityY.toDouble()).toInt())
                    return true
                }
                return false
            }
        }
    }
}

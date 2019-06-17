package com.ok7apps.farmdropexample.core

import com.ok7apps.farmdropexample.data.local.EmptyCacheException


data class Resource<out T>(
        val status: Status,
        val data: T?,
        val throwable: Throwable?
) {

    companion object {

        fun <T> success(data: T? = null): Resource<T> = Resource(Status.SUCCESS, data, null)

        fun <T> error(throwable: Throwable, data: T? = null): Resource<T> = Resource(Status.ERROR, data, throwable)

        fun <T> loading(data: T? = null): Resource<T> = Resource(Status.IN_FLIGHT, data, null)

    }

    enum class Status {
        SUCCESS,
        ERROR,
        IN_FLIGHT
    }
}

fun <T> Resource<T>.bindToUi(onSuccess: (T?) -> Unit, onProgress: () -> Unit) {
    when (this.status) {
        Resource.Status.SUCCESS -> onSuccess(this.data)
        Resource.Status.IN_FLIGHT -> onProgress()
        else -> Unit
    }
}

fun Throwable.bindToUi(onEmptyCacheException: () -> Unit, onGeneralException: () -> Unit) {
    when(this) {
        is EmptyCacheException -> {
            onEmptyCacheException()
        }
        else -> onGeneralException()
    }
}
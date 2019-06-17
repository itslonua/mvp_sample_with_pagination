package com.ok7apps.farmdropexample.data.model

data class Response<out T>(
        val response: T,
        val count: Int = 0,
        val pagination: ResponsePagination
)

data class ResponsePagination(
        val current: Int,
        val previous: Int?,
        val next: Int? = null
)
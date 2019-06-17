package com.ok7apps.farmdropexample.data.network

import com.ok7apps.farmdropexample.data.model.Response
import com.ok7apps.farmdropexample.data.model.producer.ProducerResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ProducersApi {

    @GET("/2/producers")
    fun getProducers(
            @Query("page") page: Int,
            @Query("per_page_limit") limit: Int
    ): Single<Response<List<ProducerResponse>>>

}
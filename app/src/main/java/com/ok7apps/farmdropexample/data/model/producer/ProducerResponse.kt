package com.ok7apps.farmdropexample.data.model.producer

data class ProducerResponse(
        val id: Int,
        val name: String,
        val permalink: String,
        val images: List<ProducerResponseImage>,
        val short_description: String
)


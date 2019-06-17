package com.ok7apps.farmdropexample.domain.producer

data class Producer(
    val id: Int,
    val permalink: String,
    val name: String,
    val image: String,
    val description: String,
    val page: Int
)

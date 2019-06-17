package com.ok7apps.farmdropexample.data.local.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

@Entity(
        tableName = "producer",
        indices = [Index(value = ["page"])]
)
data class ProducerEntity(
        @PrimaryKey val id: Int,
        val permalink: String,
        val name: String,
        val image: String,
        val description: String,
        val page: Int,
        val order: Int
)
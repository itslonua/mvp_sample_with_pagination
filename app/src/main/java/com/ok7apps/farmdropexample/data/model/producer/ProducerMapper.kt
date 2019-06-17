package com.ok7apps.farmdropexample.data.model.producer

import com.ok7apps.farmdropexample.domain.producer.Producer

object ProducerMapper {

    fun fromNetwork(users: List<ProducerResponse>, page: Int): List<Producer> {
        return users.map {
            Producer(
                    it.id,
                    it.permalink,
                    it.name,
                    it.images.first().path,
                    it.short_description,
                    page
            )
        }
    }
}
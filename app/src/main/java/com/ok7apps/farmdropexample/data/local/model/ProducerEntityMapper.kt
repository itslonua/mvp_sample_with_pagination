package com.ok7apps.farmdropexample.data.local.model


import com.ok7apps.farmdropexample.domain.producer.Producer

object ProducerEntityMapper {

    fun fromDatabase(producer: ProducerEntity): Producer {
        return Producer(
                producer.id,
                producer.permalink,
                producer.name,
                producer.image,
                producer.description,
                producer.page)
    }

    fun toDatabase(item: Producer, page: Int, order: Int): ProducerEntity {
        return ProducerEntity(
                item.id,
                item.permalink,
                item.name,
                item.image,
                item.description,
                page,
                order)
    }

}
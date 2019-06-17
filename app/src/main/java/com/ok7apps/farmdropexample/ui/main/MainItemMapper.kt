package com.ok7apps.farmdropexample.ui.main

import com.ok7apps.farmdropexample.domain.producer.Producer

object MainItemMapper  {

    fun map(producers: Producer): MainItem {
        return MainItem(
                producers.id,
                producers.name,
                producers.image,
                producers.description
        )
    }

}
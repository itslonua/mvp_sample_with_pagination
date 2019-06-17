package com.ok7apps.farmdropexample.ui.main

import com.ok7apps.farmdropexample.core.Resource

data class MainViewState (
        val resource: Resource<List<MainItem>>,
        val throwable: Throwable? = null,
        val forceReload: Boolean = false
)
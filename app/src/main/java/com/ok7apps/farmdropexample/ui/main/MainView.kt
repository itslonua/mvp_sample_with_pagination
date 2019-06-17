package com.ok7apps.farmdropexample.ui.main

import com.ok7apps.farmdropexample.core.MvpView

interface MainView : MvpView {

    fun renderView(viewState: MainViewState)

}
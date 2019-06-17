package com.ok7apps.farmdropexample.ui.main

import com.ok7apps.farmdropexample.core.recycler.AbstractDiffUtilsCallback

class MainItemDiffUtilsCallback(
        previousItems: List<MainItem>,
        newItems: List<MainItem>
) : AbstractDiffUtilsCallback<MainItem>(previousItems, newItems) {

    override fun isSameItems(newItem: MainItem?, oldItem: MainItem?): Boolean {
        return newItem?.id == oldItem?.id
    }

    override fun isSameContent(newItem: MainItem?, oldItem: MainItem?): Boolean {
        return newItem == oldItem
    }

}
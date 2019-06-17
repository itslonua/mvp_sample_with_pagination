package com.ok7apps.farmdropexample.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ok7apps.farmdropexample.core.image.ImageLoaderProxy
import com.ok7apps.farmdropexample.core.recycler.AbstractRecyclerViewAdapter

class MainActivityAdapter(
        private val imageLoaderProxy: ImageLoaderProxy
) : AbstractRecyclerViewAdapter<MainItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewHolderType: Int): RecyclerView.ViewHolder {
        return MainActivityViewHolder.inflate(LayoutInflater.from(parent.context), parent)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        val mainViewHolder = viewHolder as MainActivityViewHolder

        mainViewHolder.bindPhoto(item.image, imageLoaderProxy)
        mainViewHolder.bindName(item.name)
        mainViewHolder.bindDescription(item.description)
    }
}
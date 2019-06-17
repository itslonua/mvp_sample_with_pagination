package com.ok7apps.farmdropexample.ui.main


import android.support.annotation.NonNull
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ok7apps.farmdropexample.R
import com.ok7apps.farmdropexample.core.image.ImageLoaderProxy
import kotlinx.android.synthetic.main.main_activity_viewholder.view.*

class MainActivityViewHolder(
        itemView: View
) : RecyclerView.ViewHolder(itemView) {

    companion object {

        fun inflate(layoutInflater: LayoutInflater, parent: ViewGroup): MainActivityViewHolder {
            return MainActivityViewHolder(
                    layoutInflater.inflate(
                            R.layout.main_activity_viewholder,
                            parent,
                            false
                    )
            )
        }

    }

    fun bindName(@NonNull title: String) {
        itemView.producer_name.text = title
    }

    fun bindDescription(@NonNull description: String) {
        itemView.producer_description.text = description
    }

    fun bindPhoto(image: String, imageLoaderProxy: ImageLoaderProxy) {
        imageLoaderProxy.bind(itemView.imageView, image)
    }

}
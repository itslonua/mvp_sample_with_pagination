package com.ok7apps.farmdropexample.core.image

import android.widget.ImageView

interface ImageLoaderProxy {

    fun bind(imageView: ImageView, imageUrl: String)

}
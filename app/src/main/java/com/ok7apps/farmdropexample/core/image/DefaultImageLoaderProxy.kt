package com.ok7apps.farmdropexample.core.image

import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.ok7apps.farmdropexample.R

class DefaultImageLoaderProxy(private val requestManager: RequestManager) : ImageLoaderProxy {

    companion object {

        const val AVATAR_DIMENS_PX = 120

    }

    private val options by lazy {
        RequestOptions()
                .format(DecodeFormat.PREFER_RGB_565)
                .override(AVATAR_DIMENS_PX, AVATAR_DIMENS_PX)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.ic_avatar)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
    }

    override fun bind(imageView: ImageView, imageUrl: String) {
        requestManager.load(imageUrl)
                .apply(options.signature(ObjectKey(imageUrl)))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
    }

}
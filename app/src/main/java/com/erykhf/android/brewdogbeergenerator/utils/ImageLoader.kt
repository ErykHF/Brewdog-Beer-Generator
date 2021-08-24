package com.erykhf.android.brewdogbeergenerator.utils

import android.widget.ImageView

interface ImageLoader {
    fun loadImage(imageUrl: String, imageView: ImageView)
}
package com.erykhf.android.brewdogbeergenerator

import android.widget.ImageView

interface ImageLoader {
    fun loadImage(imageUrl: String, imageView: ImageView)
}
package com.erykhf.android.brewdogbeergenerator.utils

import android.content.Context
import android.graphics.Color
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.erykhf.android.brewdogbeergenerator.R
import com.squareup.picasso.Picasso

object Util {


    fun getProgressDrawable(context: Context): CircularProgressDrawable {

        val listOfColors = listOf<Int>(Color.GREEN,Color.RED,Color.BLUE,Color.YELLOW)

        return CircularProgressDrawable(context).apply {
            strokeWidth = 10f
            centerRadius = 50f
            setColorSchemeColors(R.drawable.gradient)
            colorSchemeColors
            start()
        }
    }


    @BindingAdapter("imageFromUrl")
    fun ImageView.loadImages(uri: String?) {
        Picasso.get().load(uri).error(R.mipmap.ic_launcher).into(this)

    }

//    @BindingAdapter("imageFromUrl")
//    fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
//        if (!imageUrl.isNullOrEmpty()) {
//            Glide.with(view.context)
//                .load(imageUrl)
//                .transition(DrawableTransitionOptions.withCrossFade())
//                .into(view)
//        }
//    }
}
package com.erykhf.android.brewdogbeergenerator.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
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


    fun ImageView.loadImages(uri: String, progressDrawable: CircularProgressDrawable) {
        Picasso.get().load(uri).placeholder(progressDrawable).error(R.mipmap.ic_launcher).into(this)

    }
}
package com.erykhf.android.brewdogbeergenerator.model

import com.squareup.moshi.Json

data class BeerData(
    @field:Json(name = "image_url") val image_url: String,
    val id: Int,
    @field:Json(name = "name") val name: String,
    val tagline: String,
    val first_brewed: String,
    val description: String
)
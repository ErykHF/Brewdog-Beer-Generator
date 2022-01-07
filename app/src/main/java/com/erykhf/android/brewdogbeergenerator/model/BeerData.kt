package com.erykhf.android.brewdogbeergenerator.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "beerData")
data class BeerData(
    @field:Json(name = "image_url") val image_url: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @field:Json(name = "name") val name: String,
    val tagline: String,
    val first_brewed: String,
    val description: String
)
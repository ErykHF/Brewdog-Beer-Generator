package com.erykhf.android.brewdogbeergenerator.model

import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import java.util.*

@Entity(tableName = "beerData")
data class BeerData(
    @Nullable
    @field:Json(name = "image_url") val image_url: String? = null,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @field:Json(name = "name") val name: String,
    val tagline: String,
    val first_brewed: String,
    val description: String
    )
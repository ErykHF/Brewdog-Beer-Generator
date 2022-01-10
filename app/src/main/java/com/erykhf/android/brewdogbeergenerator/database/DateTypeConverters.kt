package com.erykhf.android.brewdogbeergenerator.database

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import java.util.*


object DateTypeConverters {

    private lateinit var moshi: Moshi

    fun initialize(moshi: Moshi) {
        this.moshi = moshi
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long?): Date? {
        return millisSinceEpoch?.let {
            Date(it)
        }
    }
}
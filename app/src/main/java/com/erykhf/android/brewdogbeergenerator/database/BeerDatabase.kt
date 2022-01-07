package com.erykhf.android.brewdogbeergenerator.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.erykhf.android.brewdogbeergenerator.model.BeerData

@Database(entities = [BeerData::class], version = 1)
abstract class BeerDatabase: RoomDatabase() {
    abstract fun beerDao(): BeerDao
}
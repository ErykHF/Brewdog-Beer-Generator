package com.erykhf.android.brewdogbeergenerator.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.erykhf.android.brewdogbeergenerator.model.BeerData

@Dao
interface BeerDao {

    @Query("SELECT * FROM beerData")
    fun getAllBeers(): List<BeerData>

    @Insert
    fun insertBeer(beerData: BeerData)
}
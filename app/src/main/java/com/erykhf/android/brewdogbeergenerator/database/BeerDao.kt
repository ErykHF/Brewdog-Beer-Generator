package com.erykhf.android.brewdogbeergenerator.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.erykhf.android.brewdogbeergenerator.model.BeerData

@Dao
interface BeerDao {

    @Query("SELECT * FROM beerData ORDER by name ")
    suspend fun getAllBeers(): List<BeerData>

    @Insert(onConflict = REPLACE)
    suspend fun insertBeer(beerData: List<BeerData>?)

    @Delete
    suspend fun deleteBeer(beerData: BeerData)
}
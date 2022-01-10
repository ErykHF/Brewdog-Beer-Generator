package com.erykhf.android.brewdogbeergenerator.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.erykhf.android.brewdogbeergenerator.model.BeerData
enum class SortOrder {BY_NAME, BY_DATE}

@Dao
interface BeerDao {

    fun getAllBeers(sortOrder: SortOrder): LiveData<List<BeerData>> =
        when (sortOrder) {
            SortOrder.BY_DATE -> getBeerByDate()
            SortOrder.BY_NAME -> getBeersByName()
        }

    @Query("SELECT * FROM beerData ORDER by name ")
    fun getBeersByName(): LiveData<List<BeerData>>

    @Query("SELECT * FROM beerData ORDER by id")
    fun getBeerByDate(): LiveData<List<BeerData>>

    @Insert(onConflict = REPLACE)
    suspend fun insertBeer(beerData: List<BeerData>?)

    @Delete
    suspend fun deleteBeer(beerData: BeerData)

    @Query("DELETE FROM beerData")
    suspend fun deleteAllBeers()

}
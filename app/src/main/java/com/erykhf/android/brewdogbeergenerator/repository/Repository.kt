package com.erykhf.android.brewdogbeergenerator.repository

import com.erykhf.android.brewdogbeergenerator.api.PunkApiService
import com.erykhf.android.brewdogbeergenerator.database.BeerDao
import com.erykhf.android.brewdogbeergenerator.model.BeerData
import javax.inject.Inject

class Repository @Inject constructor(private val api: PunkApiService, private val beerDao: BeerDao){

    fun getBeers() = api.loadImages()

    suspend fun saveBeer(beerData: List<BeerData>) = beerDao.insertBeer(beerData)

    suspend fun getAllBeers() = beerDao.getAllBeers()

}
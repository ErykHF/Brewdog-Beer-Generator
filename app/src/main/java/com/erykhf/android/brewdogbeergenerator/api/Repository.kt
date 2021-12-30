package com.erykhf.android.brewdogbeergenerator.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository {


    suspend fun getBeers() = withContext(Dispatchers.IO) {

        val request = RetrofitService.punkApiService.loadImages()

        val result = try {
            request
        } catch (cause: Throwable) {
            throw BeerError("Error", cause)
        }

        if (result.isSuccessful) {
            result.body()
        } else {
            throw  BeerError("No Beers", null)
        }
    }
}

class BeerError(message: String, cause: Throwable?) : Throwable(message, cause)

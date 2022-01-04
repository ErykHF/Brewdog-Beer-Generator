package com.erykhf.android.brewdogbeergenerator.api

import com.erykhf.android.brewdogbeergenerator.model.BeerData
import retrofit2.Call
import retrofit2.http.GET


interface PunkApiService {

    @GET("random")
    fun loadImages() : Call<List<BeerData>>
}
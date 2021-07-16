package com.erykhf.android.brewdogbeergenerator.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.erykhf.android.brewdogbeergenerator.model.BeerData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://api.punkapi.com/v2/beers/"


class RetrofitService {

    private val punkApiService: PunkApiService


    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl((BASE_URL))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        punkApiService = retrofit.create(PunkApiService::class.java)
    }


    fun getBeerImageResponse(): MutableLiveData<List<BeerData>> {
        val liveDataResponse: MutableLiveData<List<BeerData>> = MutableLiveData()
        val callPunkApi: Call<List<BeerData>> = punkApiService.loadImages()

        callPunkApi.enqueue(object : Callback<List<BeerData>> {
            override fun onResponse(
                call: Call<List<BeerData>>,
                response: Response<List<BeerData>>
            ) {
                val beerResponse: List<BeerData>? = response.body()
                liveDataResponse.value = beerResponse
            }

            override fun onFailure(call: Call<List<BeerData>>, t: Throwable) {
                Log.e("MainActivity", "Failed to get search results", t)
            }

        })
        return liveDataResponse
    }
}
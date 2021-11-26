package com.erykhf.android.brewdogbeergenerator.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.erykhf.android.brewdogbeergenerator.model.BeerData
import kotlinx.coroutines.GlobalScope
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

private const val BASE_URL = "https://api.punkapi.com/v2/beers/"


class RetrofitService @Inject constructor (private val apiService: PunkApiService) {

   suspend fun getBeers() = apiService.loadImages()
}
package com.erykhf.android.brewdogbeergenerator.api


import javax.inject.Inject


class RetrofitService @Inject constructor(private val apiService: PunkApiService) {

    suspend fun getBeers() = apiService.loadImages()
}
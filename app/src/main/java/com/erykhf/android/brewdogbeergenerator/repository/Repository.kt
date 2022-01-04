package com.erykhf.android.brewdogbeergenerator.repository

import com.erykhf.android.brewdogbeergenerator.api.PunkApiService
import javax.inject.Inject

class Repository @Inject constructor(private val api: PunkApiService){

    fun getBeers() = api.loadImages()

}
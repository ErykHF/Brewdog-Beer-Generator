package com.erykhf.android.brewdogbeergenerator.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erykhf.android.brewdogbeergenerator.api.PunkApiService
import com.erykhf.android.brewdogbeergenerator.api.RetrofitService
import com.erykhf.android.brewdogbeergenerator.model.BeerData

class MainViewModel : ViewModel() {

    var beerItemLiveData : MutableLiveData<List<BeerData>>

    init {

        beerItemLiveData = RetrofitService().getBeerImageResponse()
    }

    fun refresh(){
        beerItemLiveData = RetrofitService().getBeerImageResponse()
    }
}
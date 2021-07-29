package com.erykhf.android.brewdogbeergenerator.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erykhf.android.brewdogbeergenerator.api.PunkApiService
import com.erykhf.android.brewdogbeergenerator.api.RetrofitService
import com.erykhf.android.brewdogbeergenerator.model.BeerData
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var beerItemLiveData : MutableLiveData<List<BeerData>> = MutableLiveData()

    init {
        beerItemLiveData = RetrofitService.getBeerImageResponse()
    }

    fun refresh() = viewModelScope.launch{
        beerItemLiveData = RetrofitService.getBeerImageResponse()
    }
}
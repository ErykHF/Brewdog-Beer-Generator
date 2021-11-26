package com.erykhf.android.brewdogbeergenerator.ui.main

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.*
import com.erykhf.android.brewdogbeergenerator.api.RetrofitService
import com.erykhf.android.brewdogbeergenerator.model.BeerData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val retrofitService: RetrofitService,
    application: Application
) : AndroidViewModel(application) {


    val beerFlow : Flow<List<BeerData>> = flow {
        val beers = retrofitService.getBeers()
        emit(beers)
    }


    fun isOnline(): Boolean {
        val connectivityManager =
            getApplication<Application>().applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

}
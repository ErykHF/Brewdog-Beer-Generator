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

//    var beerItemLiveData: MutableLiveData<List<BeerData>> = MutableLiveData()
//    var beerItemLiveData: Flow<List<BeerData>>

    init {
//        beerItemLiveData = getBeerImageResponse()

    }

    fun refresh() = viewModelScope.launch {
//        beerItemLiveData = getBeerImageResponse()
//        beerItemLiveData = beerFlow
    }

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

//    private fun getBeerImageResponse(): MutableLiveData<List<BeerData>> {
//        val liveDataResponse: MutableLiveData<List<BeerData>> = MutableLiveData()
//        val callPunkApi: Call<List<BeerData>> = retrofitService.getBeers()
//
//        callPunkApi.enqueue(object : Callback<List<BeerData>> {
//            override fun onResponse(
//                call: Call<List<BeerData>>,
//                response: Response<List<BeerData>>
//            ) {
//                if (response.isSuccessful) {
//                    val beerResponse: List<BeerData>? = response.body()
//                    liveDataResponse.value = beerResponse
//                    Log.d("Retrofit", "onResponse: SUCCESS!")
//
//                } else {
//                    Log.e("Retrofit", "onResponse: Not successful")
//                }
//            }
//
//            override fun onFailure(call: Call<List<BeerData>>, t: Throwable) {
//                Log.e("onFailure", "Failed to get search results", t)
//            }
//
//        })
//        return liveDataResponse
//    }

}
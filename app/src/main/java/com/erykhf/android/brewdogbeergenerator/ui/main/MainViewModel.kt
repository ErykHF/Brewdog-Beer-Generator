package com.erykhf.android.brewdogbeergenerator.ui.main

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.*
import com.erykhf.android.brewdogbeergenerator.api.BeerError
import com.erykhf.android.brewdogbeergenerator.api.Repository
import com.erykhf.android.brewdogbeergenerator.api.RetrofitService
import com.erykhf.android.brewdogbeergenerator.model.BeerData
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

//    var beerItemLiveData : MutableLiveData<List<BeerData>> = MutableLiveData()
//
//    init {
//        beerItemLiveData = getBeerImageResponse()
//    }
//
//    fun refresh() = viewModelScope.launch{
//        beerItemLiveData = getBeerImageResponse()
//    }


    val repository = Repository()

    private val _errorText = MutableLiveData<String?>()

    val errorText: LiveData<String?>
        get() = _errorText

    private val _beerData = MutableLiveData<List<BeerData>?>()
    val beerData: LiveData<List<BeerData>?> = _beerData

    fun getBeerImageResponse() = viewModelScope.launch {
        try {
            val response = repository.getBeers()
            _beerData.postValue(response)
        } catch (error: BeerError) {
            _errorText.value = error.message
        }
    }

//    private fun getBeerImageResponse(): MutableLiveData<List<BeerData>> {
//        val liveDataResponse: MutableLiveData<List<BeerData>> = MutableLiveData()
//        val callPunkApi: Call<List<BeerData>> = RetrofitService.punkApiService.loadImages()
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
//                    Log.e("Retrofit", "onResponse: Not successful",)
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
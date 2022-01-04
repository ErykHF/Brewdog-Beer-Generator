package com.erykhf.android.brewdogbeergenerator.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.erykhf.android.brewdogbeergenerator.model.BeerData
import com.erykhf.android.brewdogbeergenerator.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    var beerItemLiveData: MutableLiveData<List<BeerData>> = MutableLiveData()

    init {
        beerItemLiveData = getBeerImageResponse()

    }

    fun refresh() = viewModelScope.launch {
        beerItemLiveData = getBeerImageResponse()
    }


    private fun getBeerImageResponse(): MutableLiveData<List<BeerData>> {
        val liveDataResponse: MutableLiveData<List<BeerData>> = MutableLiveData()
        val callPunkApi: Call<List<BeerData>> = repository.getBeers()

        callPunkApi.enqueue(object : Callback<List<BeerData>> {
            override fun onResponse(
                call: Call<List<BeerData>>,
                response: Response<List<BeerData>>
            ) {
                response.let {
                    if (response.isSuccessful) {
                        val beerResponse: List<BeerData>? = response.body()
                        liveDataResponse.value = beerResponse
                        Log.d("Retrofit", "onResponse: SUCCESS!")

                    } else {
                        Log.e("Retrofit", "onResponse: Not successful")
                    }
                }
            }

            override fun onFailure(call: Call<List<BeerData>>, t: Throwable) {
                Log.e("onFailure", "Failed to get search results", t)
            }

        })
        return liveDataResponse
    }

}
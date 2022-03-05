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

    private val _beerItemLiveData: MutableLiveData<List<BeerData>> = MutableLiveData()
    var beerItemLiveData: LiveData<List<BeerData>> = _beerItemLiveData

    val error: MutableLiveData<String> = MutableLiveData()

    init {
        beerItemLiveData = getBeerImageResponse()

    }

    val beers = listOf<BeerData>()


    private val _name = MutableLiveData(beerItemLiveData.value?.firstOrNull()?.name)
    private val _description = MutableLiveData(beerItemLiveData.value?.firstOrNull()?.description)
    private val _tagline = MutableLiveData(beerItemLiveData.value?.firstOrNull()?.tagline)
    private val _imgUrl = MutableLiveData(beerItemLiveData.value?.firstOrNull()?.image_url)
    private val _firstbrewed = MutableLiveData(beerItemLiveData.value?.firstOrNull()?.first_brewed)


    val name: LiveData<String?> = _name
    val description: LiveData<String?> = _description
    val tagline: LiveData<String?> = _tagline
    val imgUrl: LiveData<String?> = _imgUrl
    val firstBrewed: LiveData<String?> = _firstbrewed

    fun refresh() = viewModelScope.launch {
        beerItemLiveData = getBeerImageResponse()
    }

    fun saveBeer(beerData: List<BeerData>) = viewModelScope.launch {
        repository.saveBeer(beerData)
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
                        error.value = response.message()

                    }
                }
            }

            override fun onFailure(call: Call<List<BeerData>>, t: Throwable) {
                Log.e("onFailure", "Failed to get search results", t)
                error.value = t.message

            }

        })
        return liveDataResponse
    }

}
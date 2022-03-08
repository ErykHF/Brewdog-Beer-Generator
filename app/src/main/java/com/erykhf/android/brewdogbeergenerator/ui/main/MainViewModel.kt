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


    private val _name = MutableLiveData<String>()
    private val _description = MutableLiveData<String>()
    private val _tagline = MutableLiveData<String>()
    private val _imgUrl = MutableLiveData<String>()
    private val _firstbrewed = MutableLiveData<String>()


    val name = _name
    val description = _description
    val tagline: LiveData<String?>
        get() = _tagline
    val imgUrl: LiveData<String?> = _imgUrl
    val firstBrewed: LiveData<String?> = _firstbrewed

    init {
        description
        _tagline
        _imgUrl
        _firstbrewed
    }

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
                        println(beerResponse)

                        if (beerResponse != null) {
                            for (beer in beerResponse.iterator()){
                                _name.value = beer.name
                                _firstbrewed.value = beer.first_brewed
                                _tagline.value = beer.tagline
                                _description.value = beer.description
                                _imgUrl.value = beer.image_url
                                    ?: "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/300px-No_image_available.svg.png"

                            }
                        }

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
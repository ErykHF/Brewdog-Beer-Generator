package com.erykhf.android.brewdogbeergenerator.ui.main.beerview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erykhf.android.brewdogbeergenerator.model.BeerData
import com.erykhf.android.brewdogbeergenerator.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeerViewViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {


    suspend fun getBeer(beerName: String) = repository.getByName(beerName)

    fun deleteBeer(beerData: BeerData) = viewModelScope.launch {
        repository.deleteBeer(beerData)
    }


}
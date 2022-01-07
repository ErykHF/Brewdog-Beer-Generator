package com.erykhf.android.brewdogbeergenerator.ui.main.savedbeers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erykhf.android.brewdogbeergenerator.model.BeerData
import com.erykhf.android.brewdogbeergenerator.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedBeersViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {


    suspend fun getAllBeers() = repository.getAllBeers()

    fun deleteBeer(beerData: BeerData) = viewModelScope.launch {
        repository.deleteBeer(beerData)
    }
}
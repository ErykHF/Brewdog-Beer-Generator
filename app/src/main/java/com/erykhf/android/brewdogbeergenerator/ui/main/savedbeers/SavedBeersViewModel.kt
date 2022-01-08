package com.erykhf.android.brewdogbeergenerator.ui.main.savedbeers

import androidx.lifecycle.LiveData
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

    val readAllData: LiveData<List<BeerData>>

    init {
        readAllData = repository.getAllBeers()
    }

    suspend fun getAllBeers() = repository.getAllBeers()

    fun deleteBeer(beerData: BeerData) = viewModelScope.launch {
        repository.deleteBeer(beerData)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAllBeers()
    }
}
package com.erykhf.android.brewdogbeergenerator.ui.main.savedbeers

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.erykhf.android.brewdogbeergenerator.database.SortOrder
import com.erykhf.android.brewdogbeergenerator.model.BeerData
import com.erykhf.android.brewdogbeergenerator.repository.Repository
import com.erykhf.android.brewdogbeergenerator.utils.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedBeersViewModel @Inject constructor(
    private val repository: Repository,
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    val readAllData: LiveData<List<BeerData>>


    private val preferencesLiveData = preferenceManager.preferencesLiveData


    private val getCarsLiveData = preferencesLiveData.switchMap { sortOrder ->

        repository.getAllBeers(sortOrder)
    }

    init {
        readAllData = getCarsLiveData
    }

    fun onSortOrderSelected(sortOrder: SortOrder) = viewModelScope.launch(Dispatchers.IO) {
        preferenceManager.updateSortOrder(sortOrder)
    }

    fun deleteBeer(beerData: BeerData) = viewModelScope.launch {
        repository.deleteBeer(beerData)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAllBeers()
    }
}
package com.erykhf.android.brewdogbeergenerator.ui.main.beerview

import androidx.lifecycle.ViewModel
import com.erykhf.android.brewdogbeergenerator.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BeerViewViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {


    suspend fun getBeer(beerName: String) = repository.getByName(beerName)


}
package com.erykhf.android.brewdogbeergenerator.utils

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.asLiveData
import com.erykhf.android.brewdogbeergenerator.database.SortOrder
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


private val Context.dataStore by preferencesDataStore("settings")


data class FilterPreferences(val sortOrder: SortOrder)

class PreferenceManager(appContext: Context) {


    private val settingsDataStore = appContext.dataStore

    val preferencesLiveData = settingsDataStore.data
        .catch { exception ->
            if (exception is IOException){
                Log.e("TAG", "Error read prefs:", exception)
                emit(emptyPreferences())
            }else{
                throw exception
            }
        }
        .map { preferences ->


            val sortOrder = SortOrder.valueOf(
                preferences[PreferencesKeys.SORT_ORDER] ?: SortOrder.BY_NAME.name
            )
            sortOrder
        }.asLiveData()

    suspend fun updateSortOrder(sortOrder: SortOrder){
        settingsDataStore.edit { preferences ->
            preferences[PreferencesKeys.SORT_ORDER] = sortOrder.name
        }
    }

    private object PreferencesKeys {
        val SORT_ORDER = stringPreferencesKey("sort_order")
    }
}
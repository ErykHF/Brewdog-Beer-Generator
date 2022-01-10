package com.erykhf.android.brewdogbeergenerator.di

import android.content.Context
import com.erykhf.android.brewdogbeergenerator.api.PunkApiService
import com.erykhf.android.brewdogbeergenerator.database.BeerDao
import com.erykhf.android.brewdogbeergenerator.repository.Repository
import com.erykhf.android.brewdogbeergenerator.utils.PreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://api.punkapi.com/v2/beers/"


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRepository(api: PunkApiService, beerDao: BeerDao) = Repository(api, beerDao)

    @Singleton
    @Provides
    fun providePunkApiService(): PunkApiService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(PunkApiService::class.java)

    @Provides
    @Singleton
    fun dataStoreManager(@ApplicationContext appContext: Context): PreferenceManager =
        PreferenceManager(appContext)
}
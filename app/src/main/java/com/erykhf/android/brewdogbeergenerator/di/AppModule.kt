package com.erykhf.android.brewdogbeergenerator.di

import com.erykhf.android.brewdogbeergenerator.api.PunkApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun providePunkApiService(): PunkApiService {
        return Retrofit.Builder()
            .baseUrl((BASE_URL))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(PunkApiService::class.java)
    }
}
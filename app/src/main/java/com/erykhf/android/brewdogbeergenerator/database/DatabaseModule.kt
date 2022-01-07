package com.erykhf.android.brewdogbeergenerator.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideBeerDataDao(beerDatabase: BeerDatabase): BeerDao {
        return beerDatabase.beerDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): BeerDatabase {
        return Room.databaseBuilder(
            appContext,
            BeerDatabase::class.java,
            "RssReader"
        ).build()
    }
}
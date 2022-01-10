package com.erykhf.android.brewdogbeergenerator.database

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
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

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context, moshi: Moshi): BeerDatabase {
        DateTypeConverters.initialize(moshi)
        return Room.databaseBuilder(
            appContext,
            BeerDatabase::class.java,
            "RssReader"
        ).build()
    }
}
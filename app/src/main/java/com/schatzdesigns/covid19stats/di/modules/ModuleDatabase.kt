package com.schatzdesigns.covid19stats.di.modules

import android.app.Application
import androidx.room.Room
import com.schatzdesigns.covid19stats.database.Covid19Database
import com.schatzdesigns.covid19stats.database.daos.CountriesDao
import com.schatzdesigns.covid19stats.database.daos.WorldwideStatsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [(ModuleViewModel::class)])
class ModuleDatabase {

    @Singleton
    @Provides
    internal fun provideDb(app: Application): Covid19Database =
        Room.databaseBuilder(app, Covid19Database::class.java, Covid19Database.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideWorldwideStatsDao(covid19Database: Covid19Database): WorldwideStatsDao =
        covid19Database.worldwideStatsDao()

    @Singleton
    @Provides
    fun provideCountryStatsDao(covid19Database: Covid19Database): CountriesDao =
        covid19Database.countryStatsDao()

}

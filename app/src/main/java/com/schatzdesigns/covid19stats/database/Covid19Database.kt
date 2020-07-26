package com.schatzdesigns.covid19stats.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.schatzdesigns.covid19stats.database.daos.CountriesDao
import com.schatzdesigns.covid19stats.database.daos.WorldwideStatsDao
import com.schatzdesigns.covid19stats.ui.stats.country.model.Country
import com.schatzdesigns.covid19stats.ui.stats.worldwide.model.WorldwideStats

@Database(
    entities = [(Country::class), (WorldwideStats::class)],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class Covid19Database : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "covid19stats.db"
    }

    abstract fun worldwideStatsDao(): WorldwideStatsDao
    abstract fun countryStatsDao(): CountriesDao
}

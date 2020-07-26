package com.schatzdesigns.covid19stats.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.schatzdesigns.covid19stats.ui.stats.country.model.Country

@Dao
interface CountriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCountriesStats(countries: List<Country>?)

    @Query("SELECT * FROM countries ORDER BY name ASC")
    suspend fun getCountries(): List<Country>

    @Query("DELETE FROM countries")
    suspend fun clearCountryStats()

}
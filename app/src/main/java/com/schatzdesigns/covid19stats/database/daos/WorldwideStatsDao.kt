package com.schatzdesigns.covid19stats.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.schatzdesigns.covid19stats.ui.stats.worldwide.model.WorldwideStats

@Dao
interface WorldwideStatsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWorldwideStats(worldwideStats: WorldwideStats?)

    @Query("SELECT * FROM worldwide_stats")
    suspend fun getWorldwideStats(): WorldwideStats

    @Query("SELECT timestamp FROM worldwide_stats")
    suspend fun getTimestamp(): String

    @Query("DELETE FROM worldwide_stats")
    suspend fun clearWorldwideStats()
}
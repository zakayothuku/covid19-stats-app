package com.schatzdesigns.covid19stats.ui.stats.worldwide

import com.schatzdesigns.covid19stats.api.ApiNetworkResource
import com.schatzdesigns.covid19stats.api.LiveResource
import com.schatzdesigns.covid19stats.api.StatisticsService
import com.schatzdesigns.covid19stats.database.daos.WorldwideStatsDao
import com.schatzdesigns.covid19stats.ui.stats.worldwide.model.WorldwideStats
import com.schatzdesigns.covid19stats.utils.RateLimiter
import retrofit2.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorldwideStatsRepository
@Inject constructor(
    private val statisticsService: StatisticsService,
    private val worldwideStatsDao: WorldwideStatsDao
) {

    companion object {
        private val requestsRateLimit = RateLimiter<String>(60, TimeUnit.MINUTES)
        private const val covid19worldwide = "covid19worldwide"
    }

    suspend fun getWorldwideStatistics(): LiveResource<WorldwideStats> {
        return object : ApiNetworkResource<WorldwideStats>() {
            override suspend fun createCall(): Response<WorldwideStats> {
                return statisticsService.getWorldwideStatistics()
            }

            override fun shouldFetch(data: WorldwideStats?): Boolean {
                return data == null || requestsRateLimit.shouldFetch(covid19worldwide)
            }

            override suspend fun saveResult(data: WorldwideStats?) {
                worldwideStatsDao.clearWorldwideStats().apply {
                    worldwideStatsDao.saveWorldwideStats(data)
                }
            }

            override suspend fun fetchFromDb(): WorldwideStats {
                return worldwideStatsDao.getWorldwideStats()
            }

            override fun onFetchFailed() {
                requestsRateLimit.reset(covid19worldwide)
            }
        }.asLiveResource()
    }

    suspend fun getTimestamp(): String {
        return worldwideStatsDao.getTimestamp()
    }

}
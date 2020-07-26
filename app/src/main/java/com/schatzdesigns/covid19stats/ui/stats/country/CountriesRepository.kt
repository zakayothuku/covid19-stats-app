package com.schatzdesigns.covid19stats.ui.stats.country

import com.schatzdesigns.covid19stats.api.ApiNetworkResource
import com.schatzdesigns.covid19stats.api.LiveResource
import com.schatzdesigns.covid19stats.api.StatisticsService
import com.schatzdesigns.covid19stats.database.daos.CountriesDao
import com.schatzdesigns.covid19stats.ui.stats.country.model.Country
import com.schatzdesigns.covid19stats.utils.RateLimiter
import retrofit2.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountriesRepository
@Inject constructor(
    private val statisticsService: StatisticsService,
    private val countriesDao: CountriesDao
) {

    companion object {
        private val requestsRateLimit = RateLimiter<String>(10, TimeUnit.MINUTES)
        private const val covid19countries = "covid19countries"
    }

    suspend fun getCountriesStatistics(): LiveResource<List<Country>> {
        return object : ApiNetworkResource<List<Country>>() {
            override suspend fun createCall(): Response<List<Country>> {
                return statisticsService.getCountriesStatistics()
            }

            override fun shouldFetch(data: List<Country>?): Boolean {
                return data == null || data.isEmpty() || requestsRateLimit.shouldFetch(covid19countries)
            }

            override suspend fun saveResult(data: List<Country>?) {
                countriesDao.clearCountryStats().apply {
                    countriesDao.saveCountriesStats(data)
                }
            }

            override suspend fun fetchFromDb(): List<Country> {
                return countriesDao.getCountries()
            }

            override fun onFetchFailed() {
                requestsRateLimit.reset(covid19countries)
            }
        }.asLiveResource()
    }

}

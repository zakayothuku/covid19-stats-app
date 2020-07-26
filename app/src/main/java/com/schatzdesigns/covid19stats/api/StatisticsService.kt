package com.schatzdesigns.covid19stats.api

import com.schatzdesigns.covid19stats.ui.stats.country.model.Country
import com.schatzdesigns.covid19stats.ui.stats.worldwide.model.WorldwideStats
import retrofit2.Response
import retrofit2.http.GET

interface StatisticsService {

    @GET("covid19worldwide")
    suspend fun getWorldwideStatistics(): Response<WorldwideStats>

    @GET("covid19countries")
    suspend fun getCountriesStatistics(): Response<List<Country>>
}

package com.schatzdesigns.covid19stats.ui.stats.worldwide

import androidx.lifecycle.MediatorLiveData
import com.schatzdesigns.covid19stats.BaseViewModel
import com.schatzdesigns.covid19stats.api.ApiError
import com.schatzdesigns.covid19stats.api.LiveResource
import com.schatzdesigns.covid19stats.api.ResourceState
import com.schatzdesigns.covid19stats.ui.stats.worldwide.model.WorldwideStats
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class WorldwideStatsViewModel
@Inject constructor(private val worldwideStatsRepository: WorldwideStatsRepository) :
    BaseViewModel() {

    private var liveResource: LiveResource<WorldwideStats>? = null
    private var loadingJob: Job? = null

    private val _loadingState = MediatorLiveData<@ResourceState Int?>()
    val loadingState = _loadingState

    private val _worldwideStats = MediatorLiveData<WorldwideStats?>()
    val worldwideStats = _worldwideStats

    private val _apiError = MediatorLiveData<ApiError?>()
    val apiError = _apiError

    fun getWorldwideStatistics() {

        if (_loadingState.value == ResourceState.STATE_LOADING) return

        uiScope.launch {
            worldwideStatsRepository.getWorldwideStatistics().apply {
                liveResource = this
                loadingJob = this.job
                bindObserver(_loadingState, this.state)
                bindObserver(_worldwideStats, this.data)
                bindObserver(_apiError, this.error)
            }
        }
    }

    suspend fun getTimestamp(): String? {
        return worldwideStatsRepository.getTimestamp()
    }

}

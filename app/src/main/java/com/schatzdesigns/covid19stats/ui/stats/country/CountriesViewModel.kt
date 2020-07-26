package com.schatzdesigns.covid19stats.ui.stats.country

import androidx.lifecycle.MediatorLiveData
import com.schatzdesigns.covid19stats.BaseViewModel
import com.schatzdesigns.covid19stats.api.ApiError
import com.schatzdesigns.covid19stats.api.LiveResource
import com.schatzdesigns.covid19stats.api.ResourceState
import com.schatzdesigns.covid19stats.ui.stats.country.model.Country
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class CountriesViewModel
@Inject constructor(private val countriesRepository: CountriesRepository) : BaseViewModel() {

    private var liveResource: LiveResource<List<Country>>? = null
    private var loadingJob: Job? = null

    private val _loadingState = MediatorLiveData<@ResourceState Int?>()
    val loadingState = _loadingState

    private val _countries = MediatorLiveData<List<Country>?>()
    val countries = _countries

    private val _apiError = MediatorLiveData<ApiError?>()
    val apiError = _apiError

    fun getCountries() {

        if (_loadingState.value == ResourceState.STATE_LOADING) return

        uiScope.launch {
            countriesRepository.getCountriesStatistics().apply {
                liveResource = this
                loadingJob = this.job
                bindObserver(_loadingState, this.state)
                bindObserver(_countries, this.data)
                bindObserver(_apiError, this.error)
            }
        }
    }

}

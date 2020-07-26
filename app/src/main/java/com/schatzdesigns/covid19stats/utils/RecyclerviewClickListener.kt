package com.schatzdesigns.covid19stats.utils

import com.schatzdesigns.covid19stats.ui.stats.country.model.Country

interface RecyclerviewOnClickListener {
    fun onRecyclerviewClicked(country: Country)
}
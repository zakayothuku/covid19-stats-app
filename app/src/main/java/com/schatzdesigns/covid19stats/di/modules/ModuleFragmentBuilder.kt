package com.schatzdesigns.covid19stats.di.modules

import com.schatzdesigns.covid19stats.ui.stats.country.CountryStatsFragment
import com.schatzdesigns.covid19stats.ui.stats.country.search.CountriesFragment
import com.schatzdesigns.covid19stats.ui.stats.worldwide.WorldwideStatsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ModuleFragmentBuilder {

    @ContributesAndroidInjector
    internal abstract fun contributeWorldStatsFragment(): WorldwideStatsFragment

    @ContributesAndroidInjector
    internal abstract fun contributeCountryStatsFragment(): CountryStatsFragment

    @ContributesAndroidInjector
    internal abstract fun contributeCountriesFragment(): CountriesFragment
}

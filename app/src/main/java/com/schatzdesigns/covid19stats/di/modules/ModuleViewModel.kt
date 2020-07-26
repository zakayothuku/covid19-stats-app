package com.schatzdesigns.covid19stats.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.schatzdesigns.covid19stats.di.factories.ViewModelFactory
import com.schatzdesigns.covid19stats.ui.stats.country.CountriesViewModel
import com.schatzdesigns.covid19stats.ui.stats.worldwide.WorldwideStatsViewModel

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ModuleViewModel {

    @Binds
    @IntoMap
    @ViewModelKey(WorldwideStatsViewModel::class)
    abstract fun bindWorldwideStatsViewModel(worldwideStatsViewModel: WorldwideStatsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CountriesViewModel::class)
    abstract fun bindCountriesViewModel(countriesViewModel: CountriesViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

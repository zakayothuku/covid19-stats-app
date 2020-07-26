package com.schatzdesigns.covid19stats.di

import android.app.Application
import com.schatzdesigns.covid19stats.Covid19Stats
import com.schatzdesigns.covid19stats.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        (AndroidSupportInjectionModule::class),
        (ModuleApplication::class),
        (ModuleActivity::class),
        (ModuleFragmentBuilder::class),
        (ModuleApiService::class),
        (ModuleDatabase::class),
        (ModuleViewModel::class)
    ]
)
interface AppComponent : AndroidInjector<Covid19Stats> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(app: Covid19Stats)
}

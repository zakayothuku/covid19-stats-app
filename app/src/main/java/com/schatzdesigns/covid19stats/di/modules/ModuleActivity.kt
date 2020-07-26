package com.schatzdesigns.covid19stats.di.modules

import com.schatzdesigns.covid19stats.ui.MainActivity
import com.schatzdesigns.covid19stats.ui.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ModuleActivity {

    @ContributesAndroidInjector(modules = [(ModuleFragmentBuilder::class)])
    internal abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [(ModuleFragmentBuilder::class)])
    internal abstract fun contributeMainActivity(): MainActivity
}

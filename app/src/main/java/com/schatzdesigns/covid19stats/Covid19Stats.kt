package com.schatzdesigns.covid19stats

import android.app.Activity
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.schatzdesigns.covid19stats.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class Covid19Stats : MultiDexApplication(), HasActivityInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity>? = androidInjector

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppInjector.init(this)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    companion object {
        @get:Synchronized
        var instance: Covid19Stats? = null
            private set
    }
}

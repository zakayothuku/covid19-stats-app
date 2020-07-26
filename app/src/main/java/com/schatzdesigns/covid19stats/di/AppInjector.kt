package com.schatzdesigns.covid19stats.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.schatzdesigns.covid19stats.Covid19Stats
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector

/**
 *
 * Helper class to automatically inject fragments if they implement [Injectable].
 *
 */
object AppInjector {

    fun init(app: Covid19Stats) {

        DaggerAppComponent.builder().application(app).build().inject(app)

        app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityPaused(p0: Activity) {
                Log.d(p0.localClassName, "%s Paused")
            }

            override fun onActivityStarted(p0: Activity) {
                Log.d(p0.localClassName, "%s Started")
            }

            override fun onActivityDestroyed(p0: Activity) {
                Log.d(p0.localClassName, "%s Destroyed")
            }

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
                Log.d(p0.localClassName, "%s SavedInstanceState")
            }

            override fun onActivityStopped(p0: Activity) {
                Log.d(p0.localClassName, "%s Stopped")
            }

            override fun onActivityResumed(p0: Activity) {
                Log.d(p0.localClassName, "%s Resumed")
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                handleActivity(activity)
                Log.d(activity.localClassName, "%s Created")
            }
        })
    }

    private fun handleActivity(activity: Activity) {
        if (activity is HasSupportFragmentInjector) {
            AndroidInjection.inject(activity)
        }
        (activity as? FragmentActivity)?.supportFragmentManager?.registerFragmentLifecycleCallbacks(
            object : FragmentManager.FragmentLifecycleCallbacks() {
                override fun onFragmentCreated(
                    fm: FragmentManager,
                    f: Fragment,
                    savedInstanceState: Bundle?
                ) {
                    if (f is Injectable) {
                        AndroidSupportInjection.inject(f)
                    }
                }
            }, true
        )
    }
}
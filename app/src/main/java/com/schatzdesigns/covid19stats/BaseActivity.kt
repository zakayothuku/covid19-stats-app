package com.schatzdesigns.covid19stats

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.schatzdesigns.covid19stats.di.factories.ViewModelFactory
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

open class BaseActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? = dispatchingAndroidInjector
}

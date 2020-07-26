package com.schatzdesigns.covid19stats

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar
import com.schatzdesigns.covid19stats.di.factories.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

open class BaseFragment : DaggerFragment(), CoroutineScope {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    fun hideKeyboard(context: Context?, view: View?) {
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    fun showSnackbarMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

}

package com.schatzdesigns.covid19stats.utils

import android.os.Parcel
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

fun SwipeRefreshLayout.startRefreshing() {
    isRefreshing = true
}

fun SwipeRefreshLayout.stopRefreshing() {
    isRefreshing = false
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

inline fun <reified T : Enum<T>> Parcel.readEnum() = readString()?.let { enumValueOf<T>(it) }

fun <T : Enum<T>> Parcel.writeEnum(value: T?) = writeString(value?.name)

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

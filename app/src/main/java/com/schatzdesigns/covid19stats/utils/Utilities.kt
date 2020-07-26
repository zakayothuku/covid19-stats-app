package com.schatzdesigns.covid19stats.utils

import java.util.*

object Utilities {

    fun numberFormatter(value: Float): String {
        return try {
            String.format(Locale.getDefault(), "%,.0f", value)
        } catch (e: Exception) {
            e.printStackTrace()
            "--"
        }
    }
}
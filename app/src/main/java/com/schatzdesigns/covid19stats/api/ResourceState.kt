package com.schatzdesigns.covid19stats.api

import androidx.annotation.IntDef
import com.schatzdesigns.covid19stats.api.ResourceState.Companion.STATE_FAILED
import com.schatzdesigns.covid19stats.api.ResourceState.Companion.STATE_LOADING
import com.schatzdesigns.covid19stats.api.ResourceState.Companion.STATE_SUCCESS

/**
 * Created by zakayothuku on 2019-09-05
 *
 * Copyright © 2019 Schatz Designs. All rights reserved.
 *
 * Status of a resource that is provided to the UI.
 *
 * These are usually created by the Repository classes where they return
 * back the latest data to the UI with its fetch status.
 */
@Target(AnnotationTarget.TYPE, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
@IntDef(
    STATE_LOADING,
    STATE_SUCCESS,
    STATE_FAILED
)
annotation class ResourceState {
    companion object {
        const val STATE_LOADING = 0
        const val STATE_SUCCESS = 1
        const val STATE_FAILED = -1
    }
}

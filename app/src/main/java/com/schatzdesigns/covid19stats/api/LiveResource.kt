package com.schatzdesigns.covid19stats.api

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Job

/**
 * Created by zakayothuku on 2019-09-05
 *
 * Copyright © 2019 Schatz Designs. All rights reserved.
 *
 * A generic class that holds the data, loading and error status.
 * @param [T]
 */
data class LiveResource<T>(
    val data: LiveData<T?>,
    val state: LiveData<@ResourceState Int?>,
    val error: LiveData<ApiError?>,
    val job: Job? = null
)

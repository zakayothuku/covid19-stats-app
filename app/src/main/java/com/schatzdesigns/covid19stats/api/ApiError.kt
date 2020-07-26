package com.schatzdesigns.covid19stats.api

/**
 * Created by zakayothuku on 2019-09-05
 *
 * Copyright © 2019 Schatz Designs. All rights reserved.
 *
 * Default api model that comes from server if something goes wrong with a repository call
 */
data class ApiError(
    val message: String,
    @Transient var apiErrorStatus: ApiErrorStatus
)

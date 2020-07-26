package com.schatzdesigns.covid19stats.api

/**
 * Created by zakayothuku on 2019-09-02
 *
 * Copyright © 2019 Schatz Designs. All rights reserved.
 *
 * Common class used by API responses.
 * @param [T] the type of the response object
 * */
data class ApiResponse<T>(
    var data: T?,
    @Transient var error: ApiError? = null
) {
    constructor(error: ApiError?) : this(null, error)
}

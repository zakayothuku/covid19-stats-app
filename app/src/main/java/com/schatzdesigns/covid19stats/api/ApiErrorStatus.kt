package com.schatzdesigns.covid19stats.api

/**
 * various api status to know what happened if something goes wrong with a repository call
 */
enum class ApiErrorStatus {
    /**
     * api in connecting to repository (Server or Database)
     */
    NO_CONNECTION,

    /**
     * api in getting value (Json Error, Server Error, etc)
     */
    BAD_RESPONSE,

    /**
     * Time out  api
     */
    TIMEOUT,

    /**
     * bad credentials
     */
    UNAUTHORIZED,

    /**
     * api not found
     */
    NOT_FOUND,

    /**
     * api forbidden
     */
    FORBIDDEN,

    /**
     * an unexpected api
     */
    NOT_DEFINED,

    /**
     * an unexpected api
     */
    INTERNAL_SERVER_ERROR
}

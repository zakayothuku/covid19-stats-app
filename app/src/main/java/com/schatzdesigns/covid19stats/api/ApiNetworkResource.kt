package com.schatzdesigns.covid19stats.api

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import com.schatzdesigns.covid19stats.Covid19Stats
import com.schatzdesigns.covid19stats.R
import com.schatzdesigns.covid19stats.api.ResourceState.Companion.STATE_FAILED
import com.schatzdesigns.covid19stats.api.ResourceState.Companion.STATE_LOADING
import com.schatzdesigns.covid19stats.api.ResourceState.Companion.STATE_SUCCESS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.CoroutineContext


/**
 * A generic class that can provide a resource backed by the network.
 *
 * @param ResultType
 *
 **/
@MainThread
abstract class ApiNetworkResource<ResultType> : CoroutineScope {

    private val result = MutableLiveData<ResultType>()
    private val state = MutableLiveData<@ResourceState Int?>()
    private val error = MutableLiveData<ApiError?>()

    private val context = Covid19Stats.instance!!.applicationContext

    private val job = SupervisorJob()
    private val ioDispatcher = Dispatchers.IO
    private val mainDispatcher = Dispatchers.Main
    final override val coroutineContext: CoroutineContext
        get() = ioDispatcher + job

    init {
        state.value = STATE_LOADING
        launch {
            val dbSource = fetchFromDb()
            if (shouldFetch(dbSource)) {
                fetchFromNetwork(dbSource)
            } else {
                setLiveResource(dbSource, STATE_SUCCESS, null)
            }
        }
    }

    private fun fetchFromNetwork(dbSource: ResultType) = launch(coroutineContext) {
        try {

            val apiSource = createCall()
            setLiveResource(dbSource, STATE_LOADING, null)

            when {
                apiSource.isSuccessful -> {
                    launch {
                        saveResult(apiSource.body())
                        val newData = fetchFromDb()
                        setLiveResource(newData, STATE_SUCCESS, null)
                    }
                }
                else -> {
                    val apiError = when (apiSource.code()) {
                        401 -> ApiError(
                            context.getString(R.string.unauthorized),
                            ApiErrorStatus.UNAUTHORIZED
                        )
                        403 -> ApiError(
                            context.getString(R.string.forbidden),
                            ApiErrorStatus.FORBIDDEN
                        )
                        404 -> ApiError(
                            context.getString(R.string.not_found),
                            ApiErrorStatus.NOT_FOUND
                        )
                        else -> ApiError(
                            context.getString(R.string.bad_response),
                            ApiErrorStatus.BAD_RESPONSE
                        )
                    }
                    setLiveResource(apiSource.body(), STATE_FAILED, apiError)
                }
            }

        } catch (throwable: Throwable) {
            val apiError: ApiError? = handleThrowable(throwable)
            setLiveResource(null, STATE_FAILED, apiError)
        }
    }

    private fun handleThrowable(throwable: Throwable): ApiError? {

        throwable.printStackTrace()

        return when (throwable) {

            // handle api call timeout api
            is SocketTimeoutException -> {
                ApiError(
                    context.getString(R.string.connection_time_out),
                    ApiErrorStatus.TIMEOUT
                )
            }

            // handle connection api
            is ConnectException -> {
                ApiError(
                    context.getString(R.string.connection_error),
                    ApiErrorStatus.NO_CONNECTION
                )
            }

            is IOException -> {
                ApiError(
                    context.getString(R.string.check_internet_connection),
                    ApiErrorStatus.NO_CONNECTION
                )
            }

            is UnknownHostException -> {
                ApiError(
                    context.getString(R.string.unknown_host),
                    ApiErrorStatus.NO_CONNECTION
                )
            }

            else -> ApiError(
                context.getString(R.string.not_defined),
                ApiErrorStatus.NOT_DEFINED
            )
        }
    }

    private fun setLiveResource(
        apiResponse: ResultType?,
        resourceState: @ResourceState Int?,
        apiError: ApiError?
    ) {
        launch(mainDispatcher + job) {
            if (result.value != apiResponse) {
                result.value = apiResponse
            }
            if (state.value != resourceState) {
                state.value = resourceState
            }
            if (error.value != apiError) {
                error.value = apiError
            }
        }
    }

    fun asLiveResource() = LiveResource(result, state, error, job)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun onFetchFailed()

    @MainThread
    protected abstract suspend fun saveResult(data: ResultType?)

    @MainThread
    protected abstract suspend fun fetchFromDb(): ResultType

    @MainThread
    protected abstract suspend fun createCall(): Response<ResultType>
}

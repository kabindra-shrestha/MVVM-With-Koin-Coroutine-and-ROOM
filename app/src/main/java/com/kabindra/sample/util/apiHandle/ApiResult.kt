package com.kabindra.sample.util.apiHandle

/**
 * AppResult class is a wrapper class that helps to handle success and failure scenarios with co routines
 */
sealed class ApiResult<out T> {

    data class Success<out T>(val successData: T) : ApiResult<T>()
    class Error(
        val exception: java.lang.Exception,
        val message: String = exception.localizedMessage
    ) : ApiResult<Nothing>()

}
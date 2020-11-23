package com.kabindra.sample.util.apiHandle

import retrofit2.Response

object ApiHandle {
    fun <T : Any> apiHandleError(resp: Response<T>): ApiResult.Error {
        val error = ApiErrorUtils.parseError(resp)
        return ApiResult.Error(Exception(error.message))
    }

    fun <T : Any> apiHandleSuccess(response: Response<T>): ApiResult<T> {
        response.body()?.let {
            return ApiResult.Success(it)
        } ?: return apiHandleError(response)
    }
}
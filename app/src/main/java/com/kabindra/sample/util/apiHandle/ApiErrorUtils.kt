package com.kabindra.sample.util.apiHandle

import com.google.gson.GsonBuilder
import com.kabindra.sample.util.TAG
import com.kabindra.sample.util.loggerDebug
import retrofit2.Response
import java.io.IOException


object ApiErrorUtils {

    fun parseError(response: Response<*>): ApiError {

        val gson = GsonBuilder().create()
        val error: ApiError

        try {
            error = gson.fromJson(response.errorBody()?.string(), ApiError::class.java)
        } catch (e: IOException) {
            e.message?.let { loggerDebug(TAG + it) }
            return ApiError()
        }
        return error
    }

}
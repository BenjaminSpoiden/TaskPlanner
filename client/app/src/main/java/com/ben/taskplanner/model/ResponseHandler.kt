package com.ben.taskplanner.model

import okhttp3.ResponseBody

sealed class ResponseHandler<out T> {
    data class SuccessResponse<out T>(val response: T): ResponseHandler<T>()
    data class FailureResponse(
        val isNetworkFailure: Boolean,
        val responseBody: ResponseBody? = null,
        val message: String? = null
    ): ResponseHandler<Nothing>()
    object Loading: ResponseHandler<Nothing>()
}
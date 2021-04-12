package com.ben.taskplanner.repository

import com.ben.taskplanner.model.ResponseHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository {

    suspend fun <T> callHandler(apiCall: suspend () -> T): ResponseHandler<T> {
        return withContext(Dispatchers.IO) {
            try {
                ResponseHandler.SuccessResponse(apiCall.invoke())
            }catch (e: Throwable) {
                when(e) {
                    is HttpException -> {
                        ResponseHandler.FailureResponse(isNetworkFailure = false, e.response()?.errorBody())
                    }
                    else -> {
                        ResponseHandler.FailureResponse(isNetworkFailure = true, null, e.message)
                    }
                }
            }
        }
    }
}
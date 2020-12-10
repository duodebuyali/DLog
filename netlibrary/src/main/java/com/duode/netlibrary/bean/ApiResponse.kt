package com.duode.netlibrary.bean

import com.duode.netlibrary.utils.ErrorHandler
import retrofit2.Response

/**
 * @author: hekang
 * @description: 为了让协程的请求数据不直接抛出异常，进行数据封装
 * 参考: https://github.com/android/architecture-components-samples/blob/main/GithubBrowserSample/app/src/main/java/com/android/example/github/api/ApiResponse.kt
 * @date: 2018/4/18 14:44
 */
sealed class ApiResponse<T> {
    companion object {
        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            //不把原始错误暴露给用户看到
            val apiException = ErrorHandler.handleException(e = error)

            return ApiErrorResponse(apiException.displayMsg)
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                // TODO: 2020/12/7 是否这里也直接当做错误处理?
                if (body == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(body = body)
                }
            } else {
                val msg = response.errorBody()?.string()
                // TODO: 2020/12/7 这里的错误信息，是否也需要特殊处理
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }
                ApiErrorResponse(errorMsg ?: "unknown error")
            }
        }
    }
}

class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()

data class ApiErrorResponse<T>(val errorMessage: String) : ApiResponse<T>()

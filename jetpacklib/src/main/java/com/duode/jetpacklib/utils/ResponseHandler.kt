package com.duode.jetpacklib.utils

import com.duode.jetpacklib.bean.BaseResponse
import com.duode.jetpacklib.consts.ExceptionConst
import com.duode.netlibrary.bean.ApiEmptyResponse
import com.duode.netlibrary.bean.ApiErrorResponse
import com.duode.netlibrary.bean.ApiResponse
import com.duode.netlibrary.bean.ApiSuccessResponse

/**
 * @author hekang
 * @des 统一的请求数据处理
 * @date 2020/12/8 14:37
 */
object ResponseHandler {

    fun <D> handleBaseResponse(baseResponse: BaseResponse<D>): D? {
        when (baseResponse.code) {
            ExceptionConst.SERVICE_CODE_OK -> {
                //服务器返回的正常数据
                return baseResponse.data
            }
            ExceptionConst.SERVICE_CODE_ILLEGAL_ARGUMENT -> {
                println("参数错误")
            }
            else -> {
                println("其他错误")
            }
        }
        return null
    }

    fun <D> handleApiResponse(apiResponse: ApiResponse<BaseResponse<D>>): D? {
        if (apiResponse is ApiSuccessResponse) {//网络请求成功
            return handleBaseResponse(apiResponse.body)
        } else if (apiResponse is ApiEmptyResponse) {//返回空数据
            println("无数据返回")
        } else {//请求异常
            val errorResponse = apiResponse as ApiErrorResponse
            println(errorResponse.errorMessage)
        }
        return null
    }
}
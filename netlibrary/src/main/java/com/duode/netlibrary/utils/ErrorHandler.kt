package com.duode.netlibrary.utils

import com.duode.netlibrary.bean.ApiException
import com.duode.netlibrary.consts.ErrorConst
import com.google.gson.JsonParseException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.text.ParseException

/**
 * @author hekang
 * @des 统一的错误处理
 * @date 2020/12/7 14:37
 */
object ErrorHandler {

    /**
     * 处理统一的异常情况
     * */
    fun handleException(e: Throwable): ApiException {
        // TODO: 2020/12/7 可以在这里对没类错误进行细分
        val exception: ApiException = if (e is HttpException) {//网络错误
            ApiException(ErrorConst.HTTP_ERROR, "网络异常!")
        } else if (e is ConnectException) {
            ApiException(ErrorConst.NETWORK_ERROR, "网络不给力!")
        } else if (e is JsonParseException || e is JSONException || e is ParseException) {
            ApiException(ErrorConst.PARSE_ERROR, "数据异常!")
        } else {
            ApiException(ErrorConst.UNKNOWN, "未知的错误!")
        }

        return exception

    }

}
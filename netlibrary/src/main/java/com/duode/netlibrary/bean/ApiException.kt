package com.duode.netlibrary.bean

/**
 * @author hekang
 * @des 用来记录网络请求的异常情况
 * @date 2020/12/4 16:51
 */
class ApiException(val code: Int, val displayMsg: String) : Exception() {

    override fun toString(): String {
        return "ApiException(code=$code, displayMsg='$displayMsg')"
    }

}
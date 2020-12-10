package com.duode.jitpacklib.bean

import com.google.gson.annotations.SerializedName

/**
 * @author hekang
 * @des 标准请求的错误数据处理，这个和逻辑相关联，是服务器和前端协商好的数据结构
 * @date 2020/12/7 17:36
 */
data class BaseResponse<D>(val data: D,
                           @SerializedName("message")
                           val msg: String,
                           @SerializedName("status")
                           val code: Int)
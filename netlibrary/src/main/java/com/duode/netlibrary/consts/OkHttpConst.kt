package com.duode.netlibrary.consts

/**
 * @author hekang
 * @des
 * @date 2020/12/7 14:02
 */
object OkHttpConst {
    //统一的连接超时时间
    const val CONNECT_TIME = 15L

    //统一的读取超时时间
    const val READ_TIME = 15L

    /**
     * 默认的内部统一请求的okHttpClient的key
     * */
    const val KEY_FOR_INNER_DEFAULT_CLIENT = "inner_default"

    /**
     * 默认的非统一请求的okHttpClient的key
     * */
    const val KEY_FOR_INNER_OTHER_CLIENT = "inner_other"

}
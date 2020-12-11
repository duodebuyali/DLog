package com.duode.jetpacklib.consts

/**
 * @author hekang
 * @des 和逻辑相关的错误信息；其实请求已经成功，但是服务器返回的异常
 * @date 2020/12/8 13:56
 */
object ExceptionConst {
    /**
     * 服务器返回请求错误
     */
    const val SERVICE_ERROR = 1004


    /**
     * 服务器返回错误中的错误码，这里暂时写几个例子
     * 200	成功返回
     * 401	没有接口访问权限
     * 403	无效请求
     * 404	地址不存在
     * 409	资源已经存在
     * 500	服务错误
     * 501	请求参数错误
     * 504	网络错误或超时(服务器内部超时或错误)
     * 510	进程错误
     *
     *
     * 606	token已失效
     * 680  帐号在另一台设备登录
     */
    const val SERVICE_CODE_OK = 200
    const val SERVICE_CODE_NO_PERMISSION = 401
    const val SERVICE_CODE_INVALID = 403
    const val SERVICE_CODE_NO_ADDRESS = 404
    const val SERVICE_CODE_NO_RESOURCE = 409
    const val SERVICE_CODE_NO_SERVICE = 500
    const val SERVICE_CODE_ILLEGAL_ARGUMENT = 501
    const val SERVICE_CODE_NO_NETWORK = 504
    const val SERVICE_CODE_PROCESS_ERROR = 510

    const val TOKEN_INVALID = 606
    const val LOGIN_ON_OTHER_DEVICE = 680
}
package com.duode.netlibrary

/**
 * @author hekang
 * @des
 * @date 2020/12/4 13:36
 */
interface BaseApi {

    /**
     * 请求的基础链接
     * 可以重写这个来改变retrofit的baseUrl
     * */
    val baseUrl: String

}
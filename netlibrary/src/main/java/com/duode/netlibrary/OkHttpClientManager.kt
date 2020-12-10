package com.duode.netlibrary

import com.duode.netlibrary.consts.OkHttpConst
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

/**
 * @author: hekang
 * @description:用来管理OkHttpClient
 * @date: 2020/12/4 15:28
 */
class OkHttpClientManager private constructor() {

    companion object {
        fun getInstance(): OkHttpClientManager {
            return Inner.instance
        }
    }

    private object Inner {
        val instance = OkHttpClientManager()
    }

    val okHttpClient by lazy {
        buildOkHttpClient()
    }

    private val mInterceptors by lazy {
        ArrayList<Interceptor>()
    }

    /**
     * 给默认的OkHttpClient添加Interceptor
     * */
    fun addInterceptor(interceptor: Interceptor) {
        mInterceptors.add(interceptor)
    }

    fun removeInterceptor(interceptor: Interceptor) {
        mInterceptors.remove(interceptor)
    }


    /**
     * 创建默认的OkHttpClient
     * 和服务器协商好统一域名的请求，会包含公共参数和公共头等信息
     * */
    private fun buildOkHttpClient(): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
        mInterceptors.forEach {
            clientBuilder.addInterceptor(it)
        }
        if (BuildConfig.DEBUG) {//打印调试日志
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(httpLoggingInterceptor)
        }
        clientBuilder.connectTimeout(OkHttpConst.CONNECT_TIME, TimeUnit.SECONDS)
        clientBuilder.readTimeout(OkHttpConst.READ_TIME, TimeUnit.SECONDS)

        return clientBuilder.build()
    }

    /**
     * 创建其他OkHttpClient，这个的配置应该是和原始的不同的
     * 比如这个client仅仅用来处理那些查询服务器状态的接口，响应时长很短，且不处理服务器的异常
     * */
    private fun buildOtherOkHttpClient(): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {//打印调试日志
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(httpLoggingInterceptor)
        }
        clientBuilder.connectTimeout(OkHttpConst.CONNECT_TIME, TimeUnit.SECONDS)
        clientBuilder.readTimeout(OkHttpConst.READ_TIME, TimeUnit.SECONDS)

        return clientBuilder.build()
    }

    /**
     * 增加其他特殊请求client,默认为原始okHttpClient
     * */
    val otherOkClient: OkHttpClient by lazy {
        buildOtherOkHttpClient()
    }

    /**
     * 2020/12/7
     * 防止后续出现需要定制多个okHttpClient的情况
     * */
    private val mClients by lazy {
        val map = ConcurrentHashMap<String, OkHttpClient>()
        map.put(OkHttpConst.KEY_FOR_INNER_DEFAULT_CLIENT, okHttpClient)
        map.put(OkHttpConst.KEY_FOR_INNER_OTHER_CLIENT, otherOkClient)

        map
    }

    fun addOkHttpClient(key: String, client: OkHttpClient) {
        if (key == OkHttpConst.KEY_FOR_INNER_DEFAULT_CLIENT || key == OkHttpConst.KEY_FOR_INNER_OTHER_CLIENT) {
            return
        }
        mClients.put(key, client)
    }

    fun getOkHttpClient(key: String): OkHttpClient? {
        return mClients[key]
    }

}
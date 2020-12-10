package com.duode.netlibrary

import com.duode.netlibrary.adapter.FlowCallAdapterFactory
import com.duode.netlibrary.adapter.LiveDataCallAdapterFactory
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author: hekang
 * @description:管理retrofit请求的基础类
 * @date: 2018/4/18 14:44
 */
abstract class ApiManager<S> : BaseApi {

    /**
     * 让服务只会创建一个
     * */
    val mService: S by lazy {
        createService()
    }

    /**
     * 生成retrofit请求服务的方法
     * */
    abstract val createService: () -> S

    val mRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .client(OkHttpClientManager.getInstance().okHttpClient)
            .baseUrl(baseUrl)
            .addCallAdapterFactory(FlowCallAdapterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
    }


    val mOtherRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .client(OkHttpClientManager.getInstance().otherOkClient)
            .baseUrl(baseUrl)
            .addCallAdapterFactory(FlowCallAdapterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
    }


    /**
     * 防止需要使用特殊的请求,比如改变了baseUrl或者client
     * */
    fun fetchCustomRetrofit(key: String, url: String = baseUrl): Retrofit {
        return Retrofit.Builder()
            .client(
                OkHttpClientManager.getInstance().getOkHttpClient(key)
                    ?: OkHttpClientManager.getInstance().okHttpClient
            )
            .baseUrl(url)
            .addCallAdapterFactory(FlowCallAdapterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
    }

}
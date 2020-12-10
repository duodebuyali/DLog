package com.duode.netlibrary.adapter

import com.duode.netlibrary.bean.ApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.CallAdapter
import retrofit2.CallAdapter.Factory
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @author: hekang
 * @description: 将okHttp的请求转化为flow的factory
 * 参考: https://github.com/MohammadSianaki/Retrofit2-Flow-Call-Adapter/tree/master/app/src
 * @date: 2018/4/18 14:44
 */
class FlowCallAdapterFactory private constructor() : Factory() {

    companion object {
        fun create(): FlowCallAdapterFactory {
            return FlowCallAdapterFactory()
        }
    }

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Flow::class.java) {
            return null
        }
        val observableType = getParameterUpperBound(0, returnType as ParameterizedType)
        val rawObservableType = getRawType(observableType)
        if (rawObservableType != ApiResponse::class.java) {
            throw IllegalArgumentException("type must be a resource")
        }
        if (observableType !is ParameterizedType) {
            throw IllegalArgumentException("resource must be parameterized")
        }

//        val rawFlowType = getRawType(responseType)
//        return if (rawFlowType == Response::class.java) {
//            check(responseType is ParameterizedType) { "Response must be parameterized as Response<Foo> or Response<out Foo>" }
//            ResponseCallAdapter<Any>(
//                    getParameterUpperBound(
//                            0,
//                            responseType
//                    )
//            )
//        } else {
//            BodyCallAdapter<Any>(responseType)
//        }
        val bodyType = getParameterUpperBound(0, observableType)
        return FlowCallAdapter<Any>(bodyType)
    }
}

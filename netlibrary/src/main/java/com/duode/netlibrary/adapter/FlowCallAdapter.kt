package com.duode.netlibrary.adapter


import com.duode.netlibrary.bean.ApiResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

/**
 * @author: hekang
 * @description: 将okHttp的请求转化为flow的具体实现
 * 参考: https://github.com/MohammadSianaki/Retrofit2-Flow-Call-Adapter/tree/master/app/src
 * @date: 2018/4/18 14:44
 */
class FlowCallAdapter<R>(private val responseType: Type) :
        CallAdapter<R, Flow<ApiResponse<R>>> {

    override fun responseType() = responseType

    @ExperimentalCoroutinesApi
    override fun adapt(call: Call<R>): Flow<ApiResponse<R>> {

        return flow {
            emit(
                    suspendCancellableCoroutine<ApiResponse<R>> {
                        call.enqueue(object : Callback<R> {
                            override fun onResponse(call: Call<R>, response: Response<R>) {
                                it.resume(ApiResponse.create(response)) {
                                    call.cancel()
                                }
                            }

                            override fun onFailure(call: Call<R>, throwable: Throwable) {
                                it.resume(ApiResponse.create(throwable)) {
                                    call.cancel()
                                }
                            }
                        })

                        it.invokeOnCancellation { call.cancel() }
                    }
            )

        }

    }
}

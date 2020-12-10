package com.duode.netlibrary.adapter


import androidx.lifecycle.LiveData
import com.duode.netlibrary.bean.ApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @author: hekang
 * @description: 将okHttp的请求转化为liveData的具体实现
 * 参考: https://github.com/android/architecture-components-samples/blob/main/GithubBrowserSample/app/src/main/java/com/android/example/github/util/LiveDataCallAdapter.kt
 * @date: 2018/4/18 14:44
 */
class LiveDataCallAdapter<R>(private val responseType: Type) :
        CallAdapter<R, LiveData<ApiResponse<R>>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<R>): LiveData<ApiResponse<R>> {
        return object : LiveData<ApiResponse<R>>() {
            private var started = AtomicBoolean(false)
            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {//判断是否需要发起请求
                    call.enqueue(object : Callback<R> {
                        override fun onResponse(call: Call<R>, response: Response<R>) {
                            postValue(ApiResponse.create(response))
                        }

                        override fun onFailure(call: Call<R>, throwable: Throwable) {
                            postValue(ApiResponse.create(throwable))
                        }
                    })
                }
            }

        }
    }
}

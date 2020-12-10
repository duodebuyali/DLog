package com.duode.dlog.test.net

import androidx.lifecycle.LiveData
import com.duode.dlog.test.bean.Weather
import com.duode.jitpacklib.bean.BaseResponse
import com.duode.netlibrary.bean.ApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

/**
 * @author hekang
 * @des
 * @date 2020/12/8 15:48
 */
interface TestApi {
    /**
     * 获取天气信息
     */
    @GET("zaihuishou/Kotlin-mvvm/master/data.json")
    fun fetchWeather(): LiveData<ApiResponse<BaseResponse<Weather>>>

    /**
     * 获取天气信息
     */
    @GET("zaihuishou/Kotlin-mvvm/master/data.json")
    fun pullWeather(): Flow<ApiResponse<BaseResponse<Weather>>>


    /**
     * 获取天气信息
     */
    @GET("zaihuishou/Kotlin-mvvm/master/data.json")
    suspend fun forceWeather(): BaseResponse<Weather>
}
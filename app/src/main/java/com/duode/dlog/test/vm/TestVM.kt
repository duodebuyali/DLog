package com.duode.dlog.test.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.duode.dlog.test.bean.Weather
import com.duode.dlog.test.net.TestApiStore
import com.duode.jitpacklib.BaseVM
import com.duode.jitpacklib.utils.ResponseHandler
import com.duode.netlibrary.utils.ErrorHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*

/**
 * @author hekang
 * @des
 * @date 2020/12/8 15:38
 */
class TestVM : BaseVM() {

    private val mApiStore by lazy {
        TestApiStore()
    }

    val mLiveData: LiveData<Weather?> = mApiStore.getWeatherLiveData()
        .map {
            val data =
                ResponseHandler.handleApiResponse(it)
            data
        }


    val mWeatherLiveData = liveData<Weather?> {
        emitSource(mApiStore.getWeatherLiveData()
            .map {
                val data =
                    ResponseHandler.handleApiResponse(it)
                data
            })
    }


    /**
     * TODO: 2020/12/8 后续可以直接将start 和 finally方法放在 vm中实现
     * */
    fun getWeatherFlow() {
        launchOnUITryCatch {
            mApiStore.getWeatherFlow()
                .onStart { println("getWeatherFlow--onStart") }
                .catch {
                    it.printStackTrace()
                }
                .onCompletion { println("getWeatherFlow--onCompletion") }
                .flowOn(Dispatchers.IO)
                .collect {
                    val data = ResponseHandler.handleApiResponse(it) ?: return@collect
                    println("getWeatherFlow:${data.wendu}")
                }

        }
    }

    fun getWeather(start: () -> Unit, finally: () -> Unit) {
        launchOnUITryCatch({
            start()
        }, {
            // FIXME: 2020/12/9 这里一定需要使用另外一个CoroutineScope包裹
//            val deferred = GlobalScope.async(Dispatchers.IO) {
//                mApiStore.getWeather()
//            }
//            val response = withContext(Dispatchers.IO) {
//                mApiStore.getWeather()
//            }
            val response = mApiStore.getWeather()
            val data = ResponseHandler.handleBaseResponse(response) ?: return@launchOnUITryCatch
            println("getWeather:${data.ganmao}")
        }, {
            finally()
        }, {
            val apiException = ErrorHandler.handleException(it)
            println("getWeather--error:${apiException.displayMsg}")
        })
    }

    fun testLiveData(): LiveData<String> {
        return liveData<String> {
            try {
                val weatherL = async(Dispatchers.IO) { mApiStore.getWeather() }.await()
                emit("suspend:${weatherL.data.ganmao}")
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

}
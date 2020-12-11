package com.duode.jetpacklib.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

/**
 * @author hekang
 * @des
 * @date 2020/12/8 17:07
 */
object FlowUtils {
    fun <T> Flow<T>.subc(dispatcher: CoroutineDispatcher = Dispatchers.IO): Flow<T> {
        return this.flowOn(dispatcher)
    }
}
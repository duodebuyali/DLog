package com.duode.log.logconsole.base.utils

import com.duode.log.logconsole.base.NetTagInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import java.lang.ref.WeakReference
import java.util.concurrent.ConcurrentHashMap

/**
 * @author hekang
 * @des 网络请求取消管理
 * @date 2020/9/14 11:36
 */
object NetCancelManager {

    /**
     * 用来记录网络请求
     * */
    private val mNetCache: ConcurrentHashMap<NetTagInfo, WeakReference<Job>> by lazy {
        ConcurrentHashMap<NetTagInfo, WeakReference<Job>>()
    }

    private fun fetchDisposable(tagInfo: NetTagInfo): Job? {
        return mNetCache[tagInfo]?.get()
    }

    fun cancelRequestByModule(moduleTag: String) {
        mNetCache.keys.forEach {
            if (it.isSameModule(moduleTag)) {
                val d = fetchDisposable(it)
                if (d?.isActive == true) {
                    runBlocking(Dispatchers.Default) {//这里取消任务，是否应该去IO线程
                        //是否应该记录取消的原因？这样可以进一步分析用户画像
                        d.cancel()
                    }
                }
            }
        }
    }
}
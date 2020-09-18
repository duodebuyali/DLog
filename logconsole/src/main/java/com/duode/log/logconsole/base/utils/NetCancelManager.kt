package com.duode.log.logconsole.base.utils

import com.duode.log.logconsole.base.NetTagInfo
import io.reactivex.disposables.Disposable
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
  private val mNetCache: ConcurrentHashMap<NetTagInfo, WeakReference<Disposable>> by lazy {
    ConcurrentHashMap<NetTagInfo, WeakReference<Disposable>>()
  }

  private fun fetchDisposable(tagInfo: NetTagInfo): Disposable? {
    return mNetCache[tagInfo]?.get()
  }

  fun cancelRequestByModule(moduleTag: String) {
    mNetCache.keys.forEach {
      if (it.isSameModule(moduleTag)) {
        val d = fetchDisposable(it)
        if (d?.isDisposed == false) {
          d.dispose()
        }
      }
    }
  }
}
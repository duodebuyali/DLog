package com.duode.dlog.test

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * @author hekang
 * @des
 * @date 2020/12/23 09:27
 */
class CustomLifecycleObserver : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        // 观察的lifecycleOwner触发ON_START时，会调用这个方法
    }
}
package com.duode.dlog.test

import android.app.Activity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

/**
 * @author hekang
 * @des
 * @date 2020/12/23 09:22
 */
class LifecycleActivity : Activity(), LifecycleOwner {

    private val mLifecycleRegistry by lazy {
        LifecycleRegistry(this)
    }

    override fun getLifecycle(): Lifecycle {
        return mLifecycleRegistry
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //关联 lifecycleObserver
        mLifecycleRegistry.addObserver(CustomLifecycleObserver())
        mLifecycleRegistry.currentState = Lifecycle.State.CREATED

    }

    override fun onStart() {
        super.onStart()
        mLifecycleRegistry.currentState = Lifecycle.State.STARTED
    }

    override fun onResume() {
        super.onResume()
        mLifecycleRegistry.currentState = Lifecycle.State.RESUMED
    }

    override fun onDestroy() {
        mLifecycleRegistry.currentState = Lifecycle.State.DESTROYED
        super.onDestroy()
    }

}
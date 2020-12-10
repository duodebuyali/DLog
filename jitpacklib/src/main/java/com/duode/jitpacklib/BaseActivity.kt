package com.duode.jitpacklib

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * @author hekang
 * @des
 * @date 2020/12/7 17:51
 */
open class BaseActivity : AppCompatActivity() {

    protected inline fun <reified DB : ViewDataBinding> binding(
        @LayoutRes resId: Int
    ): Lazy<DB> = lazy { DataBindingUtil.setContentView<DB>(this, resId) }


    /**
     * 默认的统一处理协程任务开始执行前的操作
     * 后续确认统一的loading提示dialogFragment之后可以使用
     * @param needShow 是否显示提示的对话框
     * */
    protected open fun onSubscribe(needShow: Boolean = true) {

    }

    /**
     * 默认的统一处理协程任务完成的操作
     * 后续确认统一的loading提示dialogFragment之后可以使用
     * */
    protected open fun onCompleted() {

    }
}
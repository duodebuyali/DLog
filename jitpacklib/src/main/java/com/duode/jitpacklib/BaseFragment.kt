package com.duode.jitpacklib

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * @author hekang
 * @des
 * @date 2020/12/7 17:51
 */
open class BaseFragment : Fragment() {

    protected inline fun <reified DB : ViewDataBinding> binding(
        inflater: LayoutInflater,
        @LayoutRes resId: Int,
        container: ViewGroup?
    ): DB = DataBindingUtil.inflate(inflater, resId, container, false)


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
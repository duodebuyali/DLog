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

}
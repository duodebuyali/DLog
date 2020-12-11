package com.duode.jetpacklib

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

}
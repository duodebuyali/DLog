package com.duode.jitpacklib

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider

/**
 * @author hekang
 * @des 单一的vm
 * @date 2020/12/9 16:15
 */
abstract class BaseVMActivity<VM : BaseVM> : BaseActivity() {

    /**
     * 生成的ViewModel
     * */
    val mVM by lazy {
        provideVM()
    }

    /**
     * 需要生成的VM
     * */
    abstract val providerVMClass: Class<VM>

    /**
     * 防止后续需要修改
     * */
    protected open fun provideVM(): VM {
        return ViewModelProvider(this).get(providerVMClass)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //让VM观察lifecycle
        lifecycle.addObserver(mVM)
    }
}
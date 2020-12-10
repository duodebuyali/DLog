package com.duode.jitpacklib

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider

/**
 * @author hekang
 * @des
 * @date 2020/12/7 17:51
 */
abstract class BaseVMFragment<VM : BaseVM> : BaseFragment() {

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
        return ViewModelProvider(activity!!).get(providerVMClass)
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //绑定view的lifecycle，不要绑定fragment的
        viewLifecycleOwner.lifecycle.addObserver(mVM)
        super.onViewCreated(view, savedInstanceState)
    }

}
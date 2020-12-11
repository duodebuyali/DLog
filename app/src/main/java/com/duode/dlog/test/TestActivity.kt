package com.duode.dlog.test

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.duode.dlog.R
import com.duode.dlog.databinding.ActivityTestBinding
import com.duode.dlog.test.vm.TestVM
import com.duode.jetpacklib.BaseVMActivity
import com.duode.jetpacklib.utils.CommonObserverManager
import kotlinx.android.synthetic.main.activity_test.*

/**
 * @author hekang
 * @des
 * @date 2020/12/8 15:33
 */
class TestActivity : BaseVMActivity<TestVM>() {

    override val providerVMClass: Class<TestVM> = TestVM::class.java

    private val mDB by binding<ActivityTestBinding>(R.layout.activity_test)

    companion object {
        fun getCallIntent(ctx: Context): Intent {
            return Intent(ctx, TestActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mDB.lifecycleOwner = this
        mDB.vm = mVM

        btn_suspend.setOnClickListener {
            mVM.getWeather({ println("btn_suspend--onStart")}, { })
        }

        CommonObserverManager.onStart = {
            println("CommonObserverManager--onStart")
        }
    }


}
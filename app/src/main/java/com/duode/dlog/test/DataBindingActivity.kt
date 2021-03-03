package com.duode.dlog.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.duode.dlog.R
import com.duode.dlog.databinding.ActivityDatabindingBinding
import com.duode.dlog.test.bean.TData
import com.duode.dlog.test.bean.User

/**
 * @author hekang
 * @des
 * @date 2020/12/21 17:08
 */
class DataBindingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityDatabindingBinding>(this,
            R.layout.activity_databinding)
            .apply {
                user = User("xx", 19)
                data = TData().apply {
                    a = "a"
                }
            }
    }

}
package com.duode.dlog

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * @author hekang
 * @des
 * @date 2021/3/9 13:47
 */
class LayoutActivity : AppCompatActivity() {

    companion object {
        fun getCallIntent(ctx: Context): Intent {
            return Intent(ctx, LayoutActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout)
    }
}
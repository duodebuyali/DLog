package com.duode.log.logconsole

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.duode.jetpacklib.BaseActivity
import com.duode.log.logconsole.bean.ConsoleConfigData
import com.duode.log.logconsole.consts.ConsoleConst
import com.duode.log.logconsole.utils.ScreenUtils


/**
 * @author hekang
 * @des
 * @date 2020/9/15 16:48
 */
class ConsoleActivity : BaseActivity() {

    companion object {
        fun getCallIntent(ctx: Context, configData: ConsoleConfigData): Intent {
            val intent = Intent(ctx, ConsoleActivity::class.java)
            intent.putExtra(ConsoleConst.EXTRA_CONSOLE_CONFIG, configData)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.activity_console)

        val data = intent.getParcelableExtra<ConsoleConfigData>(ConsoleConst.EXTRA_CONSOLE_CONFIG)
        if (data == null) {
            Toast.makeText(this, "请使用正确的配置信息!", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        initWindow(data)

        //传递数据到fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_console, ConsoleFragment.getInstance(data))
            .commitAllowingStateLoss()
    }

    /**
     * 设置activity大小
     * */
    private fun initWindow(data: ConsoleConfigData) {
        setFinishOnTouchOutside(true)
        val win = this.window
        val lp = win.attributes
        //设置窗口宽度
        lp.width = (ScreenUtils.getScreenWidth(this) * data.widthRadio).toInt()
        //设置窗口高度
        lp.height = (ScreenUtils.getScreenHeight(this) * data.heightRadio).toInt()
        //设置Dialog位置
        lp.gravity = data.gravity
        win.attributes = lp
    }


}
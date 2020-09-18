package com.duode.dlog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.duode.log.logconsole.ConsoleActivity
import com.duode.log.logconsole.adapter.ConsoleAdapter
import com.duode.log.logconsole.bean.ConsoleConfigData
import com.duode.log.logconsole.bean.QueryConfigData
import com.duode.loglibrary.LogUtils
import com.duode.loglibrary.adapter.AndroidLogAdapter
import com.duode.loglibrary.adapter.CsvLogAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val csvPath = cacheDir.absolutePath + "/log.csv"
        LogUtils.addLogAdapters(
            AndroidLogAdapter(),
            CsvLogAdapter(csvPath),
            ConsoleAdapter(this@MainActivity)
        )

        LogUtils.i("MainActivity", 1, "onCreate")
        LogUtils.e("MainActivity", 1, "onCreate")

        btn_log.setOnClickListener { //flag可以自定义，用来自己进行限制
            LogUtils.d("AppCompatActivity", 1, "btn_log")
            LogUtils.d("MainActivity", 1, "btn_log")
            LogUtils.e("MainActivity", 1, "btn_log")
        }
        btn_test.setOnClickListener {
            val configData = ConsoleConfigData(
//                Gravity.CENTER,
//                widthRadio = 0.8f,
//                heightRadio = 0.8f,
                queryConfigData = QueryConfigData(
                    querySelfTag = "MainActivity",
                    queryMethodName = "%onClick"
                )
//                QueryConfigData()
            )
            startActivity(ConsoleActivity.getCallIntent(this@MainActivity, configData))
        }
    }

}
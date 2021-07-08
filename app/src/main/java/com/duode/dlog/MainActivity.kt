package com.duode.dlog

import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import com.duode.dlog.algorithm.SortUtils
import com.duode.dlog.test.TActivity
import com.duode.dlog.test.TestActivity
import com.duode.log.logconsole.ConsoleActivity
import com.duode.log.logconsole.adapter.ConsoleAdapter
import com.duode.log.logconsole.bean.ConsoleConfigData
import com.duode.log.logconsole.bean.QueryConfigData
import com.duode.loglibrary.LogUtils
import com.duode.loglibrary.adapter.AndroidLogAdapter
import com.duode.loglibrary.adapter.CsvLogAdapter
import com.duode.ziplibrary.OnZipStatusListener
import com.duode.ziplibrary.ZipUtils
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        var needDestroy = false
    }

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
            LogUtils.e(msg = "btn_log")

            LogUtils.e(msg = "btn_log;${resources.getString(R.string.app_name)}")
            needDestroy = true

        }

        btn_vm.setOnClickListener { //flag可以自定义，用来自己进行限制
            startActivity(TestActivity.getCallIntent(this))
        }

        btn_layout.setOnClickListener {
//            startActivity(LayoutActivity.getCallIntent(this))
            startActivity(TActivity.getCallIntent(this))
        }

        val a = intArrayOf(8, 1, 2, 44, 4, 656, 0, 33, 1, 5)
        btn_sort.setOnClickListener {
            SortUtils.insertSort(a)
        }

        btn_console.setOnClickListener {
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

        val mArr = arrayOf(MString("1"), MString("2"))
        println("mArr:${mArr.contentToString()}")

        btn_zip.setOnClickListener {
            Thread {
                val needZipFilePath = cacheDir.absolutePath + "/log.csv"
                val destPath = cacheDir.absolutePath + "/log.zip"
                ZipUtils.zip(needZipFilePath, destPath, listener = object : OnZipStatusListener {
                    override fun onStart() {
                        LogUtils.e(msg = "btn_zip--onStart")
                    }

                    override fun onProgress(progress: Int) {
                        LogUtils.e(msg = "btn_zip--onProgress:$progress")
                    }

                    override fun onCompleted(path: String) {
                        LogUtils.e(msg = "btn_zip--onCompleted:$path")
                    }

                    override fun onError(exception: Exception) {
                        LogUtils.e(msg = "btn_zip--onError:${exception.localizedMessage}")
                    }
                })
            }.start()

        }
        btn_unzip.setOnClickListener {
            Thread {
//                val zipFilePath = cacheDir.absolutePath + "/log.zip"
                val zipFilePath =
                    Environment.getExternalStorageDirectory().absolutePath + "/documents/traces.zip"
                ZipUtils.unzip(File(zipFilePath), listener = object : OnZipStatusListener {
                    override fun onStart() {
                        LogUtils.e(msg = "btn_unzip--onStart")
                    }

                    override fun onProgress(progress: Int) {
                        LogUtils.e(msg = "btn_unzip--onProgress:$progress")
                    }

                    override fun onCompleted(path: String) {
                        LogUtils.e(msg = "btn_unzip--onCompleted:$path")
                    }

                    override fun onError(exception: Exception) {
                        LogUtils.e(msg = "btn_unzip--onError:${exception.localizedMessage}")
                    }
                })
            }.start()

        }
    }

    internal open class MObject<T> {
        var t: T? = null
    }

    internal class MString(s: String) : MObject<String>() {
        init {
            t = s
        }

        override fun toString(): String {
            return "t:$t"
        }
    }
}
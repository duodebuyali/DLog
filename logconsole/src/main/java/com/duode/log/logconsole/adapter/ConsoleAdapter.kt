package com.duode.log.logconsole.adapter

import android.content.Context
import com.duode.log.logconsole.db.LogInfoDBStore
import com.duode.loglibrary.bean.LogInfoData
import com.duode.loglibrary.listener.LogAdapter
import kotlinx.coroutines.*
import java.util.concurrent.Executors

/**
 * @author hekang
 * @des 插入日志到数据库
 * @date 2020/9/1 15:17
 */
class ConsoleAdapter(private val ctx: Context) : LogAdapter {

    /**
     * 用来插入数据
     * */
    private val mComputationDispatcher by lazy {
        //如果想要一只顺序插入使用这个
//        Executors.newSingleThreadExecutor().asCoroutineDispatcher()
        //设置为cpu的核数
        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
            .asCoroutineDispatcher()
    }

    private val mDBStore by lazy {
        LogInfoDBStore(ctx)
    }

    override fun isEnable(logLevel: Int, selfTag: String, flag: Int): Boolean {
        return true
    }

    override fun log(logInfo: LogInfoData) {
        //插入到数据库
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                mDBStore.insert(logInfo)
            }
        }
    }
}
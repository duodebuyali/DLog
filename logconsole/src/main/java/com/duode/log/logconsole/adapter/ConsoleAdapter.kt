package com.duode.log.logconsole.adapter

import android.content.Context
import com.duode.log.logconsole.base.utils.RxJavaHelper.computationSubc
import com.duode.log.logconsole.db.LogInfoRepository
import com.duode.loglibrary.bean.LogInfoData
import com.duode.loglibrary.listener.LogAdapter

/**
 * @author hekang
 * @des 插入日志到数据库
 * @date 2020/9/1 15:17
 */
class ConsoleAdapter(private val ctx: Context) : LogAdapter {

    private val mRepository by lazy {
        LogInfoRepository(ctx)
    }

    override fun isEnable(logLevel: Int, selfTag: String, flag: Int): Boolean {
        return true
    }

    override fun log(logInfo: LogInfoData) {
        //插入到数据库
        mRepository.insert(logInfo)
            .computationSubc()
            .subscribe()
    }
}
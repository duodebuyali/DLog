package com.duode.loglibrary.adapter

import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import com.duode.loglibrary.bean.LogInfoData
import com.duode.loglibrary.consts.CsvLogConst
import com.duode.loglibrary.listener.LogAdapter
import com.duode.loglibrary.utils.LogInfoUtils
import com.duode.loglibrary.utils.WriteHandler

/**
 * @author hekang
 * @des 写入日志到文件中，格式为.csv
 * @date 2020/9/9 15:02
 *
 * @param filePath 日志写入的文件的绝对地址;如果需要文件读写权限，需要在之前就进行申请
 */
class CsvLogAdapter(private val filePath: String) : LogAdapter {
    //用来处理写数据的消息队列
    private val writeHandler: CsvWriteHandler

    init {
        //创建写文件的线程;使用这个保证一个消息写完，才进行下一次写操作
        val ht = HandlerThread("CsvLogAdapter")
        ht.start()
        writeHandler = CsvWriteHandler(ht.looper, filePath)
    }


    override fun isEnable(logLevel: Int, selfTag: String, flag: Int): Boolean {
        return true
    }

    override fun log(logInfo: LogInfoData) {
        //过滤日志消息中可能存在的换行符
        var msg = logInfo.msg
        if (msg.contains(CsvLogConst.NEW_LINE)) {
            msg = msg.replace(CsvLogConst.NEW_LINE.toRegex(), CsvLogConst.NEW_LINE_REPLACEMENT)
        }

        //构建数据
        val sb = StringBuilder()
        sb.append(LogInfoUtils.fetchDefaultDateByMills(logInfo.mills))
            .append(CsvLogConst.SEPARATOR)
            .append(logInfo.globalTag)
            .append(CsvLogConst.SEPARATOR)
            .append(logInfo.selfTag)
            .append(CsvLogConst.SEPARATOR)
            .append(LogInfoUtils.fetchLogLevelStr(logInfo.logLevel))
            .append(CsvLogConst.SEPARATOR)
            .append(logInfo.className)
            .append(CsvLogConst.SEPARATOR)
            .append(logInfo.methodName)
            .append(CsvLogConst.SEPARATOR)
            .append("${logInfo.fileName}:${logInfo.logPosition}")
            .append(CsvLogConst.SEPARATOR)
            .append(msg)
            .append(CsvLogConst.SEPARATOR)
            .append(logInfo.threadName)
            .append(CsvLogConst.NEW_LINE)

        //开始写数据到文件中
        val message = Message.obtain()
        message.what = 1
        message.obj = sb.toString()
        writeHandler.sendMessage(message)

    }

    /**
     * 用来写入数据到文件中
     * */
    private class CsvWriteHandler(looper: Looper, filePath: String) : WriteHandler(looper, filePath)

}
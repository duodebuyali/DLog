package com.duode.loglibrary.utils

import com.duode.loglibrary.LogUtils
import com.duode.loglibrary.bean.LogInfoData
import com.duode.loglibrary.consts.CsvLogConst
import com.duode.loglibrary.consts.LogConst
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author hekang
 * @des 用来获取日志信息的工具类
 * @date 2020/8/27 16:30
 */
object LogInfoUtils {

    /**
     * 获取对应类的方法在stack中的下标
     * @return 返回的下标是 一直匹配成功，直到下一个匹配不成功的 下标-1
     * */
    internal fun fetchLogIndexInStack(trace: Array<StackTraceElement>, className: String): Int {
        //不从0开始遍历，从已知的已经可以过滤的stack信息之后再开始遍历
        var index = LogConst.MIN_STACK_OFFSET
        var hasMatch = false
        while (index < trace.size) {
            val stackTraceElement = trace[index]
            if (stackTraceElement.className == className) {
                hasMatch = true
            }
            if (hasMatch && stackTraceElement.className != className) {
                return --index
            }
            index++
        }
        return -1
    }

    /**
     * 将全局tag和自有tag转化为给Android系统使用的tag
     * */
    internal fun formatTag(globalTag: String, tag: String): String {
        return "$globalTag-$tag"
    }

    /**
     * 构建打印所需的tag
     * */
    internal fun buildLogInfo(
        mills: Long,
        globalTag: String,
        tag: String,
        flag: Int,
        logLevel: Int,
        msg: String
    ): LogInfoData {
        val trace = Thread.currentThread().stackTrace
        val logIndex = fetchLogIndexInStack(trace, LogUtils::class.java.name)
        //真正执行日志打印方法的下标，比logIndex大1
        val eventIndex = logIndex + 1
        return LogInfoData(
            flag, globalTag, tag, msg, logLevel,
            trace[eventIndex].fileName, trace[eventIndex].className, trace[eventIndex].methodName,
            Thread.currentThread().name, trace[eventIndex].lineNumber.toString(), mills
        )
    }

    /**
     * 获取日志等级对应的字符串
     * */
    fun fetchLogLevelStr(value: Int): String {
        return when (value) {
            LogConst.LEVEL_VERBOSE -> "VERBOSE"
            LogConst.LEVEL_DEBUG -> "DEBUG"
            LogConst.LEVEL_INFO -> "INFO"
            LogConst.LEVEL_WARN -> "WARN"
            LogConst.LEVEL_ERROR -> "ERROR"
            LogConst.LEVEL_ASSERT -> "ASSERT"
            else -> "UNKNOWN"
        }
    }

    /**
     * 获取毫秒数对应的时间（系统默认的地区）
     * */
    fun fetchDefaultDateByMills(mills: Long, timeFormat: String = CsvLogConst.DATE_FORMAT): String {
        val dateFormat = SimpleDateFormat(timeFormat, Locale.getDefault())
        return dateFormat.format(mills)
    }
}
package com.duode.log.logconsole.transforms

import com.duode.log.logconsole.db.LogInfoTable
import com.duode.loglibrary.bean.LogInfoData
import com.duode.loglibrary.utils.LogInfoUtils

/**
 * @author hekang
 * @des 日志相关的数据转化帮助类
 * @date 2020/9/10 16:10
 */
object LogInfoTransform {

    /**
     * 将本地数据转化为数据库数据
     * */
    fun dataToDb(data: LogInfoData): LogInfoTable {

        return LogInfoTable(
            data.flag,
            data.globalTag,
            data.selfTag,
            data.msg,
            data.logLevel,
            data.fileName,
            data.className,
            data.methodName,
            data.threadName,
            data.logPosition,
            data.mills
        )

    }

    /**
     * 将数据转化为map；这里主要为了提供给table使用
     * */
    fun dbToMap(data: LogInfoTable): LinkedHashMap<String, String> {
        val map = LinkedHashMap<String, String>()
        map.put("_id", "${data.id}")
        map.put("date", LogInfoUtils.fetchDefaultDateByMills(data.mills))
        map.put("flag", "${data.flag}")
        map.put("globalTag", data.globalTag)
        map.put("selfTag", data.selfTag)
        map.put("msg", data.msg)
        map.put("logLevel", LogInfoUtils.fetchLogLevelStr(data.logLevel))
        map.put("filePosition", "${data.fileName}:${data.logPosition}")
        map.put("classPosition", "${data.className}.${data.methodName}")
        map.put("threadName", data.threadName)

        return map
    }
}
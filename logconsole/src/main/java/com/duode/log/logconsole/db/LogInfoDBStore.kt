package com.duode.log.logconsole.db

import android.content.Context
import android.text.TextUtils
import com.duode.log.logconsole.consts.ConsoleConst
import com.duode.log.logconsole.transforms.LogInfoTransform
import com.duode.loglibrary.bean.LogInfoData

/**
 * @author hekang
 * @des 日志信息暴露给外部使用的数据管理类
 * 网络请求的数据也在这里，包含数据的解析和转化
 * @date 2020/9/10 16:36
 *
 */
class LogInfoDBStore(private val ctx: Context) {

    // TODO: 2020/12/9 后续应该有一个ApplicationUtils或ActivityUtils来获取Context
    private val mDao by lazy {
        LogInfoDb.getInstance(ctx).logInfoDao()
    }

    suspend fun querySelfTagGroup(): List<String> {
        return mDao.querySelfGroup().map { it.selfTag }
    }

    // TODO: 2020/12/11 这里也可以修改为实现每个tableName的分组查询，根据不同的itemName调用不同的dao方法
    suspend fun queryGroupByName(itemName: String): List<String> {
        val list = mDao.queryGroup()
        //这里去重指定的itemName
        val map = HashMap<String, String>()
        //flag,globalTag,selfTag,logLevel,fileName,className,threadName
        list.forEach {
            when (itemName) {
                "flag" -> {
                    map.put("${it.flag}", "${it.flag}")
                }
                "globalTag" -> {
                    map.put(it.globalTag, it.globalTag)
                }
                "selfTag" -> {
                    map.put(it.selfTag, it.selfTag)
                }
                "logLevel" -> {
                    map.put("${it.logLevel}", "${it.logLevel}")
                }
                "fileName" -> {
                    map.put(it.fileName, it.fileName)
                }
                "className" -> {
                    val name = it.className
                    map.put(name.substring(name.lastIndexOf(".") + 1), it.className)
                }
                "threadName" -> {
                    map.put(it.threadName, it.threadName)
                }
                else -> {//如果没有匹配到，返回空

                }

            }
        }
        return map.keys.toList()
    }

    suspend fun insert(data: LogInfoData) {
        val table = LogInfoTransform.dataToDb(data)
        mDao.insert(table)
    }

    suspend fun queryListByTag(
        globalTag: String,
        selfTag: String = ""
    ): MutableList<LogInfoTable> {
        return if (TextUtils.isEmpty(selfTag)) {
            mDao.queryListByTag(globalTag, Int.MAX_VALUE)
        } else {
            mDao.queryListByTag(globalTag, selfTag)
        }
    }

    suspend fun queryListByFileName(fileName: String): MutableList<LogInfoTable> {
        return mDao.queryListByFileName(fileName)
    }

    suspend fun queryListByClassName(classNameSuffix: String): MutableList<LogInfoTable> {
        return mDao.queryListByClassName(classNameSuffix)
    }

    suspend fun query(
        queryFlag: Int,
        queryGlobalTag: String,
        querySelfTag: String,
        queryFileName: String,
        queryClassName: String,
        queryMethodName: String
    ): MutableList<LogInfoTable> {

        val flagMathStr = if (queryFlag == ConsoleConst.NONE_MATCH_FLAG) "%" else "$queryFlag"
        return mDao.queryList(
            flagMathStr,
            queryGlobalTag,
            querySelfTag,
            queryFileName,
            //由于返回的className，做了subString，增加通配符
            "%$queryClassName",
            queryMethodName
        )
    }

}
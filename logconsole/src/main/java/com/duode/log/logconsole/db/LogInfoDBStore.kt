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
            queryClassName,
            queryMethodName
        )
    }

}
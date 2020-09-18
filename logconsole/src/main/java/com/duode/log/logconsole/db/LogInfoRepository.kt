package com.duode.log.logconsole.db

import android.content.Context
import android.text.TextUtils
import com.duode.log.logconsole.consts.ConsoleConst
import com.duode.log.logconsole.transforms.LogInfoTransform
import com.duode.loglibrary.bean.LogInfoData
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * @author hekang
 * @des 日志信息暴露给外部使用的数据管理类
 * 网络请求的数据也在这里，包含数据的解析和转化
 * @date 2020/9/10 16:36
 *
 */
class LogInfoRepository(private val ctx: Context) {

    private val mDao by lazy {
        LogInfoDb.getInstance(ctx).logInfoDao()
    }

    fun insert(data: LogInfoData): Completable {
        val table = LogInfoTransform.dataToDb(data)
        return mDao.insert(table)
    }

    fun queryListByTag(
        globalTag: String,
        selfTag: String = ""
    ): Flowable<MutableList<LogInfoTable>> {
        return if (TextUtils.isEmpty(selfTag)) {
            mDao.queryListByTag(globalTag, Int.MAX_VALUE)
        } else {
            mDao.queryListByTag(globalTag, selfTag)
        }
    }

    fun queryListByFileName(fileName: String): Flowable<MutableList<LogInfoTable>> {
        return mDao.queryListByFileName(fileName)
    }

    fun queryListByClassName(classNameSuffix: String): Flowable<MutableList<LogInfoTable>> {
        return mDao.queryListByClassName(classNameSuffix)
    }

    fun query(
        queryFlag: Int,
        queryGlobalTag: String,
        querySelfTag: String,
        queryFileName: String,
        queryClassName: String,
        queryMethodName: String
    ): Flowable<MutableList<LogInfoTable>> {

        val flagMathStr= if (queryFlag==ConsoleConst.NONE_MATCH_FLAG) "%" else "$queryFlag"
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
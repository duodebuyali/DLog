package com.duode.log.logconsole

import android.content.Context
import com.duode.log.logconsole.bean.QueryConfigData
import com.duode.log.logconsole.db.LogInfoDBStore
import com.duode.log.logconsole.db.LogInfoTable
import com.duode.log.logconsole.transforms.LogInfoTransform

/**
 * @author hekang
 * @des
 * @date 2020/9/16 11:28
 */
class ConsoleDataSource(private val ctx: Context) {

    private val mRepository by lazy {
        LogInfoDBStore(ctx)
    }

    suspend fun queryListByTag(
        globalTag: String,
        selfTag: String = ""
    ): List<LinkedHashMap<String, String>> {
        return transform(mRepository.queryListByTag(globalTag, selfTag))
    }

    suspend fun query(configData: QueryConfigData): List<LinkedHashMap<String, String>> {
        return transform(
            mRepository.query(
                configData.queryFlag, configData.queryGlobalTag, configData.querySelfTag,
                configData.queryFileName, configData.queryClassName, configData.queryMethodName
            )
        )
    }

    private fun transform(flowable: MutableList<LogInfoTable>): List<LinkedHashMap<String, String>> {
        return flowable.map { data ->
            LogInfoTransform.dbToMap(data)
        }
    }
}
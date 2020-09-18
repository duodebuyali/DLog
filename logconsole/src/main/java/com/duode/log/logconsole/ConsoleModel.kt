package com.duode.log.logconsole

import android.content.Context
import com.duode.log.logconsole.base.BaseModel
import com.duode.log.logconsole.base.utils.RxJavaHelper.standSubc
import com.duode.log.logconsole.bean.QueryConfigData
import com.duode.log.logconsole.db.LogInfoRepository
import com.duode.log.logconsole.db.LogInfoTable
import com.duode.log.logconsole.transforms.LogInfoTransform
import io.reactivex.Flowable

/**
 * @author hekang
 * @des
 * @date 2020/9/16 11:28
 */
class ConsoleModel(private val ctx: Context) : BaseModel() {

    private val mRepository by lazy {
        LogInfoRepository(ctx)
    }

    fun queryListByTag(
        globalTag: String,
        selfTag: String = ""
    ): Flowable<List<LinkedHashMap<String, String>>> {
        return transform(mRepository.queryListByTag(globalTag, selfTag))
    }

    fun query(configData: QueryConfigData): Flowable<List<LinkedHashMap<String, String>>> {
        return transform(mRepository.query(configData.queryFlag,configData.queryGlobalTag,configData.querySelfTag,
        configData.queryFileName,configData.queryClassName,configData.queryMethodName))
    }

    private fun transform(flowable: Flowable<MutableList<LogInfoTable>>): Flowable<List<LinkedHashMap<String, String>>> {
        return flowable.map {
            it.map { data ->
                LogInfoTransform.dbToMap(data)
            }
        }.standSubc()
    }
}
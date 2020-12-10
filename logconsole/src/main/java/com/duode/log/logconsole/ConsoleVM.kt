package com.duode.log.logconsole

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bin.david.form.data.table.MapTableData
import com.duode.jitpacklib.BaseVM
import com.duode.log.logconsole.bean.LogRuleData
import com.duode.log.logconsole.bean.QueryConfigData
import com.duode.log.logconsole.consts.ConsoleConst
import com.duode.loglibrary.LogUtils
import com.duode.loglibrary.consts.LogConst
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

/**
 * @author hekang
 * @des
 * @date 2020/9/15 15:01
 */
class ConsoleVM : BaseVM() {
    var ctx: Context? = null

    private val mDataSource by lazy {
        ConsoleDataSource(ctx!!)
    }

    fun buildLogRuleData(queryConfigData: QueryConfigData): MutableList<MutableList<LogRuleData>> {
        val datas = mutableListOf<MutableList<LogRuleData>>()

        datas.add(buildFlagRuleValue(queryConfigData.queryFlag))
        datas.add(buildRuleValue("globalTag", queryConfigData.queryGlobalTag))
        datas.add(buildRuleValue("selfTag", queryConfigData.querySelfTag))
        datas.add(buildRuleValue("fileName", queryConfigData.queryFileName))
        datas.add(buildRuleValue("className", queryConfigData.queryClassName))
        datas.add(buildRuleValue("methodName", queryConfigData.queryMethodName))

        return datas
    }

    private fun buildFlagRuleValue(value: Int): MutableList<LogRuleData> {
        val datas = mutableListOf<LogRuleData>()
        if (value != ConsoleConst.NONE_MATCH_FLAG) {
            datas.add(
                LogRuleData(
                    "flag 匹配：",
                    "$value",
                    value.toString(),
                    true
                )
            )
        }
        datas.add(
            LogRuleData(
                "flag 匹配：",
                ConsoleConst.NONE_MATCH_DES,
                ConsoleConst.NONE_MATCH_FLAG.toString()
            )
        )
        return datas
    }

    private fun buildRuleValue(prefix: String, value: String): MutableList<LogRuleData> {
        val datas = mutableListOf<LogRuleData>()
        when (prefix) {
            "globalTag" -> {
                if (value != ConsoleConst.NONE_MATCH_GLOBAL_TAG) {
                    datas.add(LogRuleData("$prefix 匹配：", value, value, true))
                }
                datas.add(
                    LogRuleData(
                        "$prefix 匹配：",
                        ConsoleConst.NONE_MATCH_DES,
                        ConsoleConst.NONE_MATCH_GLOBAL_TAG
                    )
                )
            }
            "selfTag" -> {
                if (value != ConsoleConst.NONE_MATCH_SELF_TAG) {
                    datas.add(LogRuleData("$prefix 匹配：", value, value, true))
                }
                datas.add(
                    LogRuleData(
                        "$prefix 匹配：",
                        ConsoleConst.NONE_MATCH_DES,
                        ConsoleConst.NONE_MATCH_SELF_TAG
                    )
                )
            }
            "fileName" -> {
                if (value != ConsoleConst.NONE_MATCH_FILE_NAME) {
                    datas.add(LogRuleData("$prefix 匹配：", value, value, true))
                }
                datas.add(
                    LogRuleData(
                        "$prefix 匹配：",
                        ConsoleConst.NONE_MATCH_DES,
                        ConsoleConst.NONE_MATCH_FILE_NAME
                    )
                )
            }
            "className" -> {
                if (value != ConsoleConst.NONE_MATCH_CLASS_NAME) {
                    datas.add(LogRuleData("$prefix 匹配：", value, value, true))
                }
                datas.add(
                    LogRuleData(
                        "$prefix 匹配：",
                        ConsoleConst.NONE_MATCH_DES,
                        ConsoleConst.NONE_MATCH_CLASS_NAME
                    )
                )
            }
            "methodName" -> {
                if (value != ConsoleConst.NONE_MATCH_METHOD_NAME) {
                    datas.add(LogRuleData("$prefix 匹配：", value, value, true))
                }
                datas.add(
                    LogRuleData(
                        "$prefix 匹配：",
                        ConsoleConst.NONE_MATCH_DES,
                        ConsoleConst.NONE_MATCH_METHOD_NAME
                    )
                )
            }
            else -> {
            }
        }
        return datas
    }

    fun fetchLogs(globalTag: String = LogConst.DEFAULT_TAG_GLOBAL, tag: String = "") {
        launchOnUITryCatch {
            val deferred = async(Dispatchers.IO) { mDataSource.queryListByTag(globalTag, tag) }
            deferred.await()
        }

    }


    val mTableData: LiveData<MapTableData>
        get() = mutableLiveData
    private val mutableLiveData = MutableLiveData<MapTableData>()

    fun queryLogs(configData: QueryConfigData) {
        launchOnUITryCatch {
            val deferred = async(Dispatchers.IO) { mDataSource.query(configData) }
            val data = deferred.await()
            if (data.isNotEmpty()) {
                val tableData = MapTableData.create("日志信息", data as List<Any>)
                mutableLiveData.value = tableData
                LogUtils.d(msg = "mTableData:${mTableData.value};${mTableData.hasObservers()}")
            }
        }
    }

    /**
     * 生成查询语句
     * */
    private fun buildSql(configData: QueryConfigData): String {
        val sb = StringBuilder()
        if (configData.queryFlag != ConsoleConst.NONE_MATCH_FLAG) {
            sb.append("flag LIKE ${configData.queryFlag}")
        }
        if (configData.queryGlobalTag != ConsoleConst.NONE_MATCH_GLOBAL_TAG) {
            if (sb.isNotEmpty()) {
                sb.append("AND ")
            }
            sb.append("globalTag LIKE ${configData.queryGlobalTag}")
        }
        if (configData.querySelfTag != ConsoleConst.NONE_MATCH_SELF_TAG) {
            if (sb.isNotEmpty()) {
                sb.append("AND ")
            }
            sb.append("selfTag LIKE ${configData.querySelfTag}")
        }
        if (configData.queryFileName != ConsoleConst.NONE_MATCH_FILE_NAME) {
            if (sb.isNotEmpty()) {
                sb.append("AND ")
            }
            sb.append("fileName LIKE ${configData.queryFileName}")
        }
        if (configData.queryGlobalTag != ConsoleConst.NONE_MATCH_CLASS_NAME) {
            if (sb.isNotEmpty()) {
                sb.append("AND ")
            }
            sb.append("className LIKE ${configData.queryClassName}")
        }

        return sb.toString()
    }
}
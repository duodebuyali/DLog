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

    fun queryGroup() {
        launchOnUITryCatch {
            val groupList = mDataSource.queryGroup()
            val groupAll = mDataSource.queryGroupByName("selfTag")
            println("groupList:$groupList;groupAll:$groupAll")

        }
    }

    val mRuleData: LiveData<MutableList<MutableList<LogRuleData>>>
        get() = mutableRule
    private val mutableRule = MutableLiveData<MutableList<MutableList<LogRuleData>>>()

    fun buildLogRuleData(queryConfigData: QueryConfigData) {

        launchOnUITryCatch {
            val datas = mutableListOf<MutableList<LogRuleData>>()
            buildRuleValue(datas, "flag", queryConfigData.queryFlag.toString())
            buildRuleValue(datas, "globalTag", queryConfigData.queryGlobalTag)
            buildRuleValue(datas, "selfTag", queryConfigData.querySelfTag)
            buildRuleValue(datas, "fileName", queryConfigData.queryFileName)
            buildRuleValue(datas, "className", queryConfigData.queryClassName)
            buildRuleValue(datas, "methodName", queryConfigData.queryMethodName)

            mutableRule.postValue(datas)
        }

    }

    // TODO: 2020/12/10 对每一个显示的类型进行group查询，这样用户可以筛选任意选项
    private suspend fun buildRuleValue(
        ruleData: MutableList<MutableList<LogRuleData>>,
        prefix: String,
        value: String
    ) {
        val datas = mutableListOf<LogRuleData>()
        when (prefix) {
            "flag" -> {
                val isMatchAllFlag = value.toInt() == ConsoleConst.NONE_MATCH_FLAG
                if (!isMatchAllFlag) {
                    datas.add(
                        LogRuleData(
                            "$prefix 匹配：",
                            value,
                            value,
                            true
                        )
                    )
                } else {
                    datas.add(
                        LogRuleData(
                            "$prefix 匹配：",
                            ConsoleConst.NONE_MATCH_DES,
                            ConsoleConst.NONE_MATCH_FLAG.toString(), true
                        )
                    )
                }
                mDataSource.queryGroupByName(prefix)
                    .forEach {
                        datas.add(LogRuleData("$prefix 匹配：", it, it))
                    }

                if (!isMatchAllFlag) {
                    datas.add(
                        LogRuleData(
                            "$prefix 匹配：",
                            ConsoleConst.NONE_MATCH_DES,
                            ConsoleConst.NONE_MATCH_FLAG.toString()
                        )
                    )
                }
            }
            "globalTag" -> {
                val isMatchAllGlobalTag = value == ConsoleConst.NONE_MATCH_GLOBAL_TAG
                if (!isMatchAllGlobalTag) {
                    datas.add(LogRuleData("$prefix 匹配：", value, value, true))
                } else {
                    datas.add(
                        LogRuleData(
                            "$prefix 匹配：",
                            ConsoleConst.NONE_MATCH_DES,
                            ConsoleConst.NONE_MATCH_GLOBAL_TAG, true
                        )
                    )
                }
                mDataSource.queryGroupByName(prefix)
                    .forEach {
                        datas.add(LogRuleData("$prefix 匹配：", it, it))
                    }
                if (!isMatchAllGlobalTag) {
                    datas.add(
                        LogRuleData(
                            "$prefix 匹配：",
                            ConsoleConst.NONE_MATCH_DES,
                            ConsoleConst.NONE_MATCH_GLOBAL_TAG
                        )
                    )
                }
            }
            "selfTag" -> {
                val isMatchAllSelfTag = value == ConsoleConst.NONE_MATCH_SELF_TAG
                if (!isMatchAllSelfTag) {
                    datas.add(LogRuleData("$prefix 匹配：", value, value, true))
                } else {
                    datas.add(
                        LogRuleData(
                            "$prefix 匹配：",
                            ConsoleConst.NONE_MATCH_DES,
                            ConsoleConst.NONE_MATCH_SELF_TAG, true
                        )
                    )
                }
                mDataSource.queryGroupByName(prefix)
                    .forEach {
                        datas.add(LogRuleData("$prefix 匹配：", it, it))
                    }
                if (!isMatchAllSelfTag) {
                    datas.add(
                        LogRuleData(
                            "$prefix 匹配：",
                            ConsoleConst.NONE_MATCH_DES,
                            ConsoleConst.NONE_MATCH_SELF_TAG
                        )
                    )
                }
            }
            "fileName" -> {
                val isMatchAllFileName = value == ConsoleConst.NONE_MATCH_FILE_NAME
                if (!isMatchAllFileName) {
                    datas.add(LogRuleData("$prefix 匹配：", value, value, true))
                } else {
                    datas.add(
                        LogRuleData(
                            "$prefix 匹配：",
                            ConsoleConst.NONE_MATCH_DES,
                            ConsoleConst.NONE_MATCH_FILE_NAME, true
                        )
                    )
                }
                mDataSource.queryGroupByName(prefix)
                    .forEach {
                        datas.add(LogRuleData("$prefix 匹配：", it, it))
                    }
                if (!isMatchAllFileName) {
                    datas.add(
                        LogRuleData(
                            "$prefix 匹配：",
                            ConsoleConst.NONE_MATCH_DES,
                            ConsoleConst.NONE_MATCH_FILE_NAME
                        )
                    )
                }
            }
            "className" -> {
                val isMatchAllClassName = value == ConsoleConst.NONE_MATCH_CLASS_NAME
                if (!isMatchAllClassName) {
                    datas.add(LogRuleData("$prefix 匹配：", value, value, true))
                } else {
                    datas.add(
                        LogRuleData(
                            "$prefix 匹配：",
                            ConsoleConst.NONE_MATCH_DES,
                            ConsoleConst.NONE_MATCH_CLASS_NAME, true
                        )
                    )
                }
                mDataSource.queryGroupByName(prefix)
                    .forEach {
                        datas.add(LogRuleData("$prefix 匹配：", it, it))
                    }
                if (!isMatchAllClassName) {
                    datas.add(
                        LogRuleData(
                            "$prefix 匹配：",
                            ConsoleConst.NONE_MATCH_DES,
                            ConsoleConst.NONE_MATCH_CLASS_NAME
                        )
                    )
                }
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
        ruleData.add(datas)
    }

    val mTableData: LiveData<MapTableData>
        get() = mutableLiveData
    private val mutableLiveData = MutableLiveData<MapTableData>()

    fun queryLogs(start: () -> Unit, configData: QueryConfigData, completed: () -> Unit) {
        launchOnUITryCatch({ start() }, {
            val deferred = async(Dispatchers.IO) { mDataSource.query(configData) }
            val data = deferred.await()

            val tableData = MapTableData.create("日志信息", data as List<Any>)
            mutableLiveData.postValue(tableData)
            LogUtils.d(msg = "mTableData:${mTableData.value};${mTableData.hasObservers()}")
        }, { completed() })
    }

}
package com.duode.log.logconsole

import com.duode.log.logconsole.base.BasePresenter
import com.duode.log.logconsole.bean.QueryConfigData
import com.duode.log.logconsole.consts.ConsoleConst
import com.duode.loglibrary.consts.LogConst
import io.reactivex.subscribers.DisposableSubscriber

/**
 * @author hekang
 * @des
 * @date 2020/9/15 15:01
 */
class ConsolePresenter(private val view: ConsoleView) : BasePresenter<ConsoleView>(view) {

    private val mModel by lazy {
        ConsoleModel(view.getContext()!!)
    }

    fun fetchLogs(globalTag: String = LogConst.DEFAULT_TAG_GLOBAL, tag: String = "") {
        mModel.queryListByTag(globalTag, tag)
            .compose(view.bindLifecycle())
            .subscribe(object : DisposableSubscriber<List<LinkedHashMap<String, String>>>() {
                override fun onComplete() {
                }

                override fun onNext(t: List<LinkedHashMap<String, String>>) {
                    view.showLogs(t)
                }

                override fun onError(e: Throwable) {
                }

            })
    }


    fun queryLogs(configData: QueryConfigData) {
        mModel.query(configData)
            .compose(view.bindLifecycle())
            .subscribe(object : DisposableSubscriber<List<LinkedHashMap<String, String>>>() {
                override fun onComplete() {
                }

                override fun onNext(t: List<LinkedHashMap<String, String>>) {
                    view.showLogs(t)
                }

                override fun onError(e: Throwable) {
                    view.showLogs(emptyList())
                }

            })
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
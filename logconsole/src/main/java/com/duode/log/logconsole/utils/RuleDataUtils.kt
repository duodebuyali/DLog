package com.duode.log.logconsole.utils

import com.duode.log.logconsole.bean.LogRuleData
import com.duode.log.logconsole.bean.QueryConfigData

/**
 * @author hekang
 * @des
 * @date 2020/10/12 16:19
 */
object RuleDataUtils {
    /**
     * @param pos 需要设置选中的位置
     * */
    fun setupSelectedRuleData(datas: MutableList<LogRuleData>, pos: Int) {
        datas.forEachIndexed { index, logRuleData ->
            logRuleData.isSelected = index == pos
        }
    }

    /**
     * 获取规则对应的QueryConfigData对象
     * */
    fun buildQueryDataByList(datas: MutableList<MutableList<LogRuleData>>): QueryConfigData {
        val data = QueryConfigData()
        datas.forEachIndexed { index, mutableList ->
            run {
                val ruleData = mutableList.find {
                    it.isSelected
                } ?: return@run
                when (index) {
                    0 -> {
                        data.queryFlag = ruleData.value.toInt()
                    }
                    1 -> {
                        data.queryGlobalTag = ruleData.value
                    }
                    2 -> {
                        data.querySelfTag = ruleData.value
                    }
                    3 -> {
                        data.queryFileName = ruleData.value
                    }
                    4 -> {
                        data.queryClassName = ruleData.value
                    }
                    5 -> {
                        data.queryMethodName = ruleData.value
                    }
                }
            }
        }

        return data
    }
}
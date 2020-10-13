package com.duode.log.logconsole.listener

import com.duode.log.logconsole.bean.QueryConfigData

/**
 * @author hekang
 * @des
 * @date 2020/10/12 16:52
 */
interface OnQueryRuleChangeListener {
    fun onChange(queryConfigData: QueryConfigData)
}
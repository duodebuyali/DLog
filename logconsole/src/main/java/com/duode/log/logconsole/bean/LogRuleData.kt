package com.duode.log.logconsole.bean

/**
 * @author hekang
 * @des
 * @date 2020/10/12 11:36
 */
data class LogRuleData(
    var prefix: String,
    var showValue: String,
    var value: String,
    var isSelected: Boolean = false
)
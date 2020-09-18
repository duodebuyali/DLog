package com.duode.log.logconsole

import com.duode.log.logconsole.base.IBaseView
import com.duode.log.logconsole.db.LogInfoTable

/**
 * @author hekang
 * @des
 * @date 2020/9/15 15:01
 */
interface ConsoleView : IBaseView {

  fun showLogs(logs: List<LinkedHashMap<String, String>>)
}
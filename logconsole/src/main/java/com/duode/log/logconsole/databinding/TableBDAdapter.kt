package com.duode.log.logconsole.databinding

import androidx.databinding.BindingAdapter
import com.bin.david.form.core.SmartTable
import com.bin.david.form.data.table.MapTableData

/**
 * @author hekang
 * @des
 * @date 2020/12/10 10:51
 */
object TableBDAdapter {

    @BindingAdapter("setupTableData")
    @JvmStatic
    fun SmartTable<Any>.setupTableData(data: MapTableData?) {
        // FIXME: 2020/12/10 使用liveData绑定的数据，一定要注意，可能返回null的情况
        if (data != null) {
            this.tableData = data
        }
    }
}
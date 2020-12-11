package com.duode.log.logconsole.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.recyclerview.widget.RecyclerView
import com.duode.log.logconsole.R
import com.duode.log.logconsole.bean.LogRuleData
import com.duode.log.logconsole.listener.OnQueryRuleChangeListener
import com.duode.log.logconsole.utils.RuleDataUtils
import com.duode.log.logconsole.utils.ScreenUtils

/**
 * @author hekang
 * @des 查看的日志console的查询规则rv的adapter
 * @date 2020/10/10 18:41
 */
class LogRuleAdapter(
    private val mDatas: MutableList<MutableList<LogRuleData>>,
    private val mListener: OnQueryRuleChangeListener
) :
    RecyclerView.Adapter<LogRuleAdapter.LogRuleHolder>() {

    /**
     * 由于第一次spinner的选中事件不需要监听，增加判断
     * */
    private var isFinishFirst = BooleanArray(mDatas.size)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogRuleHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_console_rule, parent, false)
        return LogRuleHolder(v)
    }

    override fun onBindViewHolder(holder: LogRuleHolder, position: Int) {
        holder.divider.visibility = if (position == mDatas.size - 1) View.GONE else View.VISIBLE
        holder.spinner.adapter = RuleSpinnerAdapter(mDatas[position])
        holder.spinner.dropDownVerticalOffset = ScreenUtils.dp2px(holder.itemView.context, 50f)
        holder.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                pos: Int,
                id: Long
            ) {
                if (!isFinishFirst[position]) {
                    isFinishFirst[position] = true
                    return
                }
                //第一次初始化的选中，不需要进行查询
                //修改数据中的选中
                RuleDataUtils.setupSelectedRuleData(mDatas[position], pos)
                mListener.onChange(RuleDataUtils.buildQueryDataByList(mDatas))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }

    override fun getItemCount(): Int {
        return mDatas.size
    }

    class LogRuleHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val spinner: AppCompatSpinner = itemView.findViewById(R.id.value_spinner)
        val divider: View = itemView.findViewById(R.id.divider_view)

    }

    fun setData(datas: MutableList<MutableList<LogRuleData>>) {
        mDatas.clear()
        mDatas.addAll(datas)

        isFinishFirst = BooleanArray(mDatas.size)

        notifyDataSetChanged()
    }
}
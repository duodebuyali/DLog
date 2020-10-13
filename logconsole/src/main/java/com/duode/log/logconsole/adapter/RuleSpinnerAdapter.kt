package com.duode.log.logconsole.adapter

import android.annotation.SuppressLint
import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SpinnerAdapter
import android.widget.TextView
import com.duode.log.logconsole.R
import com.duode.log.logconsole.bean.LogRuleData

/**
 * @author hekang
 * @des spinner的适配器，如果需要高度自定义实现 SpinnerAdapter
 * @date 2020/10/12 15:07
 */
class RuleSpinnerAdapter(val datas: MutableList<LogRuleData>) : SpinnerAdapter {
    override fun registerDataSetObserver(observer: DataSetObserver?) {
    }

    override fun unregisterDataSetObserver(observer: DataSetObserver?) {
    }

    override fun getCount(): Int {
        return datas.size
    }

    override fun getItem(position: Int): Any {
        return datas[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    /**
     * 默认显示的view
     * */
    @SuppressLint("ViewHolder", "SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_spinner_rule, parent, false)
        val tv = v.findViewById<TextView>(R.id.value_tv)
        tv.text = "${datas[position].prefix} ${datas[position].showValue}"
        tv.isSelected = true

        val divider = v.findViewById<View>(R.id.divider_view)
        divider.visibility = if (position == datas.size - 1) View.GONE else View.VISIBLE

        return v
    }

    override fun getItemViewType(position: Int): Int {
        return 1
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun isEmpty(): Boolean {
        return false
    }

    /**
     * 下拉出来的列表显示的view
     * */
    @SuppressLint("SetTextI18n")
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_spinner_rule, parent, false)
        val tv = v.findViewById<TextView>(R.id.value_tv)
        tv.text = "${datas[position].prefix} ${datas[position].showValue}"
        tv.isSelected = datas[position].isSelected

        val divider = v.findViewById<View>(R.id.divider_view)
        divider.visibility = if (position == datas.size - 1) View.GONE else View.VISIBLE

        return v
    }

}
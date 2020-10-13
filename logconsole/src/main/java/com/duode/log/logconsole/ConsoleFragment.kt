package com.duode.log.logconsole

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bin.david.form.data.table.MapTableData
import com.duode.log.logconsole.adapter.LogRuleAdapter
import com.duode.log.logconsole.base.BaseFragment
import com.duode.log.logconsole.bean.ConsoleConfigData
import com.duode.log.logconsole.bean.QueryConfigData
import com.duode.log.logconsole.consts.ConsoleConst
import com.duode.log.logconsole.listener.OnQueryRuleChangeListener
import kotlinx.android.synthetic.main.fragment_console.view.*

/**
 * @author hekang
 * @des 用来展示调试信息的fragment
 * @date 2020/9/1 15:07
 */
class ConsoleFragment : BaseFragment(), ConsoleView, OnQueryRuleChangeListener {

    companion object {
        fun getInstance(data: ConsoleConfigData): ConsoleFragment {
            val f = ConsoleFragment()
            val bundle = Bundle()
            bundle.putParcelable(ConsoleConst.EXTRA_CONSOLE_CONFIG, data)
            f.arguments = bundle
            return f
        }
    }

    private val mPresenter by lazy {
        ConsolePresenter(this)
    }

    private val mLogs by lazy {
        ArrayList<LinkedHashMap<String, String>>()
    }

    private val mPd by lazy {
        val pd = ProgressDialog(context)
        pd.setMessage("正在获取日志信息...")
        pd.setCanceledOnTouchOutside(false)
        pd
    }

    private lateinit var mRootView: View

    private val mAdapter by lazy {
        LogRuleAdapter(mutableListOf(), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRootView = inflater.inflate(R.layout.fragment_console, container, false)

        mRootView.rule_rv.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        mRootView.rule_rv.adapter = mAdapter

        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val configData =
            arguments?.getParcelable<ConsoleConfigData>(ConsoleConst.EXTRA_CONSOLE_CONFIG) ?: return
        mAdapter.setData(mPresenter.buildLogRuleData(configData.queryConfigData))

        query(configData.queryConfigData)
    }

    private fun query(configData: QueryConfigData) {
        if (!mPd.isShowing) {
            mPd.show()
        }
        mPresenter.queryLogs(configData = configData)
    }

    private fun setupTable(logs: List<LinkedHashMap<String, String>>) {
        if (mLogs.isNotEmpty()) {
            mLogs.clear()
        }
        mLogs.addAll(logs)
        val tableData = MapTableData.create("日志信息", mLogs as List<Any>)
        mRootView.console_table.tableData = tableData
    }

    override fun showLogs(logs: List<LinkedHashMap<String, String>>) {
        if (mPd.isShowing) {
            mPd.dismiss()
        }
        setupTable(logs)
    }

    override fun onDestroyView() {
        if (mPd.isShowing) {
            mPd.dismiss()
        }
        super.onDestroyView()
    }

    override fun onChange(queryConfigData: QueryConfigData) {
        query(queryConfigData)
    }
}
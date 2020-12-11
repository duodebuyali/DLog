package com.duode.log.logconsole

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.duode.jitpacklib.BaseVMFragment
import com.duode.log.logconsole.adapter.LogRuleAdapter
import com.duode.log.logconsole.bean.ConsoleConfigData
import com.duode.log.logconsole.bean.LogRuleData
import com.duode.log.logconsole.bean.QueryConfigData
import com.duode.log.logconsole.consts.ConsoleConst
import com.duode.log.logconsole.databinding.FragmentConsoleBinding
import com.duode.log.logconsole.listener.OnQueryRuleChangeListener

/**
 * @author hekang
 * @des 用来展示调试信息的fragment
 * @date 2020/9/1 15:07
 */
class ConsoleFragment : BaseVMFragment<ConsoleVM>(), OnQueryRuleChangeListener {

    override val providerVMClass: Class<ConsoleVM>
        get() = ConsoleVM::class.java

    companion object {
        fun getInstance(data: ConsoleConfigData): ConsoleFragment {
            val f = ConsoleFragment()
            val bundle = Bundle()
            bundle.putParcelable(ConsoleConst.EXTRA_CONSOLE_CONFIG, data)
            f.arguments = bundle
            return f
        }
    }

    lateinit var mBD: FragmentConsoleBinding

    private val mPd by lazy {
        val pd = ProgressDialog(context)
        pd.setMessage("正在获取日志信息...")
        pd.setCanceledOnTouchOutside(false)
        pd
    }

    fun onSubscribe() {
        if (!mPd.isShowing) {
            mPd.show()
        }
    }

    fun onCompleted() {
        if (mPd.isShowing) {
            mPd.dismiss()
        }
    }

    private val mAdapter by lazy {
        LogRuleAdapter(mutableListOf(), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBD = binding(inflater, R.layout.fragment_console, container)
        //订阅liveData
        mBD.lifecycleOwner = viewLifecycleOwner

        return mBD.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val configData =
            arguments?.getParcelable<ConsoleConfigData>(ConsoleConst.EXTRA_CONSOLE_CONFIG) ?: return

        mBD.vm = mVM
        mVM.ctx = context
        mBD.config = configData.queryConfigData

        mBD.ruleRv.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        mBD.ruleRv.adapter = mAdapter

        //获取顶部条件
        mVM.mRuleData.observe(viewLifecycleOwner,
            Observer<MutableList<MutableList<LogRuleData>>> {
                mAdapter.setData(it)
            })
        mVM.buildLogRuleData(configData.queryConfigData)

        //初次查询
        query(configData.queryConfigData)
    }

    private fun query(configData: QueryConfigData) {
        mVM.queryLogs({ onSubscribe() }, configData, { onCompleted() })
    }

    override fun onChange(queryConfigData: QueryConfigData) {
        query(queryConfigData)
    }


}
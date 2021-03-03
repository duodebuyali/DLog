package com.duode.dlog.test

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.util.Log
import androidx.lifecycle.LiveData
import java.lang.ref.WeakReference


/**
 * @author hekang
 * @des
 * @date 2020/12/23 11:33
 */
class WiFiStateLiveData private constructor(context: Context) : LiveData<Int>() {
    private val mContextWeakReference: WeakReference<Context> = WeakReference(context)

    //第一次被观察时，注册广播
    override fun onActive() {
        super.onActive()
        registerReceiver()
    }

    //没有观察者时，注销广播
    override fun onInactive() {
        super.onInactive()
        unregisterReceiver()
    }

    private fun registerReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiManager.RSSI_CHANGED_ACTION)
        mContextWeakReference.get()?.registerReceiver(mReceiver, intentFilter)
    }

    private fun unregisterReceiver() {
        mContextWeakReference.get()?.unregisterReceiver(mReceiver)
    }

    private val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val action = intent.action
            Log.d(TAG, "action = $action")
            if (WifiManager.RSSI_CHANGED_ACTION == action) {
                val wifiRssi = intent.getIntExtra(WifiManager.EXTRA_NEW_RSSI, -200)
                val wifiLevel = WifiManager.calculateSignalLevel(
                    wifiRssi, 4)
                sData?.value = wifiLevel
            }
        }
    }

    companion object {
        private const val TAG = "MyLiveData"
        private var sData: WiFiStateLiveData? = null

        fun getInstance(context: Context): WiFiStateLiveData? {
            if (sData == null) {
                sData = WiFiStateLiveData(context)
            }
            return sData
        }
    }

}

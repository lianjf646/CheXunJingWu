package com.hylink.chexunjingwu.base

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    var activity = this;
    var mReceiver = MyBroadcastReceiver(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reister()
        initDataSS(savedInstanceState);
        initData();
        viewOnClick();
    }

    override fun onDestroy() {
        super.onDestroy()
        unregister()

    }

    fun reister() {
        var filter = IntentFilter()
        filter.addAction(cn.com.cybertech.pdk.Intent.ACTION_PSTORE_EXIT)
        if (mReceiver != null) {
            registerReceiver(mReceiver, filter)
        }

    };

    fun unregister() {
        if (mReceiver != null) {
            unregisterReceiver(mReceiver)
        }

    }


    class MyBroadcastReceiver(var act: Activity) : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            var action: String = intent?.action!!
            if (cn.com.cybertech.pdk.Intent.ACTION_PSTORE_EXIT.equals(action)) {
                act.finish()
            }
        }
    }

    open fun initDataSS(savedInstanceState: Bundle?) {

    }

    abstract fun initData();


    abstract fun viewOnClick();
}
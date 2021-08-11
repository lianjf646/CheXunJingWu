package com.hylink.chexunjingwu.ui.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import cn.com.cybertech.pdk.PushMessage
import com.blankj.utilcode.util.NotificationUtils
import com.dylanc.viewbinding.binding
import com.hylink.chexunjingwu.R
import com.hylink.chexunjingwu.base.BaseActivity
import com.hylink.chexunjingwu.databinding.ActivityMainBinding
import com.hylink.chexunjingwu.http.AndroidMqttClient
import com.hylink.chexunjingwu.http.response.HomeLoginResponse
import com.hylink.chexunjingwu.tools.DataHelper
import com.hylink.chexunjingwu.tools.ZheJiangLog
import com.hylink.chexunjingwu.ui.fragment.HomeFragment
import com.hylink.chexunjingwu.ui.fragment.MineFragment
import com.hylink.chexunjingwu.util.PushBroadcastReceiver
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity() {
    private val bind: ActivityMainBinding by binding();
    private val homeFragment = HomeFragment();
    private val mineFragment = MineFragment();

    @RequiresApi(Build.VERSION_CODES.M)
    val goSettings =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val b: Boolean = isIgnoringBatteryOptimizations()
            if (!b) {
                requestIgnoreBatteryOptimizations()
            }
        }

    var pushReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            var action = intent!!.action
            if (PushMessage.buildupBroadcastAction4PushMessage(packageName).equals(action)) {
                var message: PushMessage? = intent.getParcelableExtra(PushMessage.KEY_MESSAGE);

                if (message == null) return
                if (DataHelper.imei.isNullOrEmpty()) return
                PushBroadcastReceiver.messageArrived(message!!.messageType, message!!.message)
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun initData() {

        supportFragmentManager.beginTransaction()
            .add(R.id.framelayout, homeFragment)
            .add(R.id.framelayout, mineFragment)
            .hide(mineFragment)
            .show(homeFragment)
            .commitAllowingStateLoss()

        NotificationUtils.areNotificationsEnabled()
        val b: Boolean = isIgnoringBatteryOptimizations()
        if (!b) {
            requestIgnoreBatteryOptimizations()
        }
        ZheJiangLog.create()

        var filter = IntentFilter();
        filter.addAction(PushMessage.buildupBroadcastAction4PushMessage(getPackageName()))
        registerReceiver(pushReceiver, filter)

        val mExecutor = Executors.newSingleThreadScheduledExecutor()
        var mFuture: ScheduledFuture<*>? = null
        mFuture = mExecutor.scheduleWithFixedDelay({
            //定时任务
            if (!DataHelper.imei.isNullOrEmpty()) {
                var userBean =
                    DataHelper.getData(DataHelper.loginUserInfo) as HomeLoginResponse.Data.Data.User;
                AndroidMqttClient.connect(DataHelper.imei!!, userBean.pcard);
                mFuture?.cancel(true)

            }

        }, 1, 3, TimeUnit.SECONDS)
        mFuture.run {}
    }


    override fun onDestroy() {
        super.onDestroy()
        if (pushReceiver != null) {
            unregisterReceiver(pushReceiver)
        }
    }


    override fun viewOnClick() {
        bind.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.rbn_home) {
                supportFragmentManager
                    .beginTransaction()
                    .hide(mineFragment)
                    .show(homeFragment)
                    .commitAllowingStateLoss()
            } else if (checkedId == R.id.rbn_mine) {
                supportFragmentManager
                    .beginTransaction()
                    .hide(homeFragment)
                    .show(mineFragment)
                    .commitAllowingStateLoss()
            }
        };
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun isIgnoringBatteryOptimizations(): Boolean {
        var isIgnoring = false
        val powerManager = getSystemService(POWER_SERVICE) as PowerManager
        if (powerManager != null) {
            isIgnoring = powerManager.isIgnoringBatteryOptimizations(packageName)
        }
        return isIgnoring
    }

    fun requestIgnoreBatteryOptimizations() {
        var intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
        intent.data = Uri.parse("package:$packageName")
        goSettings.launch(intent)
    }
}
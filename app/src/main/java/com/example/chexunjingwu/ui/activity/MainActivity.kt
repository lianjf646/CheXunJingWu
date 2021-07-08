package com.example.chexunjingwu.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.blankj.utilcode.util.NotificationUtils
import com.dylanc.viewbinding.binding
import com.example.chexunjingwu.R
import com.example.chexunjingwu.base.BaseActivity
import com.example.chexunjingwu.databinding.ActivityMainBinding
import com.example.chexunjingwu.http.AndroidMqttClient
import com.example.chexunjingwu.ui.fragment.HomeFragment
import com.example.chexunjingwu.ui.fragment.MineFragment

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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    override fun initData() {
        var imei = "R624422009130189";
        AndroidMqttClient.connect(imei);
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
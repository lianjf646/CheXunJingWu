package com.hylink.chexunjingwu.ui.activity

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.blankj.utilcode.util.NotificationUtils
import com.dylanc.viewbinding.binding
import com.hylink.chexunjingwu.R
import com.hylink.chexunjingwu.base.BaseActivity
import com.hylink.chexunjingwu.databinding.ActivityMainBinding
import com.hylink.chexunjingwu.http.AndroidMqttClient
import com.hylink.chexunjingwu.http.response.HomeLoginResponse
import com.hylink.chexunjingwu.tools.DataHelper
import com.hylink.chexunjingwu.ui.fragment.HomeFragment
import com.hylink.chexunjingwu.ui.fragment.MineFragment
import com.permissionx.guolindev.PermissionX
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




    @RequiresApi(Build.VERSION_CODES.M)
    override fun initData() {

        supportFragmentManager.beginTransaction()
            .add(R.id.framelayout, homeFragment)
            .add(R.id.framelayout, mineFragment)
            .hide(mineFragment)
            .show(homeFragment)
            .commitAllowingStateLoss()

        NotificationUtils.areNotificationsEnabled()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val b: Boolean = isIgnoringBatteryOptimizations()
            if (!b) {
                requestIgnoreBatteryOptimizations()
            }
        }


        val mExecutor = Executors.newSingleThreadScheduledExecutor()
        var mFuture: ScheduledFuture<*>? = null
        mFuture = mExecutor.scheduleWithFixedDelay({
            //????????????
            if (!DataHelper.imei.isNullOrEmpty()) {
                var userBean =
                    DataHelper.getData(DataHelper.loginUserInfo) as HomeLoginResponse.Data.Data.User;
                AndroidMqttClient.connect(DataHelper.imei!!, userBean.pcard);
                mFuture?.cancel(true)

            }

        }, 1, 3, TimeUnit.SECONDS)
        mFuture.run {}

        PermissionX.init(this)
            .permissions(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_SMS,
                Manifest.permission.READ_PHONE_NUMBERS,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            )
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(
                    deniedList,
                    "?????????????????????????????????",
                    "OK",
                    "Cancel"
                )
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
//                    var intent = Intent(this, LoginActivity::class.java)
//                    startActivity(intent)
//                    finish()
                } else {
//                    showNormal("These permissions are denied: $deniedList")
//                    showRequestReasonDialog(filteredList, "?????????????????????????????????????????????", "????????????")

                }
            }
    }


    override fun onDestroy() {
        super.onDestroy()

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


    @RequiresApi(Build.VERSION_CODES.M)
    private fun isIgnoringBatteryOptimizations(): Boolean {
        var isIgnoring = false
        val powerManager = getSystemService(POWER_SERVICE) as PowerManager
        if (powerManager != null) {
            isIgnoring = powerManager.isIgnoringBatteryOptimizations(packageName)
        }
        return isIgnoring
    }

    private fun requestIgnoreBatteryOptimizations() {
        var intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
        intent.data = Uri.parse("package:$packageName")
        goSettings.launch(intent)
    }
}
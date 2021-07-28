package com.hylink.chexunjingwu.ui.activity

import android.Manifest
import android.content.Intent
import com.dylanc.viewbinding.binding
import com.hylink.chexunjingwu.base.BaseActivity
import com.hylink.chexunjingwu.databinding.ActivitySplashBinding
import com.hylink.chexunjingwu.tools.showNormal
import com.permissionx.guolindev.PermissionX


class SplashActivity : BaseActivity() {

    private val bind: ActivitySplashBinding by binding();
    override fun viewOnClick() {
        bind.root
    }

    override fun initData() {

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
                    "没有以下权限将无法使用",
                    "OK",
                    "Cancel"
                )
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {

                    var intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    showNormal("These permissions are denied: $deniedList")
//                    showRequestReasonDialog(filteredList, "摄像机权限是程序必须依赖的权限", "我已明白")

                }
            }
    }
}
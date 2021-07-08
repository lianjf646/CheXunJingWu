package com.example.chexunjingwu.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.View
import com.dylanc.viewbinding.binding
import com.example.chexunjingwu.base.BaseActivity
import com.example.chexunjingwu.databinding.ActivitySettingHouTaiBinding
import com.example.chexunjingwu.tools.OnClickViewListener

class SettingHouTaiActivity : BaseActivity() {
    private val bind: ActivitySettingHouTaiBinding by binding();

    override fun initData() {
        bind.includeTitle.ibnBack.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                finish()
            }

        })

        bind.includeTitle.tvTitle.text = "后台设置"


    }

    override fun viewOnClick() {
        bind.btnConfirm.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
//                Intent intent =  new Intent(Settings.ACTION_SETTINGS);
                startActivity(getAppDetailSettingIntent())

            }
        })
    }


    private fun getAppDetailSettingIntent(): Intent? {
        val localIntent = Intent()
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
            localIntent.data = Uri.fromParts("package", packageName, null)
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.action = Intent.ACTION_VIEW
            localIntent.setClassName(
                "com.android.settings",
                "com.android.settings.InstalledAppDetails"
            )
            localIntent.putExtra("com.android.settings.ApplicationPkgName", packageName)
        }
        return localIntent
    }
}
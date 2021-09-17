package com.hylink.chexunjingwu.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import cn.com.cybertech.pdk.UserInfo
import com.hylink.chexunjingwu.R
import com.hylink.chexunjingwu.tools.DataHelper
import com.hylink.chexunjingwu.tools.ZheJiangLog
import com.hylink.chexunjingwu.tools.showNormal


class SplashActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

    }

    override fun onResume() {
        super.onResume()
        var user = UserInfo.getUser(this)
        if (user == null) {
            DataHelper.idCard= "412822198402080733"
            DataHelper.deptId ="330109000000"
            startActivity(Intent(this@SplashActivity, MainActivity::class.java));
            finish()
            showNormal("通过sdk 获取用户信息失败")
            return
        }
        if (user?.idCard.isNullOrEmpty()) {
            showNormal("通过sdk 获取身份证号不存在")
            return
        }

        if (user?.deptId.isNullOrEmpty()) {
            showNormal("通过sdk 获取deptId不存在")
            return
        }
        ZheJiangLog.login()
        DataHelper.user = user;
        DataHelper.idCard = user.idCard;
        DataHelper.deptId = user.deptId;
        startActivity(Intent(this@SplashActivity, MainActivity::class.java));
        finish()
    }
}
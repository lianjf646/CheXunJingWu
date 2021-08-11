package com.hylink.chexunjingwu.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import cn.com.cybertech.pdk.auth.Oauth2AccessToken
import cn.com.cybertech.pdk.auth.PstoreAuth
import cn.com.cybertech.pdk.auth.PstoreAuthListener
import cn.com.cybertech.pdk.auth.sso.SsoHandler
import cn.com.cybertech.pdk.exception.PstoreException
import com.hylink.chexunjingwu.R


class SplashActivity : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
//        initData();
        startActivity(Intent(this@SplashActivity, LoginActivity::class.java));
        finish()
    }

    fun initData() {
        //key: 35D6F3BCDDC05176A39352D7F2747657	应用REG_ID：	330000100115
        var mAuth = PstoreAuth(this, "35D6F3BCDDC05176A39352D7F2747657");
        var mSsoHandler = SsoHandler(this, mAuth);

        mSsoHandler.authorize(object : PstoreAuthListener {
            override fun onComplete(p0: Oauth2AccessToken?) {
                Log.e(">>>>>", "onComplete: " + p0!!.token)
//                UserInfo.getUser(this@SplashActivity)
//                var link = LinkInfo.getLink(this@SplashActivity);
//                ApiService.BASE_URL = link.ip
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java));
                finish()
            }

            override fun onPstoreException(p0: PstoreException?) {
                Log.e(">>>>>", "onPstoreException: " + p0.toString() + p0!!.message)
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java));
                finish()
            }

            override fun onCancel() {
                Log.e(">>>>>", "onCancel: ")
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java));
                finish()
            }
        })

    }


}
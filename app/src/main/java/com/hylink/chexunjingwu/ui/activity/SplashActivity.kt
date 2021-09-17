package com.hylink.chexunjingwu.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.hylink.chexunjingwu.R


class SplashActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startActivity(Intent(this@SplashActivity, LoginActivity::class.java));
        finish()
    }
}
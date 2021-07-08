package com.example.chexunjingwu.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    var activity = this;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initData();

        viewOnClick();
    }

    abstract fun initData();

    abstract fun viewOnClick();


}
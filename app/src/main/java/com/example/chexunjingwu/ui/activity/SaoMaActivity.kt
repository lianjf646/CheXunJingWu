package com.example.chexunjingwu.ui.activity

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import com.dylanc.viewbinding.binding
import com.example.chexunjingwu.R
import com.example.chexunjingwu.base.BaseActivity
import com.example.chexunjingwu.databinding.ActivitySaomaBinding
import com.uuzuche.lib_zxing.activity.CaptureFragment
import com.uuzuche.lib_zxing.activity.CodeUtils
import com.uuzuche.lib_zxing.activity.CodeUtils.AnalyzeCallback

class SaoMaActivity : BaseActivity() {
    private val bind: ActivitySaomaBinding by binding();
    var captureFragment = CaptureFragment()

    override fun initData() {
        bind.root
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera)
        captureFragment.analyzeCallback = analyzeCallback
        supportFragmentManager.beginTransaction().replace(R.id.fl_my_container, captureFragment)
            .commitAllowingStateLoss()

    }

    override fun viewOnClick() {

    }

    /**
     * 二维码解析回调函数
     */
    var analyzeCallback: AnalyzeCallback = object : AnalyzeCallback {
        override fun onAnalyzeSuccess(mBitmap: Bitmap, result: String) {
            val resultIntent = Intent()
            val bundle = Bundle()
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS)
            bundle.putString(CodeUtils.RESULT_STRING, result)
            resultIntent.putExtras(bundle)
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        override fun onAnalyzeFailed() {
            finish()
        }
    }
}
package com.hylink.chexunjingwu.tools

import android.view.View
import java.util.*

abstract class OnClickViewListener : View.OnClickListener {
    var lastClickTime = 0;
    val MIN_CLICK_DELAY_TIME = 500
    override fun onClick(v: View?) {
        var currentTime = Calendar.getInstance().timeInMillis
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime.toInt()
            onClickSuc(v)
        }
    }

    abstract fun onClickSuc(v: View?)
}
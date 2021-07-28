package com.hylink.chexunjingwu.tools

import android.view.View
import android.widget.AdapterView
import java.util.*

abstract class OnItemClickViewListener : AdapterView.OnItemClickListener {
    private var lastClickTime: Long = 0
    override fun onItemClick(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
        val currentTime = Calendar.getInstance().timeInMillis
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime
            onItemClickView(parent, view, position, id)
        }
    }

    abstract fun onItemClickView(parent: AdapterView<*>?, view: View?, position: Int, id: Long)

    companion object {
        const val MIN_CLICK_DELAY_TIME = 1000
    }
}
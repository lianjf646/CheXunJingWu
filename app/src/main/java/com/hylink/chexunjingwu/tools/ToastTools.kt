package com.hylink.chexunjingwu.tools

import android.widget.Toast

import com.hylink.chexunjingwu.base.App

private const val TYPE_NORMAL = 0
private const val TYPE_ERROR = 1


private fun customToast(
    text: String,
    type: Int = TYPE_NORMAL,
    duration: Int = Toast.LENGTH_SHORT
) {
    val toast: Toast = if (type == TYPE_NORMAL) {
        Toast.makeText(App.app, text, duration)
    } else {
        Toast.makeText(App.app, text, duration)
    }
    toast.show();
}


fun showNormal(msg: String) {
    customToast(text = msg)
}

fun showNormalLong(msg: String) {
    customToast(msg, duration = Toast.LENGTH_LONG)
}

fun showError(msg: String) {
    customToast(msg, type = TYPE_ERROR)
}

fun showErrorLong(msg: String) {
    customToast(msg, type = TYPE_ERROR, duration = Toast.LENGTH_LONG)
}
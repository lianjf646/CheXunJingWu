package com.hylink.chexunjingwu.http.gsontypeadapter

import android.text.TextUtils
import java.util.regex.Pattern


fun isIntOrLong(str: String?): Boolean {
    return TextUtils.isDigitsOnly(str)
}

fun isFloatOrDouble(str: String?): Boolean {
    val pattern = Pattern.compile("^[-\\+]?[.\\d]*$")
    return pattern.matcher(str).matches()
}

package com.hylink.chexunjingwu.tools

import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.hylink.chexunjingwu.BuildConfig.BASE_URL
import com.hylink.chexunjingwu.base.App

fun glideLoad(url: String, imageView: ImageView) {
    if (TextUtils.isEmpty(url)) {
        return
    }
    if (url.startsWith("http")) {
        Glide.with(App.app).load(url).into(imageView)
    } else if (url.startsWith("/upload")) {
        Glide.with(App.app).load(BASE_URL + url).into(imageView)
    } else if (!url.startsWith("data:image/")) {
        Glide.with(App.app).load("data:image/jpg;base64,$url").into(imageView)
    }
}
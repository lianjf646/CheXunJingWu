package com.hylink.chexunjingwu.util

import android.content.Context
import android.os.Environment
import java.io.File

object OffLineMapUtils {

    /**
     * 获取map 缓存和读取目录
     */
    fun getSdCacheDir(context: Context?): String? {
        return if (Environment.getExternalStorageState() ==
            Environment.MEDIA_MOUNTED
        ) {
            val fExternalStorageDirectory = Environment
                .getExternalStorageDirectory()
            val dir = File(
                fExternalStorageDirectory, "lmapsdk"
            )
            var result = false
            if (!dir.exists()) {
                result = dir.mkdir()
            }
            val minimapDir = File(
                dir,
                "offlineMap"
            )
            if (!minimapDir.exists()) {
                result = minimapDir.mkdir()
            }
            "$minimapDir/"
        } else {
            ""
        }
    }
}
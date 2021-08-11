package com.hylink.chexunjingwu.tools

import cn.com.cybertech.pdk.OperationLog
import com.hylink.chexunjingwu.BuildConfig
import com.hylink.chexunjingwu.base.App
import java.text.SimpleDateFormat
import java.util.*

object ZheJiangLog {


    fun login() {
        saveLog(
            BuildConfig.REG_ID, BuildConfig.CLIENT_ID, "Module-LOGIN",
            OperationLog.OperationType.CODE_LOGIN, OperationLog.OperationResult.CODE_SUCCESS,
            OperationLog.LogType.CODE_API_CALLED, ""
        )
    }

    fun create() {
        saveLog(
            BuildConfig.REG_ID, BuildConfig.CLIENT_ID, "Module-CREATION",
            OperationLog.OperationType.CODE_LOGIN, OperationLog.OperationResult.CODE_SUCCESS,
            OperationLog.LogType.CODE_API_CALLED,
            "CREATION CONDITION (" + SimpleDateFormat.getDateTimeInstance().format(
                Date()
            ) + ")"
        )
    }





    private fun saveLog(
        regId: String,
        appKey: String,
        module: String,
        operateTypeCode: Int,
        operateResultCode: Int,
        logTypeCode: Int,
        operateCondition: String
    ) {
        try {
            OperationLog.logging(
                App.app,
                regId,
                appKey,
                module,
                operateTypeCode,
                operateResultCode,
                logTypeCode,
                operateCondition
            )
        } catch (e: Exception) {
            showNormal(e.message!!)
        }


    }

}
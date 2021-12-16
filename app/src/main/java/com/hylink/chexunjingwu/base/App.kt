package com.hylink.chexunjingwu.base

import android.app.Application
import com.baidu.mapapi.CoordType
import com.baidu.mapapi.SDKInitializer
import com.blankj.utilcode.util.CrashUtils
import com.blankj.utilcode.util.PathUtils
import com.hylink.chexunjingwu.BuildConfig

class App : Application() {
    companion object {
        lateinit var app: App
    }

    override fun onCreate() {
        super.onCreate()
        app = this

        if (BuildConfig.FLAVOR.equals("互联网")) {
            //在使用SDK各组件之前初始化context信息，传入ApplicationContext
            SDKInitializer.initialize(this)
            //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
            //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
            SDKInitializer.setCoordType(CoordType.BD09LL)
        }
        CrashUtils.init(PathUtils.getExternalStoragePath() + "/车巡警务协同错误日志")
//        AppException
//            .getInstance()
//            .registerExceptionHandler(this, BuildConfig.CLIENT_ID)
    }


}

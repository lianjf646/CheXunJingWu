package com.hylink.chexunjingwu.tools

import android.util.Log
import android.widget.Toast
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.hylink.chexunjingwu.base.App

object BaiduLocationUntil {
    var locationAdressInfoListener: LocationAdressInfoListener? = null

    private var mLocationClient: LocationClient? = null
    private var myListener = MyLocationListener()

    init {
        mLocationClient = LocationClient(App.app.applicationContext)
        //声明LocationClient类
        //声明LocationClient类

        //注册监听函数
        //注册监听函数
        val locationOption = LocationClientOption()
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        locationOption.locationMode = LocationClientOption.LocationMode.Hight_Accuracy;
        //可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        locationOption.setCoorType("bd09ll");
        //可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
        locationOption.setScanSpan(3000);
        //可选，设置是否需要地址信息，默认不需要
        locationOption.setIsNeedAddress(true);
        //可选，设置是否需要地址描述
        locationOption.setIsNeedLocationDescribe(true);
        //可选，设置是否需要设备方向结果
        locationOption.setNeedDeviceDirect(false);
        //可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        locationOption.isLocationNotify = false
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        locationOption.setIgnoreKillProcess(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        locationOption.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        locationOption.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        locationOption.SetIgnoreCacheException(false);
        //可选，默认false，设置是否开启Gps定位
        locationOption.isOpenGps = true;
        //可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        locationOption.setIsNeedAltitude(false);
//        //设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者，该模式下开发者无需再关心定位间隔是多少，定位SDK本身发现位置变化就会及时回调给开发者
//        locationOption.setOpenAutoNotifyMode();
//        //设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者
//        locationOption.setOpenAutoNotifyMode(3000, 1, LocationClientOption.LOC_SENSITIVITY_HIGHT);
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        mLocationClient!!.locOption = locationOption;
        mLocationClient?.registerLocationListener(myListener)
    }


    fun onStart() {
        if (mLocationClient == null) return
        if (!mLocationClient!!.isStarted) {
            mLocationClient?.start()

        }
    }

    fun onStop() {
        if (mLocationClient == null) return
        if (mLocationClient!!.isStarted) {
//            mLocationClient?.unRegisterLocationListener(myListener)
            mLocationClient?.stop()
        }

    }

    class MyLocationListener : BDAbstractLocationListener() {
        override fun onReceiveLocation(location: BDLocation) {
            if (locationAdressInfoListener == null) return
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            var addr: String = location.addrStr//获取详细地址信息

            var country: String = location.country //获取国家

            var province: String = location.province //获取省份

            var city: String = location.city //获取城市

            var district: String = location.district //获取区县

            var street: String = location.street //获取街道信息

            /**  locType
             *   61 	GPS定位结果，GPS定位成功
             *   62 	无法获取有效定位依据，定位失败，请检查运营商网络或者WiFi网络是否正常开启，尝试重新请求定位
             *   63 	网络异常，没有成功向服务器发起请求，请确认当前测试手机网络是否通畅，尝试重新请求定位
             *   66 	离线定位结果。通过requestOfflineLocaiton调用时对应的返回结果
             *   67 	离线定位失败
             *   161 	网络定位结果，网络定位成功
             *   162 	请求串密文解析失败，一般是由于客户端SO文件加载失败造成，请严格参照开发指南或demo开发，放入对应SO文件
             *   167 	服务端定位失败，请您检查是否禁用获取位置信息权限，尝试重新请求定位
             *   505 	AK不存在或者非法，请按照说明文档重新申请AK
             */
            /**  locType
             * 61 	GPS定位结果，GPS定位成功
             * 62 	无法获取有效定位依据，定位失败，请检查运营商网络或者WiFi网络是否正常开启，尝试重新请求定位
             * 63 	网络异常，没有成功向服务器发起请求，请确认当前测试手机网络是否通畅，尝试重新请求定位
             * 66 	离线定位结果。通过requestOfflineLocaiton调用时对应的返回结果
             * 67 	离线定位失败
             * 161 	网络定位结果，网络定位成功
             * 162 	请求串密文解析失败，一般是由于客户端SO文件加载失败造成，请严格参照开发指南或demo开发，放入对应SO文件
             * 167 	服务端定位失败，请您检查是否禁用获取位置信息权限，尝试重新请求定位
             * 505 	AK不存在或者非法，请按照说明文档重新申请AK
             */
            var locType: Int = location.locType
            var la: Double = location.getLatitude() //经度
            var lo: Double = location.getLongitude() //维度
//            var locationDescribe: String = location?.locationDescribe!! //获取位置描述信息

            Log.e(
                "BaiduLocationUntilInfo", """
     onReceiveLocation: $addr
     $country
     $province
     $city
     $district
     $street
     $la
     $lo
     ${location.address}
     ${location.locType}
     """.trimIndent()
            )
            if (locType == 161) { //网络定位结果
                locationAdressInfoListener!!.onGetAddressInfo(location)
                //                onStop();
                return
            }
            if (locType == 61) { //GPS定位结果
                locationAdressInfoListener!!.onGetAddressInfo(location)
                //                onStop();
                return
            }
            if (locType == 66) {
                locationAdressInfoListener!!.onGetAddressInfo(location)
                //                onStop();
                return
            }
            if (locType == 67) {
                Toast.makeText(App.app, "gps定位失败", Toast.LENGTH_SHORT).show()
            }

//            locationAdressInfoListener.onGetAddressInfo(location);
//            onStop();
        }

        override fun onLocDiagnosticMessage(p0: Int, p1: Int, p2: String?) {
            super.onLocDiagnosticMessage(p0, p1, p2)
            Log.e("----->", "onLocDiagnosticMessage: $p0")
        }

    }

    interface LocationAdressInfoListener {
        fun onGetAddressInfo(location: BDLocation?)
    }
}
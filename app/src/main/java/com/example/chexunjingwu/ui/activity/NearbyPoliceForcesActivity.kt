package com.example.chexunjingwu.ui.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.CompoundButton
import com.baidu.location.BDLocation
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.dylanc.viewbinding.binding
import com.example.chexunjingwu.R
import com.example.chexunjingwu.base.BaseViewModelActivity
import com.example.chexunjingwu.databinding.ActivityNearbyPoliceForcesBinding
import com.example.chexunjingwu.http.api.HttpResponseState
import com.example.chexunjingwu.http.response.GetPatrolListResponse
import com.example.chexunjingwu.http.response.HomeLoginResponse
import com.example.chexunjingwu.http.response.QueryPeripheryVehicleResponse
import com.example.chexunjingwu.tools.*
import com.example.chexunjingwu.viewmodel.NearbyPoliceForcesViewModel
import java.util.*


class NearbyPoliceForcesActivity : BaseViewModelActivity<NearbyPoliceForcesViewModel>() {

    private val bind: ActivityNearbyPoliceForcesBinding by binding();
    private lateinit var mBaiduMap: BaiduMap;
    private var government: String? = null
    private var idCard: String? = null
    var markerJingChe: ArrayList<Marker> = ArrayList()
    var markerDjj: ArrayList<Marker> = ArrayList()

    var userInfo: HomeLoginResponse.Data.Data.User =
        DataHelper.getData(DataHelper.loginUserInfo) as HomeLoginResponse.Data.Data.User;

    override fun observe() {
        mViewModel.queryPeripheryVehicleLD.observe(this, {
            if (it?.httpResponseState != HttpResponseState.STATE_SUCCESS) return@observe
            showJcMark(it?.httpResponse!!)
        })

        mViewModel.getPatrolListVehicleLD.observe(this, {
            if (it?.httpResponseState != HttpResponseState.STATE_SUCCESS) return@observe
            intercomResult(it?.httpResponse?.result?.intercomList!!)
        })
    }

    override fun initData() {
        bind.includeTitle.tvTitle.text = "周边警力"
        bind.includeTitle.ibnBack.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                finish()
            }

        })
        government = intent.getStringExtra("code")!!
        idCard = intent.getStringExtra("idCard")!!

        mBaiduMap = bind.mapView.map
        bind.mapView.removeViewAt(1)
        //        mBaiduMap.getUiSettings().setAllGesturesEnabled(false);   //关闭一切手势操作
//        mBaiduMap.getUiSettings().setOverlookingGesturesEnabled(true);//屏蔽双指下拉时变成3D地图
        //        mBaiduMap.getUiSettings().setAllGesturesEnabled(false);   //关闭一切手势操作
//        mBaiduMap.getUiSettings().setOverlookingGesturesEnabled(true);//屏蔽双指下拉时变成3D地图
        mBaiduMap.uiSettings.isRotateGesturesEnabled = false //屏蔽旋转
//        mBaiduMap.getUiSettings().setZoomGesturesEnabled(false);//获取是否允许缩放手势返回:是否允许缩放手势
        //        mBaiduMap.getUiSettings().setZoomGesturesEnabled(false);//获取是否允许缩放手势返回:是否允许缩放手势
        mBaiduMap.isMyLocationEnabled = true

        BaiduLocationUntil.locationAdressInfoListener =
            object : BaiduLocationUntil.LocationAdressInfoListener {
                override fun onGetAddressInfo(location: BDLocation?) {
                    val locData = MyLocationData.Builder()
                        .accuracy(location!!.radius) // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(location!!.direction)
                        .latitude(location!!.latitude)
                        .longitude(location!!.longitude).build()
                    mBaiduMap.setMyLocationData(locData)
                    setCenterPos(location.latitude, location.longitude);
                }
            }
        BaiduLocationUntil.onStart()
//        initLocationOption()
    }

    override fun onResume() {
        super.onResume()
        //在activity执行onResume时必须调用mMapView. onResume ()
        bind.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        //在activity执行onPause时必须调用mMapView. onPause ()
        bind.mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        //在activity执行onDestroy时必须调用mMapView.onDestroy()
        bind.mapView.onDestroy()
        mBaiduMap.isMyLocationEnabled = false
        BaiduLocationUntil.onStop()
    }

    override fun viewOnClick() {
        bind.chbJingche.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                clearMarkList(markerJingChe);
                if (!bind.chbJingche.isChecked) return
                mViewModel.queryPeripheryVehicle()
            }

        })

        bind.chbDuijiang.setOnCheckedChangeListener(object :
            CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                clearMarkList(markerDjj)
                if (!bind.chbDuijiang.isChecked) return
                mViewModel.getPatrolList(userInfo.idCard)
            }
        })
    }


    private fun setCenterPos(x: Double, y: Double) {
        val cenpt = LatLng(x, y) //设定中心点坐标``````````
        val mMapStatus = MapStatus.Builder() //定义地图状态
            .target(cenpt)
            .zoom(14f)
            .build() //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        val mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus)
        mBaiduMap.setMapStatus(mMapStatusUpdate) //改变地图状态
        //        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.zoomTo(15));
    }


    private fun showJcMark(response: QueryPeripheryVehicleResponse) {
        val icon_jc_t = BitmapDescriptorFactory.fromResource(R.drawable.icon_jc_t)
        val icon_jc_f = BitmapDescriptorFactory.fromResource(R.drawable.icon_jc_f)
        for (dto in response.list) {
            if (dto.gps_point.isNullOrEmpty()) continue
            val xy: DoubleArray =
                GPSUtil.gps84_To_bd09(dto.gps_point[1], dto.gps_point[0])
            val p1 = LatLng(xy[0], xy[1])
            var option: OverlayOptions?
            option = if (dto.is_online === 1) {
                MarkerOptions()
                    .position(p1)
                    .icon(icon_jc_t)
            } else {
                MarkerOptions()
                    .position(p1)
                    .icon(icon_jc_f)
            }
            val marker = mBaiduMap.addOverlay(option) as Marker
            markerJingChe.add(marker)
            val stringBuilder = StringBuilder()
            stringBuilder.append("车牌号:")
            stringBuilder.append(getStrings(dto.vehicle_license_plate))
            stringBuilder.append("\n")
            stringBuilder.append("组织机构:")
            stringBuilder.append(getStrings(dto.organization_name))
            stringBuilder.append("\n")
            stringBuilder.append("车台号:")
            stringBuilder.append(if (TextUtils.isEmpty(dto.cth)) "暂无车台" else dto.cth)
            stringBuilder.append("\n")
            stringBuilder.append("警员列表:")
            if (dto.userInfo.isNullOrEmpty()) {
                stringBuilder.append("暂无警员")
            } else {
                stringBuilder.append("\n")
                for (userinfoBean in dto.userInfo) {
                    if (userinfoBean == null) {
                        stringBuilder.append("暂无警员")
                        stringBuilder.append("\n")
                        continue
                    }
                    stringBuilder.append(userinfoBean.name)
                    stringBuilder.append("  电话:")
                    stringBuilder.append(userinfoBean.contact)
                    stringBuilder.append("\n")
                }
                val last = stringBuilder.lastIndexOf("\n")
                if (last >= 0) {
                    stringBuilder.delete(last, stringBuilder.length)
                }
            }
            val bundle = Bundle()
            bundle.putString("type", "jingche")
            bundle.putString("infowindow", stringBuilder.toString())
            marker.extraInfo = bundle
        }
    }

    private fun intercomResult(beanList: List<GetPatrolListResponse.Result.Intercom>) {
        if (beanList.isNullOrEmpty()) {
            return
        }
        val dj_f = BitmapDescriptorFactory.fromResource(R.drawable.icon_dj_f)
        val dj_t = BitmapDescriptorFactory.fromResource(R.drawable.icon_dj_t)
        for (intercomListBean in beanList) {
            if (intercomListBean.gps_point.isNullOrEmpty()) continue
            if (intercomListBean.status === 0) continue
            val xy = GPSUtil.gps84_To_bd09(
                intercomListBean.gps_point[1],
                intercomListBean.gps_point[0]
            )
            val p1 = LatLng(xy[0], xy[1])
            var option: OverlayOptions?
            option = if (intercomListBean.status === 0) {
                MarkerOptions()
                    .position(p1)
                    .icon(dj_f)
            } else {
                MarkerOptions()
                    .position(p1)
                    .icon(dj_t)
            }
            val stringBuilder = java.lang.StringBuilder()
            stringBuilder.append("设备号:")
            stringBuilder.append(if (TextUtils.isEmpty(intercomListBean.cth)) "暂无车台" else intercomListBean.cth)
            stringBuilder.append("\n")
            stringBuilder.append("名称:")
            stringBuilder.append(getStrings(intercomListBean.equipment_name))
            stringBuilder.append("\n")
            stringBuilder.append("组织机构:")
            stringBuilder.append(getStrings(intercomListBean.equipment_organization_name))
            val marker = mBaiduMap.addOverlay(option) as Marker
            markerDjj.add(marker)
            val bundle = Bundle()
            bundle.putString("type", "duijiangji")
            bundle.putString("infowindow", stringBuilder.toString())
            marker.extraInfo = bundle
        }
    }


    private fun clearMarkList(markerList: List<Marker>) {
        if (!markerList.isEmpty()) {
            for (marker in markerList) {
                if (!marker.isRemoved) {
                    marker.remove()
                }
            }
        }
    }

}
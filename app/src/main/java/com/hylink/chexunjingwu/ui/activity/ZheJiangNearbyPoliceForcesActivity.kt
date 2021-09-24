package com.hylink.chexunjingwu.ui.activity

import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import cn.com.cybertech.pdk.LocationInfo
import cn.com.cybertech.provider.PstoreContract
import com.dylanc.viewbinding.binding
import com.hylink.chexunjingwu.R
import com.hylink.chexunjingwu.base.App
import com.hylink.chexunjingwu.base.BaseViewModelActivity
import com.hylink.chexunjingwu.databinding.ActivityNearbyPoliceZhejiangBinding
import com.hylink.chexunjingwu.http.api.HttpResponseState
import com.hylink.chexunjingwu.http.response.GetPatrolListResponse
import com.hylink.chexunjingwu.http.response.HomeLoginResponse
import com.hylink.chexunjingwu.http.response.QueryPeripheryVehicleResponse
import com.hylink.chexunjingwu.tools.*
import com.hylink.chexunjingwu.util.LocationInfoFields
import com.hylink.chexunjingwu.util.OffLineMapUtils
import com.hylink.chexunjingwu.viewmodel.NearbyPoliceForcesViewModel
import com.mapabc.api.maps.*
import com.mapabc.api.maps.model.*
import java.util.*
import kotlin.collections.ArrayList


class ZheJiangNearbyPoliceForcesActivity : BaseViewModelActivity<NearbyPoliceForcesViewModel>(),
    MapController.OnMarkerClickListener, MapController.OnInfoWindowClickListener {
    private val bind: ActivityNearbyPoliceZhejiangBinding by binding();
    private var government: String? = null
    private var idCard: String? = null

    private var mapView: MapView? = null
    private var lMap: MapController? = null

    var markerJingChe: ArrayList<Marker> = ArrayList()
    var markerDjj: ArrayList<Marker> = ArrayList()

    var userInfo: HomeLoginResponse.Data.Data.User =
        DataHelper.getData(DataHelper.loginUserInfo) as HomeLoginResponse.Data.Data.User;
    var isFirst = true;
    var locMarker: MarkerOptions? = null;
    private var mContentObserver = object : ContentObserver(Handler(Looper.myLooper()!!)) {
        override fun onChange(selfChange: Boolean) {
            super.onChange(selfChange)
            if (!isFirst) {
                return
            }
            isFirst = false
            var locationInfo = LocationInfo.getLocationInfo(App.app)
            if (locationInfo.isNullOrEmpty()) return
            var latitude = locationInfo[LocationInfoFields.FIELD_LATITUDE]
            var longitude = locationInfo[LocationInfoFields.FIELD_LONGITUDE]
            if (latitude.isNullOrEmpty()) return
            if (longitude.isNullOrEmpty()) return
            var latLng = LatLng(longitude!!.toDouble(), latitude!!.toDouble())
            locMarker!!.position(latLng)
        }
    }

    override fun initDataSS(savedInstanceState: Bundle?) {
        super.initDataSS(savedInstanceState)
        /**
         * 设置离线地图存储目录，在下载离线地图或初始化地图设置;
         * 使用过程中可自行设置, 若自行设置了离线地图存储的路径，
         * 则需要在离线地图下载和使用地图页面都进行路径设置
         * */
        //Demo中为了其他界面可以使用下载的离线地图，使用默认位置存储，屏蔽了自定义设置
        MapsInitializer.sdcardDir = OffLineMapUtils.getSdCacheDir(this)
        mapView = bind.mapView
        mapView!!.onCreate(savedInstanceState) // 此方法必须重写
        if (lMap == null) {
            lMap = mapView!!.map
            lMap!!.setOnMarkerClickListener(this)
            lMap!!.setOnInfoWindowClickListener(this)
            var latLng: LatLng
            var locationInfo = LocationInfo.getLocationInfo(App.app)
            if (locationInfo.isNullOrEmpty()) {

                latLng = LatLng(30.285271, 120.217235)
            } else {
                var latitude = locationInfo[LocationInfoFields.FIELD_LATITUDE]
                var longitude = locationInfo[LocationInfoFields.FIELD_LONGITUDE]
                if (latitude.isNullOrEmpty() || longitude.isNullOrEmpty()) {
                    latLng = LatLng(30.285271, 120.217235)
                } else {
                    latLng = LatLng(longitude!!.toDouble(), latitude!!.toDouble())
                    Log.e(">>>>>>", "initDataSS3: ")
                }

            }

            locMarker = MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker))
                .position(latLng)
            lMap!!.addMarker(
                locMarker
            )

            changeCamera(
                CameraUpdateFactory.newCameraPosition(
                    CameraPosition(
                        latLng!!, 16.0f, 0f, 0f
                    )
                )
            )
        }

    }


    /**
     * 根据动画按钮状态，调用函数animateCamera或moveCamera来改变可视区域
     */
    private fun changeCamera(update: CameraUpdate) {

        lMap!!.moveCamera(update)

    }

    /**
     * 方法必须重写
     */
    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        mapView!!.onSaveInstanceState(outState)
    }

    /**
     * 方法必须重写
     */
    override fun onResume() {
        super.onResume()
        mapView!!.onResume()
    }

    /**
     * 方法必须重写
     */
    override fun onPause() {
        super.onPause()
        mapView!!.onPause()
    }

    /**
     * 方法必须重写
     */
    override fun onDestroy() {
        super.onDestroy()
        mapView!!.onDestroy()
        try {
            contentResolver.unregisterContentObserver(mContentObserver)
        } catch (e: Exception) {
            Log.e(">>>>>", "initData: ${e.message}")
        }

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

        try {
            contentResolver.registerContentObserver(
                PstoreContract.LocationInfo.CONTENT_URI,
                false,
                mContentObserver
            )
        } catch (e: Exception) {
            Log.e(">>>>>", "initData: ${e.message}")
        }

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

    private fun showJcMark(response: QueryPeripheryVehicleResponse) {

        for (dto in response.list) {
            if (dto.gps_point.isNullOrEmpty()) continue
            val xy: DoubleArray =
                GPSUtil.gps84_To_Gcj02(dto.gps_point[1], dto.gps_point[0])
            val p1 = LatLng(xy[0], xy[1])
            var marker =
                if (dto.is_online === 1) {
                    lMap!!.addMarker(
                        MarkerOptions()
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_jc_t))
                            .position(p1)
                    )
                } else {
                    lMap!!.addMarker(
                        MarkerOptions()
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_jc_f))
                            .position(p1)
                    )
                }
            val stringBuilder = StringBuilder()
//            stringBuilder.append("车牌号:")
//            stringBuilder.append(getStrings(dto.vehicle_license_plate))
//            stringBuilder.append("\n")
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
            marker.title = "车牌号:${getStrings(dto.vehicle_license_plate)}"
            marker.snippet = stringBuilder.toString()
            markerJingChe.add(marker)
        }
    }

    private fun intercomResult(beanList: List<GetPatrolListResponse.Result.Intercom>) {
        if (beanList.isNullOrEmpty()) return
        val dj_f = BitmapDescriptorFactory.fromResource(R.drawable.icon_dj_f)
        val dj_t = BitmapDescriptorFactory.fromResource(R.drawable.icon_dj_t)
        for (intercomListBean in beanList) {
            if (intercomListBean.gps_point.isNullOrEmpty()) continue
            if (intercomListBean.status === 0) continue
            val xy = GPSUtil.gps84_To_Gcj02(
                intercomListBean.gps_point[1],
                intercomListBean.gps_point[0]
            )
            val p1 = LatLng(xy[0], xy[1])
            var option = if (intercomListBean.status === 0) {
                MarkerOptions()
                    .position(p1)
                    .icon(dj_f)
            } else {
                MarkerOptions()
                    .position(p1)
                    .icon(dj_t)
            }
            var marker = lMap!!.addMarker(option)

            val stringBuilder = java.lang.StringBuilder()
//            stringBuilder.append("设备号:")
//            stringBuilder.append(if (TextUtils.isEmpty(intercomListBean.cth)) "暂无车台" else intercomListBean.cth)
//            stringBuilder.append("\n")
            stringBuilder.append("名称:")
            stringBuilder.append(getStrings(intercomListBean.equipment_name))
            stringBuilder.append("\n")
            stringBuilder.append("组织机构:")
            stringBuilder.append(getStrings(intercomListBean.equipment_organization_name))
            marker.title =
                "设备号:${if (TextUtils.isEmpty(intercomListBean.cth)) "暂无车台" else intercomListBean.cth}"
            marker.snippet = stringBuilder.toString()
            markerDjj.add(marker)
        }
    }

    private fun clearMarkList(markerList: ArrayList<Marker>) {
        if (markerList.isNotEmpty()) {
            for (marker in markerList) {
//                if (!marker.isRemoved) {
//                    marker.remove()
//                }
                marker.remove()
            }
            markerList.clear()
        }
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        if (p0!!.title.isNullOrEmpty()) return false
        p0!!.showInfoWindow()
        return false
    }

    override fun onInfoWindowClick(p0: Marker?) {
        p0!!.hideInfoWindow()
    }
}




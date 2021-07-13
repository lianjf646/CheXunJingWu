package com.example.chexunjingwu.ui.activity

import android.view.View
import com.baidu.mapapi.map.BaiduMap
import com.dylanc.viewbinding.binding
import com.example.chexunjingwu.base.BaseViewModelActivity
import com.example.chexunjingwu.databinding.ActivityNearbyPoliceForcesBinding
import com.example.chexunjingwu.tools.OnClickViewListener
import com.example.chexunjingwu.viewmodel.NearbyPoliceForcesViewModel

class NearbyPoliceForcesActivity : BaseViewModelActivity<NearbyPoliceForcesViewModel>() {

    private val bind: ActivityNearbyPoliceForcesBinding by binding();
    private lateinit var mBaiduMap: BaiduMap;

    override fun observe() {

    }

    override fun initData() {
        bind.includeTitle.tvTitle.text = "周边警力"
        bind.includeTitle.ibnBack.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                finish()
            }

        })
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
    }

    override fun viewOnClick() {
    }

}
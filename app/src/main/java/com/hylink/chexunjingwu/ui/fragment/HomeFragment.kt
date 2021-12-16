package com.hylink.chexunjingwu.ui.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.dylanc.viewbinding.binding
import com.google.gson.Gson
import com.hylink.chexunjingwu.R
import com.hylink.chexunjingwu.base.BaseVMFragment
import com.hylink.chexunjingwu.bean.QRcodeInfo
import com.hylink.chexunjingwu.databinding.FragmentHomeBinding
import com.hylink.chexunjingwu.http.api.HttpResponseState
import com.hylink.chexunjingwu.http.response.HomeLoginResponse
import com.hylink.chexunjingwu.tools.DataHelper
import com.hylink.chexunjingwu.tools.OnClickViewListener
import com.hylink.chexunjingwu.ui.activity.*
import com.hylink.chexunjingwu.viewmodel.HomeFragmentViewModel
import com.uuzuche.lib_zxing.activity.CodeUtils

class HomeFragment : BaseVMFragment<HomeFragmentViewModel>(R.layout.fragment_home) {

    var imei: String? = null
    val bind: FragmentHomeBinding by binding()
    var userInfo: HomeLoginResponse.Data.Data.User =
        DataHelper.getData(DataHelper.loginUserInfo) as HomeLoginResponse.Data.Data.User;

    var government: String? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.linearQrcode.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                var intent = Intent(activity, SaoMaActivity::class.java)
                getSaoMaData.launch(intent);
            }
        })

        bind.btnMineEarlyWarning.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                if (TextUtils.isEmpty(imei)) return
                val intent = Intent(context, MineEarlyWarningActivity::class.java)
                intent.putExtra("imei", imei)
                startActivity(intent)
            }
        })

        bind.btnJiechujing.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                if (TextUtils.isEmpty(imei)) return
                val intent = Intent(context, JieChuJingActivity::class.java)
                intent.putExtra("imei", imei)
                startActivity(intent)
            }

        })

        bind.btnCheck.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                if (TextUtils.isEmpty(imei)) return
                val intent = Intent(context, CheckActivity::class.java)
                startActivity(intent)
            }

        })

        bind.btnZhiling.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                if (TextUtils.isEmpty(imei)) return
                val intent = Intent(context, InstructionsActivity::class.java)
                startActivity(intent)
            }
        })

        bind.btnSetting.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                val intent = Intent(context, SettingHouTaiActivity::class.java)
                startActivity(intent)
            }

        })

        bind.btnNearbyPoliceForces.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                government = userInfo.group.code
                if (government!!.isEmpty()) return
                val intent = Intent()
                intent.setClass(activity, NearbyPoliceForcesActivity::class.java)
                intent.putExtra("government", government)
                startActivity(intent)
            }
        })

        var idCard = userInfo?.idCard;
        mViewModel.signStatus(idCard)
    }

    private val getSaoMaData =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val result = it?.data?.getStringExtra(CodeUtils.RESULT_STRING) ?: "二维码失效"
                var uuid = Gson().fromJson(result, QRcodeInfo::class.java).uuid
                var idCard = userInfo?.idCard;
                mViewModel.ifTimeOut(uuid, idCard)
            }
        }

    override fun observe() {
        mViewModel.signStatusLiveData.observe(this, {
            if (it.httpResponseState == HttpResponseState.STATE_SUCCESS) {
                var signStatusResponse = it?.httpResponse?.data;
                if (signStatusResponse == null) {
                    imei = "";
                    DataHelper.imei = "";
                    bind.tvCaron.text = "车牌:"
                    return@observe
                }

                if (signStatusResponse?.sign_identification.equals("qd")) {
                    bind.tvState.text = "巡逻车已连接"
                    bind.tvCaron.text = "车牌:${signStatusResponse?.carNo}"
                    imei = signStatusResponse?.imei;
                    DataHelper.putData(DataHelper.carInfo, signStatusResponse)
                    DataHelper.imei = imei;
                } else {
                    bind.tvCaron.text = "车牌:"
                    bind.tvState.text = "巡逻车未连接"
                    DataHelper.putData(DataHelper.carInfo, signStatusResponse)
                    imei = "";
                    DataHelper.imei = "";
                }

                if (TextUtils.isEmpty(bind.tvState.text)) {
                    bind.tvState.visibility = View.GONE
                } else {
                    bind.tvState.visibility = View.VISIBLE
                }
            }
        });
    }
}
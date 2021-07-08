package com.example.chexunjingwu.ui.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.dylanc.viewbinding.binding
import com.example.chexunjingwu.R
import com.example.chexunjingwu.base.BaseVMFragment
import com.example.chexunjingwu.bean.QRcodeInfo
import com.example.chexunjingwu.databinding.FragmentHomeBinding
import com.example.chexunjingwu.http.api.HttpResponseState
import com.example.chexunjingwu.http.response.HomeLoginResponse
import com.example.chexunjingwu.tools.DataHelper
import com.example.chexunjingwu.tools.OnClickViewListener
import com.example.chexunjingwu.ui.activity.*
import com.example.chexunjingwu.viewmodel.HomeFragmentViewModel
import com.google.gson.Gson
import com.uuzuche.lib_zxing.activity.CodeUtils

class HomeFragment : BaseVMFragment<HomeFragmentViewModel>(R.layout.fragment_home) {

    var imei: String? = null
    val bind: FragmentHomeBinding by binding()
    var userInfo: HomeLoginResponse.Data.Data.User =
        DataHelper.getData(DataHelper.loginUserInfo) as HomeLoginResponse.Data.Data.User;

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
                //                String topic = "Ret/print/Send/" + "R624422009130189";
//                String ddd = "ddddddddddddd";
//                SmartMqtt.getInstance().sendData(ddd.getBytes(), topic);
            }
        })

        bind.btnSetting.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                val intent = Intent(context, SettingHouTaiActivity::class.java)
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
                if (signStatusResponse == null) return@observe
                bind.tvCaron.text = signStatusResponse?.carNo
                if (signStatusResponse?.sign_identification.equals("qd")) {
                    bind.tvState.text = "巡逻车已连接"
                    imei = signStatusResponse?.imei;
                    DataHelper.putData(DataHelper.carInfo, signStatusResponse)
                    DataHelper.imei = imei;
                } else {
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
package com.hylink.chexunjingwu.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import cn.com.cybertech.pdk.auth.Oauth2AccessToken
import cn.com.cybertech.pdk.auth.PstoreAuth
import cn.com.cybertech.pdk.auth.PstoreAuthListener
import cn.com.cybertech.pdk.auth.sso.SsoHandler
import cn.com.cybertech.pdk.exception.PstoreException
import com.dylanc.viewbinding.binding
import com.fri.libfriapkrecord.read.SignRecordTools
import com.hylink.chexunjingwu.BuildConfig
import com.hylink.chexunjingwu.R
import com.hylink.chexunjingwu.base.BaseFragment
import com.hylink.chexunjingwu.databinding.FragmentMineBinding
import com.hylink.chexunjingwu.http.response.HomeLoginResponse
import com.hylink.chexunjingwu.tools.DataHelper
import com.hylink.chexunjingwu.tools.OnClickViewListener
import com.hylink.chexunjingwu.tools.showNormal
import kotlin.system.exitProcess

class MineFragment : BaseFragment(R.layout.fragment_mine) {
    val bind: FragmentMineBinding by binding()
    var userInfo: HomeLoginResponse.Data.Data.User =
        DataHelper.getData(DataHelper.loginUserInfo) as HomeLoginResponse.Data.Data.User;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.tvName.text = "姓名:${userInfo.name}"
        bind.tvJh.text = "警号:${userInfo.pcard}"
        bind.tvVersionName.text = "版本:${BuildConfig.VERSION_NAME}"

        bind.btnOutLogin.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                getActivity()?.finish()
                exitProcess(0)
            }
        })
        getCode()
        getToken()
    }

    private fun getToken() {

//        var mAuth = PstoreAuth(activity, BuildConfig.REG_ID);
//        var mSsoHandler = SsoHandler(activity, mAuth);
//
//        mSsoHandler.authorize(object : PstoreAuthListener {
//            override fun onComplete(p0: Oauth2AccessToken?) {
//                bind.tvToken.text = "token:${p0?.token}"
//
//            }
//
//            override fun onPstoreException(p0: PstoreException?) {
//                showNormal(p0?.message!!)
//
//            }
//
//            override fun onCancel() {
//
//            }
//        })
    }

    private fun getCode() {
//        var apkPath: String? = null
//        try {
//            apkPath = activity.applicationInfo.sourceDir
//            //读取备案号
//            val recordNum = SignRecordTools.readNumbers(apkPath)
//            bind.tvRecordNum.text = "备案号:${recordNum}"
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
    }
}
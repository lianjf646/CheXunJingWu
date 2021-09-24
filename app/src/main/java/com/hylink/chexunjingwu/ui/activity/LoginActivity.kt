package com.hylink.chexunjingwu.ui.activity

import android.Manifest
import android.content.Intent
import android.view.View
import androidx.datastore.preferences.core.*
import androidx.lifecycle.Observer
import com.dylanc.viewbinding.binding
import com.google.gson.Gson
import com.hylink.chexunjingwu.BuildConfig
import com.hylink.chexunjingwu.base.BaseViewModelActivity
import com.hylink.chexunjingwu.databinding.ActivityLoginBinding
import com.hylink.chexunjingwu.http.api.HttpResponseState
import com.hylink.chexunjingwu.http.response.HomeLoginResponse
import com.hylink.chexunjingwu.tools.DataHelper
import com.hylink.chexunjingwu.tools.ZheJiangLog
import com.hylink.chexunjingwu.tools.showNormal
import com.hylink.chexunjingwu.viewmodel.LoginViewModel
import com.permissionx.guolindev.PermissionX
import com.xinghuo.mpaas.librarysso.LoginUserInfo
import com.xinghuo.mpaas.librarysso.SSOContext
import com.xinghuo.mpaas.librarysso.VerifyResponseCallback


class LoginActivity : BaseViewModelActivity<LoginViewModel>() {
    private val bind: ActivityLoginBinding by binding();

    override fun viewOnClick() {
        bind.btn.setOnClickListener(View.OnClickListener {
            val num1 = bind.etNum.text.toString()
            val password1 = bind.etPassword.text.toString()

            if (num1.isEmpty()) {
                showNormal("请输入账号")
                return@OnClickListener;
            }
            if (password1.isEmpty()) {
                showNormal("请输入密码")
                return@OnClickListener
            }
            mViewModel.login(num1, password1);
        })
    }


    override fun initData() {

        PermissionX.init(this)
            .permissions(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_SMS,
                Manifest.permission.READ_PHONE_NUMBERS,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            )
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(
                    deniedList,
                    "没有以下权限将无法使用",
                    "OK",
                    "Cancel"
                )
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {

                } else {
//                    showNormal("These permissions are denied: $deniedList")
//                    showRequestReasonDialog(filteredList, "摄像机权限是程序必须依赖的权限", "我已明白")

                }
            }
        if (BuildConfig.FLAVOR == "互联网") {
            mViewModel.login("0", "0");
            return
        }
        var token = intent.getStringExtra("token")!!
        if (token.isNullOrEmpty()) {
            showNormal("token未获取到")
            return
        }

        SSOContext.verifyToken(token, object : VerifyResponseCallback {
            override fun responseSuccess(p0: String?) {
                runOnUiThread {
                    val userInfo = Gson().fromJson(p0, LoginUserInfo::class.java)
                    if (userInfo != null && userInfo.code == 200) {
                        bind.tvIdCard.text = "idCard:$userInfo.data.idcard"
                        mViewModel.login(userInfo.data.idcard)
                    } else {
                        bind.tvIdCard.text = "idCard:$userInfo.data.idcard"
                        if (p0 != null) {
                            showNormal(p0)
                        };
                    }
                }
            }

            override fun responseFail(p0: String?) {
                runOnUiThread {
                    if (p0 != null) {
                        showNormal(p0)
                    }
                }
            }
        })
    }

    override fun observe() {
        mViewModel.loginLiveData.observe(this, Observer {
            if (it.httpResponseState == HttpResponseState.STATE_SUCCESS) {
                goMainAct(it?.httpResponse!!)
            }
        })
    }

    private fun goMainAct(httpResponse: HomeLoginResponse) {
        if (httpResponse == null) {
            showNormal("未获取到用户信息4")
            return
        }

        if (httpResponse?.data == null) {
            showNormal("未获取到用户信息3")
            return
        }

        if (httpResponse?.data?.data == null) {
            showNormal("未获取到用户信息2")
            return
        }

        if (httpResponse?.data?.data?.user == null) {
            showNormal("未获取到用户信息1")
            return
        }

        DataHelper.putData(
            DataHelper.loginUserInfo,
            httpResponse?.data?.data?.user!!
        )
        DataHelper.putData(
            DataHelper.loginUserGroup,
            httpResponse?.data?.data?.user?.group!!
        )
        DataHelper.putData(
            DataHelper.loginUserJob,
            httpResponse?.data?.data?.user?.job!!
        )

        ZheJiangLog.login()
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


}
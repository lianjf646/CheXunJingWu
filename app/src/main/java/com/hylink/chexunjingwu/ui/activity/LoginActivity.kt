package com.hylink.chexunjingwu.ui.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.Observer
import cn.com.cybertech.pdk.UserInfo
import com.dylanc.viewbinding.binding
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


class LoginActivity : BaseViewModelActivity<LoginViewModel>() {
    private val bind: ActivityLoginBinding by binding();
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "login_detail")
    val NUM = stringPreferencesKey("num")
    val PASSWORD = stringPreferencesKey("password")
    val IS_REMEMBER = booleanPreferencesKey("isRemember")

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
//        GlobalScope.launch(Dispatchers.Main) {
//            dataStore.data
//                .collect {
//                    bind.etNum.setText(it[NUM])
//                    bind.etPassword.setText(it[PASSWORD])
//                    bind.chb.isChecked = it[IS_REMEMBER] == true
//                }
//        }

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
//                    var intent = Intent(this, LoginActivity::class.java)
//                    startActivity(intent)
//                    finish()
                } else {
//                    showNormal("These permissions are denied: $deniedList")
//                    showRequestReasonDialog(filteredList, "摄像机权限是程序必须依赖的权限", "我已明白")

                }
            }
        if (BuildConfig.FLAVOR == "互联网") {

            mViewModel.login("0","0");
            return
        }


        if (BuildConfig.FLAVOR == "浙江测试") {
            var idCard = "33010319810702131X";
            mViewModel.login(idCard)
            return
        }


        var user = UserInfo.getUser(this)
        if (user == null) {
            showNormal("通过sdk 获取用户信息失败")
            return
        }
        if (user?.idCard.isNullOrEmpty()) {
            showNormal("通过sdk 获取身份证号不存在")
            return
        }
        var idCard = user.idCard
        mViewModel.login(idCard)
        bind.tvIdCard.text = "idCard:$idCard"

    }

    override fun observe() {
        mViewModel.loginLiveData.observe(this, Observer {
            if (it.httpResponseState == HttpResponseState.STATE_SUCCESS) {
                goMainAct(it?.httpResponse!!)
            }
        })
    }

    private fun goMainAct(httpResponse: HomeLoginResponse) {
//                GlobalScope.launch {
//                   if (bind.chb.isChecked) {
//                       dataStore.edit { value ->
//                           value[NUM] = bind.etNum.text.toString()
//                           value[PASSWORD] = bind.etPassword.text.toString()
//                           value[IS_REMEMBER] = bind.chb.isChecked
//                       }
//                   } else {
//                       dataStore.edit {
//                           it.clear()
//                       }
//                   }
//               }

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
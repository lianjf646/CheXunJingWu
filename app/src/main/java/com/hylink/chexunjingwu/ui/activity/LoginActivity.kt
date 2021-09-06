package com.hylink.chexunjingwu.ui.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.Observer
import cn.com.cybertech.pdk.UserInfo
import cn.com.cybertech.pdk.auth.Oauth2AccessToken
import cn.com.cybertech.pdk.auth.PstoreAuth
import cn.com.cybertech.pdk.auth.PstoreAuthListener
import cn.com.cybertech.pdk.auth.sso.SsoHandler
import cn.com.cybertech.pdk.exception.PstoreException
import com.dylanc.viewbinding.binding
import com.fri.libfriapkrecord.read.SignRecordTools
import com.hylink.chexunjingwu.BuildConfig
import com.hylink.chexunjingwu.base.BaseViewModelActivity
import com.hylink.chexunjingwu.databinding.ActivityLoginBinding
import com.hylink.chexunjingwu.http.api.HttpResponseState
import com.hylink.chexunjingwu.http.response.HomeLoginResponse
import com.hylink.chexunjingwu.tools.DataHelper
import com.hylink.chexunjingwu.tools.ZheJiangLog
import com.hylink.chexunjingwu.tools.md5
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
            mViewModel.login(num1, md5(password1));
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
                    showNormal("These permissions are denied: $deniedList")
//                    showRequestReasonDialog(filteredList, "摄像机权限是程序必须依赖的权限", "我已明白")

                }
            }
        getToken();
        getCode();

        if (BuildConfig.FLAVOR == "互联网") {
            return
        }
//        var idCard = "339005199210247317";
//        mViewModel.login(idCard)
        var user = UserInfo.getUser(this)
        if (user == null) {
            showNormal("通过sdk 获取用户信息失败")
            return
        }
        if (user?.idCard.isNullOrEmpty()) {
            showNormal("通过sdk 获取身份证号不存在")
            return
        }
        var idCard = user.idCard;
        mViewModel.login(idCard)

    }

    override fun observe() {
        mViewModel.loginLiveData.observe(this, Observer {
            if (it.httpResponseState == HttpResponseState.STATE_SUCCESS) {
                goMainAct(it?.httpResponse!!)
            }
            finish()
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
    }

    private fun getToken() {

        //330000100115
        var mAuth = PstoreAuth(this, BuildConfig.REG_ID);
        var mSsoHandler = SsoHandler(this, mAuth);

        mSsoHandler.authorize(object : PstoreAuthListener {
            override fun onComplete(p0: Oauth2AccessToken?) {
//                Log.e(">>>>>", "onComplete: " + p0!!.token)
                bind.tvToken.text == p0!!.token
            }

            override fun onPstoreException(p0: PstoreException?) {
                Log.e(">>>>>", "onPstoreException: " + p0.toString() + p0!!.message)
//                startActivity(Intent(this@SplashActivity, LoginActivity::class.java));
//                finish()
                showNormal(p0!!.message!!)
            }

            override fun onCancel() {
//                Log.e(">>>>>", "onCancel: ")
//                startActivity(Intent(this@SplashActivity, LoginActivity::class.java));
//                finish()
            }
        })
    }

    private fun getCode() {
        var apkPath: String? = null
        try {
            apkPath = applicationInfo.sourceDir
            //读取备案号
            val recordNum = SignRecordTools.readNumbers(apkPath)
            bind.tvCode.text = "全国注册备案号：$recordNum"
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}
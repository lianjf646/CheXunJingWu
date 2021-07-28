package com.hylink.chexunjingwu.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.Observer
import com.dylanc.viewbinding.binding
import com.hylink.chexunjingwu.base.BaseViewModelActivity
import com.hylink.chexunjingwu.databinding.ActivityLoginBinding
import com.hylink.chexunjingwu.http.api.HttpResponseState
import com.hylink.chexunjingwu.tools.DataHelper
import com.hylink.chexunjingwu.tools.md5
import com.hylink.chexunjingwu.tools.showNormal
import com.hylink.chexunjingwu.viewmodel.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


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
        GlobalScope.launch(Dispatchers.Main) {
            dataStore.data
                .collect {
                    bind.etNum.setText(it[NUM])
                    bind.etPassword.setText(it[PASSWORD])
                    bind.chb.isChecked = it[IS_REMEMBER] == true
                }
        }


    }

    override fun observe() {
        mViewModel.loginLiveData.observe(this, Observer {
            if (it.httpResponseState == HttpResponseState.STATE_SUCCESS) {
                GlobalScope.launch {
                    if (bind.chb.isChecked) {
                        dataStore.edit { value ->
                            value[NUM] = bind.etNum.text.toString()
                            value[PASSWORD] = bind.etPassword.text.toString()
                            value[IS_REMEMBER] = bind.chb.isChecked
                        }
                    } else {
                        dataStore.edit {
                            it.clear()
                        }
                    }
                }
//                DataHelper.putData(DataHelper.loginInfo, it?.httpResponse!!)
                DataHelper.putData(
                    DataHelper.loginUserInfo,
                    it?.httpResponse?.data?.data?.user!!
                )
                DataHelper.putData(
                    DataHelper.loginUserGroup,
                    it?.httpResponse?.data?.data?.user?.group!!
                )
                DataHelper.putData(
                    DataHelper.loginUserJob,
                    it?.httpResponse?.data?.data?.user?.job!!
                )
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }
}
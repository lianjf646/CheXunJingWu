package com.hylink.chexunjingwu.ui.fragment

import android.os.Bundle
import android.view.View
import com.dylanc.viewbinding.binding
import com.hylink.chexunjingwu.BuildConfig
import com.hylink.chexunjingwu.R
import com.hylink.chexunjingwu.base.BaseFragment
import com.hylink.chexunjingwu.databinding.FragmentMineBinding
import com.hylink.chexunjingwu.http.response.HomeLoginResponse
import com.hylink.chexunjingwu.tools.DataHelper

class MineFragment : BaseFragment(R.layout.fragment_mine) {
    val bind: FragmentMineBinding by binding()
    var userInfo: HomeLoginResponse.Data.Data.User =
        DataHelper.getData(DataHelper.loginUserInfo) as HomeLoginResponse.Data.Data.User;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.tvName.text = "姓名:${userInfo.name}"
        bind.tvJh.text = "警号:${userInfo.pcard}"
        bind.tvVersionName.text = "版本:${BuildConfig.VERSION_NAME}"
    }

}
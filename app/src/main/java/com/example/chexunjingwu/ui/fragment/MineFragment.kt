package com.example.chexunjingwu.ui.fragment

import android.os.Bundle
import android.view.View
import com.dylanc.viewbinding.binding
import com.example.chexunjingwu.BuildConfig
import com.example.chexunjingwu.R
import com.example.chexunjingwu.base.BaseFragment
import com.example.chexunjingwu.databinding.FragmentMineBinding
import com.example.chexunjingwu.http.response.HomeLoginResponse
import com.example.chexunjingwu.tools.DataHelper

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
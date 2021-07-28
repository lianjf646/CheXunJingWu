package com.hylink.chexunjingwu.ui.activity

import android.view.View
import com.dylanc.viewbinding.binding
import com.hylink.chexunjingwu.R
import com.hylink.chexunjingwu.base.BaseActivity
import com.hylink.chexunjingwu.databinding.ActivityCheckDetailBinding
import com.hylink.chexunjingwu.http.response.BasicInformationBean
import com.hylink.chexunjingwu.tools.DataHelper
import com.hylink.chexunjingwu.tools.OnClickViewListener
import com.hylink.chexunjingwu.ui.adapter.CheckAdapter

class CheckDetailActivity : BaseActivity() {

    val bind: ActivityCheckDetailBinding by binding()
    var tCheckAdapter = CheckAdapter()
    var fCheckAdapter = CheckAdapter()


    override fun initData() {

        bind.includeTitle.tvTitle.text = "核查详情"
        bind.includeTitle.ibnBack.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                finish()
            }
        })

        var basicInformationBeanList: List<BasicInformationBean> =
            DataHelper.getData(DataHelper.basicInformationBean) as List<BasicInformationBean>
        var tagesInfoList: List<BasicInformationBean> =
            DataHelper.getData(DataHelper.tagesInfoList) as List<BasicInformationBean>

        tCheckAdapter.dataList = basicInformationBeanList
        fCheckAdapter.dataList = tagesInfoList

        bind.lv.adapter = tCheckAdapter
        bind.lvError.adapter = fCheckAdapter

        bind.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbn_car_baseinfo -> {
                    bind.lv.visibility = View.VISIBLE
                    bind.lvError.visibility = View.GONE
                }
                R.id.rbn_car_error_info -> {
                    bind.lv.visibility = View.GONE
                    bind.lvError.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun viewOnClick() {

    }
}
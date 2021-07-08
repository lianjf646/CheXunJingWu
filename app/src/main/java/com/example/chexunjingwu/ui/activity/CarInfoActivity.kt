package com.example.chexunjingwu.ui.activity

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.dylanc.viewbinding.binding
import com.example.chexunjingwu.R
import com.example.chexunjingwu.base.BaseViewModelActivity
import com.example.chexunjingwu.databinding.ActivityCarInfoBinding
import com.example.chexunjingwu.http.api.HttpResponseState
import com.example.chexunjingwu.http.request.GetAlarmByIdRequest
import com.example.chexunjingwu.http.request.GetVerificationPortraitByIdRequest
import com.example.chexunjingwu.tools.OnClickViewListener
import com.example.chexunjingwu.tools.glideLoad
import com.example.chexunjingwu.ui.adapter.PersonnelrInfoAdapter
import com.example.chexunjingwu.viewmodel.CarInfoViewModel
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter

class CarInfoActivity : BaseViewModelActivity<CarInfoViewModel>() {
    val bind: ActivityCarInfoBinding by binding()
    override fun observe() {
        mViewModel.chaCheLiveData.observe(this, {
            if (it?.httpResponseState == HttpResponseState.STATE_SUCCESS) {
                glideLoad(it?.httpResponse?.comparison_img!!, bind.ivCaron)
                bind.tvCaron.text = it?.httpResponse?.comparison_message?.hphm
                bind.idFlowlayout.adapter = object : TagAdapter<String>(it?.httpResponse?.Tags) {
                    override fun getView(parent: FlowLayout?, position: Int, t: String?): View {
                        var tv = LayoutInflater.from(activity).inflate(
                            R.layout.tv,
                            null
                        ) as TextView
                        tv.setText(t);
                        return tv;
                    }
                }
                var adapter = PersonnelrInfoAdapter()
                adapter.dataList = it?.httpResponse?.basicInformation!!
                bind.lv.adapter = adapter

                var adapterError = PersonnelrInfoAdapter()
                adapterError.dataList = it?.httpResponse?.tagesInfoList!!
                bind.lvError.adapter = adapterError
            }
        })

        mViewModel.getVerificationPortraitByIdLiveData.observe(this, {
            if(it?.httpResponseState==HttpResponseState.STATE_SUCCESS){
                var bean = it?.httpResponse?.data
                glideLoad(bean?.comparison_img!!,bind.ivCaron)

                bind.idFlowlayout.adapter = object : TagAdapter<String>(bean?.tags) {
                    override fun getView(parent: FlowLayout?, position: Int, t: String?): View {
                        var tv = LayoutInflater.from(activity).inflate(
                            R.layout.tv,
                            null
                        ) as TextView
                        tv.setText(t);
                        return tv;
                    }
                }

                var adapter = PersonnelrInfoAdapter()
                adapter.dataList = bean.basicInformation!!
                bind.lv.adapter = adapter

                var adapterError = PersonnelrInfoAdapter()
                adapterError.dataList =bean.tagesInfoList!!
                bind.lvError.adapter = adapterError
            }
        })
    }

    override fun initData() {
        bind.includeTitle.tvTitle.text = "车辆详情";
        bind.includeTitle.ibnBack.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                finish()
            }
        });
        val byId = intent!!.getStringExtra("byId")!!
        if (byId.isNullOrEmpty()) {
            var comparison_id = intent!!.getStringExtra("comparison_id")!!
            mViewModel.getVerificationPortraitById(GetVerificationPortraitByIdRequest(comparison_id))
        } else {
            mViewModel.getAlarmById(GetAlarmByIdRequest(byId))
        }
    }

    override fun viewOnClick() {
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
}
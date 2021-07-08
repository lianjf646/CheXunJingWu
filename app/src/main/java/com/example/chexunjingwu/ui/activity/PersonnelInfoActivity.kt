package com.example.chexunjingwu.ui.activity

import android.view.LayoutInflater
import android.view.View
import android.widget.RadioGroup
import android.widget.TextView
import com.dylanc.viewbinding.binding
import com.example.chexunjingwu.R
import com.example.chexunjingwu.base.BaseViewModelActivity
import com.example.chexunjingwu.databinding.ActivityPersonnelrInfoBinding
import com.example.chexunjingwu.http.api.HttpResponseState
import com.example.chexunjingwu.http.request.GetAlarmByIdRequest
import com.example.chexunjingwu.http.request.GetVerificationPortraitByIdRequest
import com.example.chexunjingwu.tools.OnClickViewListener
import com.example.chexunjingwu.tools.getStrings
import com.example.chexunjingwu.tools.glideLoad
import com.example.chexunjingwu.ui.adapter.PersonnelrInfoAdapter
import com.example.chexunjingwu.viewmodel.PersonnelInfoViewModel
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter

class PersonnelInfoActivity : BaseViewModelActivity<PersonnelInfoViewModel>() {
    private val bind: ActivityPersonnelrInfoBinding by binding()

    override fun observe() {
        mViewModel.getAlarmByIdLiveData.observe(this, {
            if (it?.httpResponseState == HttpResponseState.STATE_SUCCESS) {
                if (it?.httpResponse == null) return@observe
                var bean = it?.httpResponse
                glideLoad(bean?.path!!, bind.ivCaron)
                bind.tvId.text = bean.idcard;
                bind.tvCaron.text =
                    "姓名:${getStrings(bean.name)}  性别:${getStrings(bean.sex)}  相似度${bean.score}";

                var adapter = PersonnelrInfoAdapter()
                adapter.dataList = bean.comparisonData.basicInformation
                bind.lv.adapter = adapter

                var adapterError = PersonnelrInfoAdapter()
                adapterError.dataList = bean.comparisonData.tagesInfoList
                bind.lvError.adapter = adapterError

                bind.idFlowlayout.adapter = object : TagAdapter<String>(bean?.comparisonData.Tags) {
                    override fun getView(parent: FlowLayout?, position: Int, t: String?): View {
                        var tv = LayoutInflater.from(activity).inflate(
                            R.layout.tv,
                            null
                        ) as TextView
                        tv.setText(t);
                        return tv;
                    }
                }
            }
        })

        mViewModel.getVerificationPortraitByIdLiveData.observe(this, {
            if (it?.httpResponseState == HttpResponseState.STATE_SUCCESS) {
                var dataDTO = it?.httpResponse?.data
                glideLoad(dataDTO?.comparison_img!!, bind.ivCaron)
                bind.idFlowlayout.adapter = object : TagAdapter<String>(dataDTO.tags) {
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
                adapter.dataList = dataDTO?.basicInformation!!
                bind.lv.adapter = adapter

                var adapterError = PersonnelrInfoAdapter()
                adapterError.dataList = dataDTO.tagesInfoList!!
                bind.lvError.adapter = adapterError
            }
        })
    }

    override fun initData() {
        bind.includeTitle.tvTitle.text = "人员详情";
        bind.includeTitle.ibnBack.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                finish()
            }
        })
        val pos = intent.getIntExtra("pos", -1)
        if (pos == -1) {
            var comparison_id = intent.getStringExtra("comparison_id")
            mViewModel.getVerificationPortraitById(GetVerificationPortraitByIdRequest(comparison_id))
        } else {
            var alarm_id = intent.getStringExtra("byId")
            mViewModel.getAlarmById(GetAlarmByIdRequest(alarm_id), pos)
        }
    }

    override fun viewOnClick() {
        bind.radioGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                when (checkedId) {
                    R.id.rbn_car_baseinfo -> {
                        bind.lv.visibility = View.VISIBLE;
                        bind.lvError.visibility = View.GONE;
                    }
                    R.id.rbn_car_error_info -> {
                        bind.lv.visibility = View.GONE;
                        bind.lvError.visibility = View.VISIBLE;
                    }
                }
            }
        })
    }
}
package com.hylink.chexunjingwu.ui.activity

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import com.dylanc.viewbinding.binding
import com.hylink.chexunjingwu.base.BaseViewModelActivity
import com.hylink.chexunjingwu.databinding.ActivityFeedBackDetailBinding
import com.hylink.chexunjingwu.databinding.HeardViewFeedbackBinding
import com.hylink.chexunjingwu.http.api.HttpResponseState
import com.hylink.chexunjingwu.http.response.GetJqListResponse
import com.hylink.chexunjingwu.tools.OnClickViewListener
import com.hylink.chexunjingwu.ui.adapter.FeedBackAdapter1
import com.hylink.chexunjingwu.viewmodel.FeedBackDetailViewModel

class FeedBackDetailActivity : BaseViewModelActivity<FeedBackDetailViewModel>() {
    private val bind: ActivityFeedBackDetailBinding by binding()
    var feedBackAdapter1: FeedBackAdapter1 = FeedBackAdapter1()

    override fun observe() {
        mViewModel.getJqFkListLiveData.observe(this, {
            if (it?.httpResponseState == HttpResponseState.STATE_SUCCESS) {
                feedBackAdapter1.dataList = it?.httpResponse?.list!!
                feedBackAdapter1.notifyDataSetChanged()
            }

        })
    }

    override fun initData() {
        bind.includeTitle.tvTitle.text = "反馈详情"
        bind.includeTitle.ibnBack.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                finish()
            }
        })
//        feedBackAdapter = FeedBackAdapter(activity);
        val bean: GetJqListResponse.ListBean? =
            intent.getSerializableExtra("getjqlistresponse.resultbean.listbean") as GetJqListResponse.ListBean?
        val jqbh = intent.getStringExtra("jqbh")


        var heardBind: HeardViewFeedbackBinding =
            HeardViewFeedbackBinding.inflate(LayoutInflater.from(activity))
        heardBind.tvPfsj.text = "派发时间: ${bean?.bjsj}"
        heardBind.tvQsjy.text = "签收警员: ${bean?.qsjyxm}"
        heardBind.tvQssj.text = "签收时间: ${bean?.qssj}"
        heardBind.tvDcjy.text = "到场警员: ${bean?.dcjyxm}"
        heardBind.tvDcsj.text = "到场时间: ${bean?.dcsj}"

        bind.lv.addHeaderView(heardBind.root)
        bind.lv.adapter = feedBackAdapter1;

        if (!TextUtils.isEmpty(jqbh)) {
            mViewModel.getJqFkList(jqbh)
        }


    }

    override fun viewOnClick() {

    }
}
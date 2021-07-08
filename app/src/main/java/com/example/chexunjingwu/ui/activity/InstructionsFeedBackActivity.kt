package com.example.chexunjingwu.ui.activity

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.dylanc.viewbinding.binding
import com.example.chexunjingwu.R
import com.example.chexunjingwu.base.BaseViewModelActivity
import com.example.chexunjingwu.databinding.ActivityInstructionsFeedBackBinding
import com.example.chexunjingwu.http.api.HttpResponseState
import com.example.chexunjingwu.http.request.JTztgFkRequest
import com.example.chexunjingwu.http.response.YwDictResponse
import com.example.chexunjingwu.tools.DataHelper
import com.example.chexunjingwu.tools.OnClickViewListener
import com.example.chexunjingwu.tools.showNormal
import com.example.chexunjingwu.viewmodel.InstructionsFeedBackViewModel
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter

class InstructionsFeedBackActivity : BaseViewModelActivity<InstructionsFeedBackViewModel>() {
    private val bind: ActivityInstructionsFeedBackBinding by binding()
    var notice_id: String? = null
    var dataBeans: List<YwDictResponse.DataBean?>? = null
    override fun observe() {

        mViewModel.reedBackLiveData.observe(this, {
            if (it?.httpResponseState != HttpResponseState.STATE_SUCCESS) return@observe
            dataBeans = it?.httpResponse?.data
            if (dataBeans.isNullOrEmpty()) return@observe
            bind.tagFlow.adapter =
                object : TagAdapter<YwDictResponse.DataBean>(dataBeans) {
                    override fun getView(
                        parent: FlowLayout,
                        position: Int,
                        dataBean: YwDictResponse.DataBean
                    ): View {
                        val tv =
                            LayoutInflater.from(activity).inflate(R.layout.tv_tag, null) as TextView
                        tv.text = dataBean.name
                        return tv
                    }
                }

        })
        mViewModel.jTztgFkLiveData.observe(this, {
            if (it?.httpResponseState != HttpResponseState.STATE_SUCCESS) return@observe
            showNormal("反馈成功")
            setResult(RESULT_OK)
            finish()
        })
    }

    override fun initData() {
        notice_id = intent.getStringExtra("notice_id")
        bind.includeTitle.tvTitle.text = "指令反馈"
        bind.includeTitle.ibnBack.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                finish()
            }
        })


        mViewModel.ywDict()
    }

    override fun viewOnClick() {
        bind.btnConfirm.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {

                var fknr = bind.etContent.text.toString()
                if (TextUtils.isEmpty(fknr)) {
                    showNormal("请输入内容")
                    return
                }
                var request = JTztgFkRequest(
                    pad_cid = DataHelper.imei,
                    notice_id = notice_id,
                    fknr = fknr
                )
                mViewModel.jTztgFk(request)
            }
        })

        bind.tagFlow.setOnTagClickListener { view, position, parent ->
            bind.etContent.append(dataBeans!![position]?.name!!)
            false
        }
    }
}
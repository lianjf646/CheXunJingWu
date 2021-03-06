package com.hylink.chexunjingwu.ui.activity

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.dylanc.viewbinding.binding
import com.hylink.chexunjingwu.R
import com.hylink.chexunjingwu.base.BaseViewModelActivity
import com.hylink.chexunjingwu.databinding.ActivityInstructionsFeedBackBinding
import com.hylink.chexunjingwu.http.api.HttpResponseState
import com.hylink.chexunjingwu.http.request.JTztgFkRequest
import com.hylink.chexunjingwu.http.response.YwDictResponse
import com.hylink.chexunjingwu.tools.DataHelper
import com.hylink.chexunjingwu.tools.OnClickViewListener
import com.hylink.chexunjingwu.tools.showNormal
import com.hylink.chexunjingwu.viewmodel.InstructionsFeedBackViewModel
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
            showNormal("????????????")
            setResult(RESULT_OK)
            finish()
        })
    }

    override fun initData() {
        notice_id = intent.getStringExtra("notice_id")
        bind.includeTitle.tvTitle.text = "????????????"
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
                    showNormal("???????????????")
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
package com.hylink.chexunjingwu.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import com.dylanc.viewbinding.binding
import com.hylink.chexunjingwu.R
import com.hylink.chexunjingwu.base.BaseVMFragment
import com.hylink.chexunjingwu.databinding.FragmentPresonCheckBinding
import com.hylink.chexunjingwu.http.api.HttpResponseState
import com.hylink.chexunjingwu.http.request.ChaRenRequest
import com.hylink.chexunjingwu.http.response.BasicInformationBean
import com.hylink.chexunjingwu.http.response.HomeLoginResponse
import com.hylink.chexunjingwu.tools.*
import com.hylink.chexunjingwu.ui.activity.CheckDetailActivity
import com.hylink.chexunjingwu.viewmodel.CheckPersonViewModel

class CheckPersonFragment : BaseVMFragment<CheckPersonViewModel>(R.layout.fragment_preson_check) {
    val bind: FragmentPresonCheckBinding by binding()
    var userBean: HomeLoginResponse.Data.Data.User =
        DataHelper.getData(DataHelper.loginUserInfo) as HomeLoginResponse.Data.Data.User

    var basicInformationBeanList: List<BasicInformationBean>? = null
    var tagesInfoList: List<BasicInformationBean>? = null

    override fun setArguments(args: Bundle?) {
        super.setArguments(args)
        val idCard = args!!.getString("idCard")
        if (!TextUtils.isEmpty(idCard)) {
            bind.etContent.setText(idCard)
            checkRY()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.etContent.setOnEditorActionListener { v, actionId, event ->
            if (actionId === EditorInfo.IME_ACTION_SEARCH) {
                checkRY()
                return@setOnEditorActionListener true
            }
            false
        }
        bind.llDetail.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                if (basicInformationBeanList.isNullOrEmpty()) return
                DataHelper.putData(DataHelper.basicInformationBean, basicInformationBeanList)
                DataHelper.putData(DataHelper.tagesInfoList, tagesInfoList)

                startActivity(Intent(activity, CheckDetailActivity::class.java))
            }
        })
        bind.tvNotData.text = "暂无核查信息"
    }

    @SuppressLint("SetTextI18n")
    override fun observe() {
        mViewModel.chaRenLiveData.observe(this, {
            if (it?.httpResponseState == HttpResponseState.STATE_SUCCESS) {
                if (it?.httpResponse?.data.isNullOrEmpty()) {
                    showNormal("暂无核查信息")
                    checkDataIsEmpty(true)
                    return@observe
                }
                val basicInformationBean: List<BasicInformationBean>? =
                    it?.httpResponse?.data?.get(0)?.basicInformation

                var comparison_exception = it?.httpResponse?.data?.get(0)?.comparison_exception

                var tags: List<String> = it?.httpResponse?.data?.get(0)?.Tags!!
                if (basicInformationBean.isNullOrEmpty()) {
                    showNormal("暂无核查信息")
                    checkDataIsEmpty(true)
                    return@observe
                }
                checkDataIsEmpty(false)
                basicInformationBeanList = it?.httpResponse?.data?.get(0)?.basicInformation
                tagesInfoList = it?.httpResponse?.data?.get(0)?.tagesInfoList
                val stringMap: Map<String, String> = basicInformationBean?.get(0)?.data?.get(0)!!
                bind.tvName.text = "姓名:" + getStrings(stringMap["XM"]!!)
                bind.tvSex.text = "性别:" + getStrings(stringMap["XB"]!!)
                bind.tvNation.text = "民族:" + getStrings(stringMap["MZ"]!!)
                val CSRQ = stringMap["CSRQ"]

                if (!TextUtils.isEmpty(CSRQ)) {
                    val sb = StringBuffer(CSRQ)
                    sb.insert(4, "年")
                    sb.insert(7, "月")
                    sb.insert(10, "日")
                    bind.tvBirthData.text = "出生:$sb"
                }
                bind.tvAddress.text = "住址:" + getStrings(stringMap["户口所在地"]!!)
                bind.tvId.text = "公民身份证号码:" + getStrings(stringMap["居民身份号码"]!!)
                if (TextUtils.isEmpty(stringMap["服务处所"]!!)) {
                    bind.tvWorkAddress.visibility = View.GONE
                } else {
                    bind.tvWorkAddress.text = "服务处所:" + stringMap["服务处所"]
                    bind.tvWorkAddress.visibility = View.VISIBLE
                }
                if (!TextUtils.isEmpty(stringMap["XP"])) {
                    glideLoad(stringMap["XP"]!!, bind.ivRenxiang)
                    bind.ivRenxiang.visibility = View.VISIBLE
                } else {
                    bind.ivRenxiang.visibility = View.INVISIBLE
                }

                if (comparison_exception == 1) {
                    bind.tvState.text = tags?.get(0)
                    bind.tvState.setTextColor(Color.RED)
                    bind.tvState.setBackgroundResource(R.drawable.hongbei)
                } else {
                    bind.tvState.text = "正常"
                    bind.tvState.setTextColor(Color.GREEN)
                    bind.tvState.setBackgroundResource(R.drawable.lvbei)
                }
            }
        })
    }

    private fun checkRY() {

        var idNumber = bind.etContent.text.toString()
        if (!isIDNumber(idNumber)) {
            showNormal("请输入有效身份证号码")
            return
        }
        var request = ChaRenRequest()
        var bean = ChaRenRequest.OperatorBean()
        bean.jybmbh = userBean?.group?.code
        bean.jybmmc = userBean?.group?.name
        bean.jycode = userBean?.pcard
        bean.jyxm = userBean?.name
        bean.jysfzh = userBean?.idCard
        request.operator = bean
        request.gps_x = ""
        request.gps_y = ""
        request.pad_cid = DataHelper.imei
        request.username = userBean.name
        request.ry_sfzh = idNumber
        mViewModel.checkRY(request)

    }

    private fun checkDataIsEmpty(isEmpty: Boolean) {
        if (isEmpty) {
            bind.tvNotData.visibility = View.VISIBLE
            bind.llDetail.visibility = View.GONE
        } else {
            bind.tvNotData.visibility = View.GONE
            bind.llDetail.visibility = View.VISIBLE
        }
    }
}
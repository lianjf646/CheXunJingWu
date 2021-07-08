package com.example.chexunjingwu.ui.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import com.dylanc.viewbinding.binding
import com.example.chexunjingwu.R
import com.example.chexunjingwu.base.BaseVMFragment
import com.example.chexunjingwu.bean.DialogChoiceCarBean
import com.example.chexunjingwu.databinding.FragmentCheckCarBinding
import com.example.chexunjingwu.http.api.HttpResponseState
import com.example.chexunjingwu.http.request.ChaCheRequest
import com.example.chexunjingwu.http.response.BasicInformationBean
import com.example.chexunjingwu.http.response.HomeLoginResponse
import com.example.chexunjingwu.tools.DataHelper
import com.example.chexunjingwu.tools.OnClickViewListener
import com.example.chexunjingwu.tools.getStrings
import com.example.chexunjingwu.tools.showNormal
import com.example.chexunjingwu.tools.text.SpannableBuilder
import com.example.chexunjingwu.tools.text.SpannableStringUtil
import com.example.chexunjingwu.ui.activity.CheckActivity
import com.example.chexunjingwu.ui.activity.CheckDetailActivity
import com.example.chexunjingwu.ui.adapter.CarTypeAdapter
import com.example.chexunjingwu.viewmodel.CheckCarViewModel
import java.util.*

class CheckCarFragment : BaseVMFragment<CheckCarViewModel>(R.layout.fragment_check_car) {

    val bind: FragmentCheckCarBinding by binding()
    private val carBeanList = ArrayList<DialogChoiceCarBean>()
    private var dialogChoiceCarBean: DialogChoiceCarBean? = null
    var userBean: HomeLoginResponse.Data.Data.User =
        DataHelper.getData(DataHelper.loginUserInfo) as HomeLoginResponse.Data.Data.User
    var basicInformationBeanList: List<BasicInformationBean>? = null
    var tagesInfoList: List<BasicInformationBean>? = null
    private var idCard: String? = null
    override fun observe() {
        mViewModel.chaCheLiveData.observe(this, {
            if (it?.httpResponseState == HttpResponseState.STATE_SUCCESS) {
                if (it?.httpResponse?.data == null) {
                    showNormal("暂无核查信息")
                    checkDataIsEmpty(true)
                    return@observe
                }
                if (it?.httpResponse?.data.isNullOrEmpty()) {
                    showNormal("暂无核查信息.")
                    checkDataIsEmpty(true)
                    return@observe
                }
                var dabean = it?.httpResponse?.data!![0];
                if (dabean?.basicInformation.isNullOrEmpty()) {
                    showNormal("暂无核查信息..")
                    checkDataIsEmpty(true)
                    return@observe
                }
                if (dabean?.basicInformation!![0]?.data.isNullOrEmpty()) {
                    showNormal("暂无核查信息...")
                    checkDataIsEmpty(true)
                    return@observe
                }
                DataHelper.putData(
                    DataHelper.basicInformationBean,
                    it?.httpResponse?.data?.get(0)?.basicInformation!!
                )
                DataHelper.putData(
                    DataHelper.tagesInfoList,
                    it?.httpResponse?.data?.get(0)?.tagesInfoList!!
                )

                basicInformationBeanList = dabean?.basicInformation
                tagesInfoList = it?.httpResponse?.data?.get(0)?.tagesInfoList
                checkDataIsEmpty(false)
                val dataBean: Map<String, String> = dabean?.basicInformation?.get(0)?.data?.get(0)!!

                bind.cphm.text = "车牌号码:" + getStrings(dataBean["HPHM"])
                bind.cpzl.text = "车牌种类:" + getStrings(dataBean["HPZL"])
                bind.clpp.text = "车辆品牌:" + getStrings(dataBean["中文品牌"])
                bind.ys.text = "颜色:" + getStrings(dataBean["CSYS"])
                bind.cjh.text = "车架号:" + getStrings(dataBean["CLSBDH"])
                bind.xm.text = "姓名:" + getStrings(dataBean["SYR"])
                val idCode = "身份证:" + getStrings(dataBean["居民身份号码"])

                idCard = dataBean["居民身份号码"]
                if (!TextUtils.isEmpty(idCode)) {
                    bind.sfzh.setText(
                        SpannableStringUtil.withInclusiveInclusive(
                            idCode, SpannableBuilder.Builder()
                                .withForegroundColorSpan(
                                    4,
                                    idCode.length,
                                    Color.parseColor("#01B8F7")
                                )
                                .withUnderlineSpan(4, idCode.length)
                                .build()
                        )
                    )
                }
                if (dabean?.comparison_exception === 1) {
                    bind.tvState.text = dabean?.Tags!![0]
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addCarBeanList()
        dialogChoiceCarBean = carBeanList[0]

        var carTypeAdapter = CarTypeAdapter()
        carTypeAdapter.dataList = carBeanList
        bind.spinner.adapter = carTypeAdapter
        bind.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                dialogChoiceCarBean = carTypeAdapter.getItem(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        bind.etContent.setOnEditorActionListener { v, actionId, event ->
            if (actionId === EditorInfo.IME_ACTION_SEARCH) {
                checCL()
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

        bind.sfzh.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                if (TextUtils.isEmpty(idCard)) {
                    return
                }
                (activity as CheckActivity).clickRadio(idCard)
            }

        })
        bind.tvNotData.text = "暂无核查信息"
    }

    private fun checCL() {
        val caron = bind.etContent.text.toString().trim()
        if (TextUtils.isEmpty(caron)) {
            showNormal("请输入车牌号码")
            return
        }

        var chaCheRequest = ChaCheRequest()
        var operatorBean = ChaCheRequest.OperatorBean()
        operatorBean.jybmbh = userBean?.group?.code
        operatorBean.jybmmc = userBean?.group?.name
        operatorBean.jycode = userBean?.pcard
        operatorBean.jysfzh = userBean?.idCard
        operatorBean.jyxm = userBean?.name
        chaCheRequest.operator = operatorBean
        chaCheRequest.cl_fdjh = "sd"
        chaCheRequest.cl_hphm = caron
        chaCheRequest.cl_hpzl = dialogChoiceCarBean?.code
        chaCheRequest.cl_sbdm = ""
        chaCheRequest.pad_cid = DataHelper.imei
        chaCheRequest.username = userBean.name
        chaCheRequest.gps_x = ""
        chaCheRequest.gps_y = ""
        mViewModel.checCL(chaCheRequest)


    }

    private fun checkDataIsEmpty(isEmpty: Boolean) {
        if (isEmpty) {
            bind.tvNotData.setVisibility(View.VISIBLE)
            bind.llDetail.setVisibility(View.GONE)
        } else {
            bind.tvNotData.setVisibility(View.GONE)
            bind.llDetail.setVisibility(View.VISIBLE)
        }
    }

    private fun addCarBeanList() {
        carBeanList.add(DialogChoiceCarBean("小型汽车", "02"))
        carBeanList.add(DialogChoiceCarBean("大型汽车", "01"))
        carBeanList.add(DialogChoiceCarBean("使馆汽车", "03"))
        carBeanList.add(DialogChoiceCarBean("领馆汽车", "04"))
        carBeanList.add(DialogChoiceCarBean("境外汽车", "05"))
        carBeanList.add(DialogChoiceCarBean("外籍汽车", "06"))
        carBeanList.add(DialogChoiceCarBean("两、三轮摩托车", "07"))
        carBeanList.add(DialogChoiceCarBean("轻便摩托车", "08"))
        carBeanList.add(DialogChoiceCarBean("使馆摩托车", "09"))
        carBeanList.add(DialogChoiceCarBean("领馆摩托车", "10"))
        carBeanList.add(DialogChoiceCarBean("境外摩托车", "11"))
        carBeanList.add(DialogChoiceCarBean("外籍摩托车", "12"))
        carBeanList.add(DialogChoiceCarBean("农用运输车类", "13"))
        carBeanList.add(DialogChoiceCarBean("挂车", "15"))
        carBeanList.add(DialogChoiceCarBean("教练车", "16"))
        carBeanList.add(DialogChoiceCarBean("教练摩托车", "17"))
        carBeanList.add(DialogChoiceCarBean("试验汽车", "18"))
        carBeanList.add(DialogChoiceCarBean("试验摩托车", "19"))
        carBeanList.add(DialogChoiceCarBean("临时入境汽车", "20"))
        carBeanList.add(DialogChoiceCarBean("临时入境摩托车", "21"))
        carBeanList.add(DialogChoiceCarBean("临时行驶车", "22"))
        carBeanList.add(DialogChoiceCarBean("警用汽车号牌", "23"))
        carBeanList.add(DialogChoiceCarBean("警用摩托车号牌", "24"))
        carBeanList.add(DialogChoiceCarBean("军用车辆号牌", "25"))
    }
}
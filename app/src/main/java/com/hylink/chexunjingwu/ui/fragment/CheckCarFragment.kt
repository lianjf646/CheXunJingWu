package com.hylink.chexunjingwu.ui.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import com.dylanc.viewbinding.binding
import com.hylink.chexunjingwu.R
import com.hylink.chexunjingwu.base.BaseVMFragment
import com.hylink.chexunjingwu.bean.DialogChoiceCarBean
import com.hylink.chexunjingwu.databinding.FragmentCheckCarBinding
import com.hylink.chexunjingwu.http.api.HttpResponseState
import com.hylink.chexunjingwu.http.request.ChaCheRequest
import com.hylink.chexunjingwu.http.response.BasicInformationBean
import com.hylink.chexunjingwu.http.response.HomeLoginResponse
import com.hylink.chexunjingwu.tools.DataHelper
import com.hylink.chexunjingwu.tools.OnClickViewListener
import com.hylink.chexunjingwu.tools.getStrings
import com.hylink.chexunjingwu.tools.showNormal
import com.hylink.chexunjingwu.tools.text.SpannableBuilder
import com.hylink.chexunjingwu.tools.text.SpannableStringUtil
import com.hylink.chexunjingwu.ui.activity.CheckActivity
import com.hylink.chexunjingwu.ui.activity.CheckDetailActivity
import com.hylink.chexunjingwu.ui.adapter.CarTypeAdapter
import com.hylink.chexunjingwu.viewmodel.CheckCarViewModel
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
                    showNormal("??????????????????")
                    checkDataIsEmpty(true)
                    return@observe
                }
                if (it?.httpResponse?.data.isNullOrEmpty()) {
                    showNormal("??????????????????.")
                    checkDataIsEmpty(true)
                    return@observe
                }
                var dabean = it?.httpResponse?.data!![0];
                if (dabean?.basicInformation.isNullOrEmpty()) {
                    showNormal("??????????????????..")
                    checkDataIsEmpty(true)
                    return@observe
                }
                if (dabean?.basicInformation!![0]?.data.isNullOrEmpty()) {
                    showNormal("??????????????????...")
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

                bind.cphm.text = "????????????:" + getStrings(dataBean["HPHM"])
                bind.cpzl.text = "????????????:" + getStrings(dataBean["HPZL"])
                bind.clpp.text = "????????????:" + getStrings(dataBean["????????????"])
                bind.ys.text = "??????:" + getStrings(dataBean["CSYS"])
                bind.cjh.text = "?????????:" + getStrings(dataBean["CLSBDH"])
                bind.xm.text = "??????:" + getStrings(dataBean["SYR"])
                val idCode = "?????????:" + getStrings(dataBean["??????????????????"])

                idCard = dataBean["??????????????????"]
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
                    bind.tvState.text = "??????"
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
        bind.tvNotData.text = "??????????????????"
    }

    private fun checCL() {
        val caron = bind.etContent.text.toString().trim()
        if (TextUtils.isEmpty(caron)) {
            showNormal("?????????????????????")
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
        carBeanList.add(DialogChoiceCarBean("????????????", "02"))
        carBeanList.add(DialogChoiceCarBean("????????????", "01"))
        carBeanList.add(DialogChoiceCarBean("????????????", "03"))
        carBeanList.add(DialogChoiceCarBean("????????????", "04"))
        carBeanList.add(DialogChoiceCarBean("????????????", "05"))
        carBeanList.add(DialogChoiceCarBean("????????????", "06"))
        carBeanList.add(DialogChoiceCarBean("?????????????????????", "07"))
        carBeanList.add(DialogChoiceCarBean("???????????????", "08"))
        carBeanList.add(DialogChoiceCarBean("???????????????", "09"))
        carBeanList.add(DialogChoiceCarBean("???????????????", "10"))
        carBeanList.add(DialogChoiceCarBean("???????????????", "11"))
        carBeanList.add(DialogChoiceCarBean("???????????????", "12"))
        carBeanList.add(DialogChoiceCarBean("??????????????????", "13"))
        carBeanList.add(DialogChoiceCarBean("??????", "15"))
        carBeanList.add(DialogChoiceCarBean("?????????", "16"))
        carBeanList.add(DialogChoiceCarBean("???????????????", "17"))
        carBeanList.add(DialogChoiceCarBean("????????????", "18"))
        carBeanList.add(DialogChoiceCarBean("???????????????", "19"))
        carBeanList.add(DialogChoiceCarBean("??????????????????", "20"))
        carBeanList.add(DialogChoiceCarBean("?????????????????????", "21"))
        carBeanList.add(DialogChoiceCarBean("???????????????", "22"))
        carBeanList.add(DialogChoiceCarBean("??????????????????", "23"))
        carBeanList.add(DialogChoiceCarBean("?????????????????????", "24"))
        carBeanList.add(DialogChoiceCarBean("??????????????????", "25"))
    }
}
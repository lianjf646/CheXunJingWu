package com.example.chexunjingwu.ui.activity

import android.content.Intent
import android.text.TextUtils
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.dylanc.viewbinding.binding
import com.example.chexunjingwu.R
import com.example.chexunjingwu.base.BaseViewModelActivity
import com.example.chexunjingwu.databinding.ActivityPoliceInforartionDetailBinding
import com.example.chexunjingwu.http.AndroidMqttClient
import com.example.chexunjingwu.http.mqttresponse.HuiZhiBean
import com.example.chexunjingwu.http.mqttresponse.PrintRquest
import com.example.chexunjingwu.http.request.JqChangeStateRequest
import com.example.chexunjingwu.http.response.GetJqListResponse
import com.example.chexunjingwu.http.response.HomeLoginResponse
import com.example.chexunjingwu.tools.DataHelper
import com.example.chexunjingwu.tools.OnClickViewListener
import com.example.chexunjingwu.viewmodel.PoliceInforartionDetailViewModel
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

class PoliceInforartionDetailActivity : BaseViewModelActivity<PoliceInforartionDetailViewModel>() {
    private val bind: ActivityPoliceInforartionDetailBinding by binding();
    private lateinit var bean: GetJqListResponse.ListBean
    private lateinit var userBean: HomeLoginResponse.Data.Data.User;
    private lateinit var jjdzt: String;

    override fun observe() {
        mViewModel.mutableLiveData.observe(this, {
            finish()
        })
    }

    override fun initData() {
        bind.includeTitle.tvTitle.text = "警情详情"
        bind.includeTitle.ibnBack.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                finish()
            }
        })
        bean =
            (intent.getSerializableExtra("getjqlistresponse.resultbean.listbean") as GetJqListResponse.ListBean?)!!

        userBean = DataHelper.getData(DataHelper.loginUserInfo) as HomeLoginResponse.Data.Data.User;
        bind.tvJqbh.setText(bean?.jqbh)
        bind.tvTime.setText(bean?.bjsj)
        bind.tvLoc.setText(bean?.afdd)
        bind.tvContent.setText(bean?.bjnr)
        bind.tvType.setText(bean?.bjlbmc)
        bind.tvCjdw.setText(bean?.gxdwmc)
        bind.tvBjdh.setText(bean?.bjdh)
        bind.tvBjr.text = if (TextUtils.isEmpty(bean?.bjr)) "匿名" else bean?.bjr


        var jjdztName = ""
        var tv_textColor = 0
        var tv_bg = 0
        jjdzt = bean?.jjdzt
        when (jjdzt) {
            "01" -> {
                jjdztName = "待签收"
                tv_bg = R.drawable.bg_radius_yellow_5
                tv_textColor = R.color.text_color_E69022
                bind.btnConfirm.text = "签收"
                bind.llPrint.visibility = View.GONE
            }
            "02" -> {
                jjdztName = "待到场"
                tv_bg = R.drawable.bg_radius_yellow_5
                tv_textColor = R.color.text_color_E69022
                bind.btnConfirm.text = "到场"
                bind.llPrint.visibility = View.GONE
            }
            "03" -> {
                jjdztName = "待结束"
                tv_bg = R.drawable.bg_radius_yellow_5
                tv_textColor = R.color.text_color_E69022
                bind.btnConfirm.text = "结束"
                bind.llPrint.visibility = View.GONE
            }
            "04" -> {
                jjdztName = "待反馈"
                tv_bg = R.drawable.bg_radius_blue_5
                tv_textColor = R.color.text_color_E5D8DF6
                bind.btnConfirm.text = "反馈"
                bind.llPrint.visibility = View.GONE
            }
            "05" -> {
                jjdztName = "已反馈"
                tv_bg = R.drawable.bg_radius_green_5
                tv_textColor = R.color.text_color_5EBB4F
                bind.btnConfirm.text = "反馈"
                bind.btnLook.visibility = View.VISIBLE
                bind.llPrint.visibility = View.VISIBLE
            }
            else -> {
                jjdztName = ""
                tv_bg = 0
                bind.llPrint.visibility = View.GONE
            }
        }

        bind.tvState.text = jjdztName
        if (tv_bg != 0) {
            val color = activity.resources.getColor(tv_textColor)
            bind.tvState.setTextColor(color)
            bind.tvState.setBackgroundResource(tv_bg)
        }

    }

    override fun viewOnClick() {

        bind.btnConfirm.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {

                val request = JqChangeStateRequest(
                    jqbh = bean?.jqbh,
                    mjdwdm = userBean?.group?.code,
                    mjdwmc = userBean?.group?.name,
                    mjjh = userBean?.pcard,
                    mjxm = userBean?.name,
                    zkimei = DataHelper.imei,
                    xzb = "",
                    yzb = ""
                )

                when (jjdzt) {
                    "01" -> {
                        mViewModel.jqQs(request)
                    }
                    "02" -> {
                        mViewModel.jqDcqr(request)
                    }
                    "03" -> {
                        mViewModel.jqJqjs(request)
                    }
                    "04",
                    "05" -> {
                        val intent = Intent()
                        intent.setClass(activity, JingQingFeedBackActivity::class.java)
                        intent.putExtra("getjqlistresponse.resultbean.listbean", bean)
                        intent.putExtra("code", userBean?.group?.code)
                        goFeedBackActivity.launch(intent)
                    }
                    else -> {
                    }
                }
            }
        })

        bind.btnLook.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                val intent = Intent(activity, FeedBackDetailActivity::class.java)
                intent.putExtra("jqbh", bean.jqbh)
                intent.putExtra("getjqlistresponse.resultbean.listbean", bean)
                startActivity(intent)
            }

        })

        bind.tvHuiZhiDan.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd") //
                var date = Date(System.currentTimeMillis())
                var huiZhiBean = HuiZhiBean()
                huiZhiBean.bao_an_shi_jian = bean.bjsj.replace("/", "-").substring(0, 10)
                huiZhiBean.neirong = bean.bjnr
                huiZhiBean.xingming = bean.bjr
                huiZhiBean.bao_an_ren = bean.bjr
                huiZhiBean.ju_bao_ren = bean.bjr
                huiZhiBean.lian_xi_dan_wei = bean.gxdwmc
                huiZhiBean.lian_xi_dian_hua = bean.bjdh
                huiZhiBean.dan_qian_shi_jian = simpleDateFormat.format(date)
                val printRquest = PrintRquest()
                printRquest.type = "hui_zhi_dan"
                printRquest.printStateTopic = AndroidMqttClient.printState
                printRquest.content = Gson().toJson(huiZhiBean)
                printRquest.code = bean.gxdwdm
                var topic = "Ret/print/Send/" + DataHelper.imei
                AndroidMqttClient.publish(topic = topic, msg = Gson().toJson(printRquest))
            }
        })

        bind.tvChuFaDan.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                val intent = Intent(activity, ChuFaDanActivity::class.java)
                intent.putExtra("gxdwdm", bean.gxdwdm)
                intent.putExtra("jqbh", bean.jqbh)
                startActivity(intent)
            }
        })
    }

    var goFeedBackActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it?.resultCode == RESULT_OK) {
                finish()
            }
        }


}
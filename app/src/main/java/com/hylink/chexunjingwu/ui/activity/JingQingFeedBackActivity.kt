package com.hylink.chexunjingwu.ui.activity

import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bigkoo.pickerview.view.TimePickerView
import com.blankj.utilcode.constant.RegexConstants
import com.blankj.utilcode.util.RegexUtils
import com.dylanc.viewbinding.binding
import com.hylink.chexunjingwu.R
import com.hylink.chexunjingwu.base.BaseViewModelActivity
import com.hylink.chexunjingwu.databinding.ActivityJingQingFeedBackBinding
import com.hylink.chexunjingwu.dialog.JqDictDialog
import com.hylink.chexunjingwu.dialog.YwDictDialog
import com.hylink.chexunjingwu.http.api.HttpResponseState
import com.hylink.chexunjingwu.http.request.JqJqfkRequest
import com.hylink.chexunjingwu.http.response.*
import com.hylink.chexunjingwu.tools.*
import com.hylink.chexunjingwu.viewmodel.JingQingFeedBackViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import java.util.*
import kotlin.collections.ArrayList

class JingQingFeedBackActivity : BaseViewModelActivity<JingQingFeedBackViewModel>() {

    private val bind: ActivityJingQingFeedBackBinding by binding()
    private var bean: GetJqListResponse.ListBean? = null
    private lateinit var userBean: HomeLoginResponse.Data.Data.User

    private var bjlbdm: String? = null
    private var bjlxdm: String? = null
    private var bjxldm: String? = null
    private var bjsj: String? = null
    private var fk_police_alarm_flow_id: String? = null

    private var pvTime: TimePickerView? = null

    private var bjlbdmEntity: JqDictResponse? = null
    private var bjlxdmEntity: JqDictResponse? = null
    private var bjxldmEntity: JqDictResponse? = null

    private var bean131: JqDictResponse.ListBean? = null
    private var bean141: JqDictResponse.ListBean? = null
    private lateinit var jqDictDialog: JqDictDialog
    private lateinit var ywDictDialog: YwDictDialog
    private var listDTOList: ArrayList<GetUserListResponse.DataDTO.ListDTO>? = null


    private val getPoliceListInfo =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                listDTOList = it.data?.getParcelableArrayListExtra("policeListInfo")!!
                if (listDTOList.isNullOrEmpty()) {
                    bind.tagFlow.visibility = View.GONE
                    bind.etChuDongCheChuanShu.setText("0")
                    return@registerForActivityResult
                }
                bind.tagFlow.visibility = View.VISIBLE
                bind.etCdjl.setText(listDTOList?.size.toString())
                bind.tagFlow.adapter =
                    object : TagAdapter<GetUserListResponse.DataDTO.ListDTO>(listDTOList) {
                        override fun getView(
                            parent: FlowLayout,
                            position: Int,
                            dto: GetUserListResponse.DataDTO.ListDTO
                        ): View {
                            val tv = LayoutInflater.from(this@JingQingFeedBackActivity)
                                .inflate(R.layout.item_tag_name, null) as TextView
                            tv.text = dto.name
                            return tv
                        }
                    }
            }
        }

    override fun observe() {
        mViewModel.jqJqfkzcDetailsLiveData.observe(this, {
            if (it?.httpResponseState != HttpResponseState.STATE_SUCCESS) return@observe
            var response = it?.httpResponse?.data
            fk_police_alarm_flow_id = response?.police_alarm_flow_id
            bind.tvJqbh.text = getStrings(response?.jqbh);
            bind.tvAjfssj.setText(getStrings(response?.ajfssj));
            var bjr =
                if (TextUtils.isEmpty(response?.bJR))
                    "??????"
                else
                    TextUtils.isEmpty(response?.bJR)
            bind.etBjr.setText(bjr.toString())
            bind.etBjdh.setText(getStrings(response?.bjdh))
            bind.etZjh.setText(getStrings(response?.bjrzjhm))
            bind.tvJqlb.setText(getStrings(response?.jqlbmc))
            bind.tvJqlx.setText(getStrings(response?.jqlxmc))
            bind.tvJqxl.setText(getStrings(response?.jqxlmc))
            bind.etFsdd.setText(getStrings(response?.fsdd))
            bind.etFknr.setText(getStrings(response?.cjqk))
            bind.etChuDongCheChuanShu.setText(getStrings(response?.cdccs))
            bjlbdm = response?.jqlb
            bjlxdm = response?.jqlx
            bjxldm = response?.jqxl

            if (response?.ajfssj.isNullOrEmpty()) {
                bjsj = response?.ajfssj
            } else {
                bjsj = response?.police_alarm_message?.bjsj
            }

            if (!TextUtils.isEmpty(bjsj)) {
                pvTime = initDataSelectDetail(bjsj!!, activity, bind.tvAjfssj)
            }
            val fkrys = response?.fkrys
            if (!fkrys.isNullOrEmpty()) {
                listDTOList = ArrayList()
                val fkrysList: List<JqJqfkRequest.Fkrys> = Gson().fromJson(
                    fkrys,
                    object : TypeToken<List<JqJqfkRequest.Fkrys?>?>() {}.type
                )
                for (fkrys1 in fkrysList) {
                    val dto: GetUserListResponse.DataDTO.ListDTO =
                        GetUserListResponse.DataDTO.ListDTO()
                    dto.choice = true
                    dto.name = fkrys1?.mjxm
                    dto.pcard = fkrys1?.mjjh
                    listDTOList!!.add(dto)
                }
                if (listDTOList.isNullOrEmpty()) {
                    bind.etChuDongCheChuanShu.setText("0")
                    bind.tagFlow.visibility = View.GONE
                    return@observe
                }
                bind.tagFlow.visibility = View.VISIBLE
                bind.etCdjl.setText(listDTOList!!.size.toString())
                bind.tagFlow.adapter = object :
                    TagAdapter<GetUserListResponse.DataDTO.ListDTO>(listDTOList) {
                    override fun getView(
                        parent: FlowLayout,
                        position: Int,
                        o: GetUserListResponse.DataDTO.ListDTO
                    ): View {
                        val tv = LayoutInflater.from(this@JingQingFeedBackActivity)
                            .inflate(R.layout.item_tag_name, null) as TextView
                        tv.text = o.name
                        return tv
                    }
                }

//                if (!TextUtils.isEmpty(response.getResult().getData().getCjjgmc())) {
//                    bean131 = new JqDictResponse.ResultBean.ListBean();
//                    bind.tvCjjg.setText(TextUtil.getString(response.getResult().getData().getCjjgmc()));
//                    bean131.setDictkey(response.getResult().getData().getCjjg());
//                    bean131.setDictvalue(response.getResult().getData().getCjjgmc());
//                }
//                if (!TextUtils.isEmpty(response.getResult().getData().getFscsmc())) {
//                    bean141 = new JqDictResponse.ResultBean.ListBean();
//                    bind.tvFscs.setText(TextUtil.getString(response.getResult().getData().getFscsmc()));
//                    bean141.setDictkey(response.getResult().getData().getFscs());
//                    bean141.setDictvalue(response.getResult().getData().getFscsmc());
//                }
            }
        })

        mViewModel.jqDictLiveData.observe(this, {
            if (it.httpResponseState == HttpResponseState.STATE_SUCCESS) {
                var response = it.httpResponse
                var message = ""
                var dictid = response?.dictid;

                when (dictid) {
                    "121" -> {
                        bjlbdmEntity = response
                        message = "????????????????????????"
                    }
                    "161" -> {
                        bjlxdmEntity = response
                        message = "????????????????????????"
                    }
                    "181" -> {
                        bjxldmEntity = response
                        message = "????????????????????????"
                    }
                    "131" -> {
                        message = "????????????????????????"
                    }
                    "141" -> {
                        message = "????????????????????????"
                    }
                }
                if (response?.list?.isNullOrEmpty()!!) {
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
                    return@observe
                }
                jqDictDialog.dictid = dictid;
                jqDictDialog.listDTOList = response?.list
                jqDictDialog.showDialog(supportFragmentManager, "jqDictDialog")
            }
        })

        mViewModel.ywDictLiveData.observe(this, {
            if (it.httpResponseState != HttpResponseState.STATE_SUCCESS) return@observe
            ywDictDialog.listDTOList = it?.httpResponse?.data
            ywDictDialog.showDialog(supportFragmentManager, "ywDictDialog")
        })
        mViewModel.jqJqfkLiveData.observe(this, {
            if (it.httpResponseState != HttpResponseState.STATE_SUCCESS) return@observe
            setResult(RESULT_OK)
            finish()
        })

    }

    override fun initData() {
        bind.includeTitle.tvTitle.text = "????????????"
        bind.includeTitle.ibnBack.setOnClickListener { finish() }
        userBean = DataHelper.getData(DataHelper.loginUserInfo) as HomeLoginResponse.Data.Data.User
        val jqbh = intent.getStringExtra("byId")

        if (!TextUtils.isEmpty(jqbh)) {
            mViewModel.jqJqfkzcDetails(jqbh!!)
        } else {
            bean =
                intent.getSerializableExtra("getjqlistresponse.resultbean.listbean") as GetJqListResponse.ListBean
            val code: String = userBean?.group?.code!!

            bind.tvJqbh.text = bean?.jqbh
            bind.tvAjfssj.text = bean?.bjsj
            bind.etBjr.setText(if (TextUtils.isEmpty(bean?.bjr)) "??????" else bean?.bjr)
            bind.tvJqlb.text = bean?.bjlbmc
            bind.tvJqlx.text = bean?.bjlxmc
            bind.tvJqxl.text = bean?.bjxlmc
            bind.etBjdh.setText(bean?.bjdh)
            bind.etFsdd.setText(bean?.afdd)
            bjlbdm = bean?.bjlbdm
            bjlxdm = bean?.bjlxdm
            bjxldm = bean?.bjxldm
            bjsj = bean?.bjsj
            if (!TextUtils.isEmpty(bjsj)) {
                pvTime = initDataSelectDetail(bjsj!!, activity, bind.tvAjfssj)
            }


        }
        initDialog()

    }

    override fun viewOnClick() {
        bind.tvAjfssj.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                pvTime?.show()

            }
        })

        bind.tvJqlb.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                mViewModel.jqDict("121", "", userBean.pcard)
            }
        })

        bind.tvJqlx.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                if (TextUtils.isEmpty(bjlbdm)) {
                    showNormal("????????????????????????")
                    return
                }
                mViewModel.jqDict("161", bjlbdm!!, userBean.pcard)
            }
        })

        bind.tvJqxl.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                if (TextUtils.isEmpty(bjlxdm)) {
                    showNormal("????????????????????????")
                    return
                }
                mViewModel.jqDict("181", bjlxdm!!, userBean.pcard)
            }
        })

        bind.tvCjjg.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                mViewModel.jqDict("131", "", userBean.pcard)
            }
        })

        bind.tvFscs.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                mViewModel.jqDict("141", "", userBean.pcard)
            }
        })

        bind.tvAddProple.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                val intent = Intent(activity, PoliceListActivity::class.java)
                intent.putExtra("code", userBean?.group?.code)
                if (!listDTOList.isNullOrEmpty()) {
                    var pcardList = ArrayList<String>();
                    for (dto in listDTOList!!) {
                        pcardList.add(dto?.pcard.toString())
                    }
                    intent.putStringArrayListExtra("nameList", pcardList)
                }
                getPoliceListInfo.launch(intent)
            }
        })

        bind.tvFknr.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                mViewModel.ywDict()
            }
        })

        bind.btnConfirm.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                jqJqfk()
            }
        })

    }

    private fun initDialog() {
        jqDictDialog = JqDictDialog(object : OnItemClickListenerT {
            override fun onItemClick(bean: JqDictResponse.ListBean, pos: Int) {
                jqDictDialog.dismiss()
                when (jqDictDialog.dictid) {
                    "121" -> {
                        bind.tvJqlb.text = bean.dictvalue
                        bjlbdm = bean.dictkey
                        bind.tvJqlx.text = ""
                        bind.tvJqxl.text = ""
                        mViewModel.jqDict("161", bjlbdm!!, userBean.pcard)
                    }
                    "161" -> {
                        bind.tvJqlx.text = bean.dictvalue
                        bjlxdm = bean.dictkey
                        bind.tvJqxl.text = ""
                        mViewModel.jqDict("181", bean.dictkey, userBean.pcard)
                    }
                    "181" -> {
                        bind.tvJqxl.text = bean.dictvalue
                        bjxldm = bean.dictkey
                    }

                    "131" -> {
                        bean131 = bean
                        bind.tvCjjg.text = bean.dictvalue
                    }
                    "141" -> {
                        bean141 = bean
                        bind.tvFscs.text = bean.dictvalue
                    }
                }
            }
        })

        ywDictDialog = YwDictDialog(object : OnItemClickListenerYwDict {
            override fun onItemClick(bean: YwDictResponse.DataBean, pos: Int) {
                bind.etFknr.append(bean.name)
                ywDictDialog.dismiss()
            }
        })
    }

    private fun jqJqfk() {
        var request = JqJqfkRequest()
        val time = bind.tvAjfssj.text.toString()
        if (TextUtils.isEmpty(time)) {
            showNormal("?????????????????????")
            return
        }


        if (TextUtils.isEmpty(bjlbdm)) {
            if (bjlbdmEntity != null) {
                if (bjlbdmEntity?.list?.isNotEmpty() == true) {
                    showNormal("?????????????????????")
                    return
                }
            }
        }

        if (TextUtils.isEmpty(bjlxdm)) {
            if (bjlxdmEntity != null) {
                if (bjlxdmEntity?.list?.isNotEmpty() == true) {
                    showNormal("?????????????????????")
                    return
                }
            }
        }

        if (TextUtils.isEmpty(bjxldm)) {
            if (bjxldmEntity != null) {
                if (bjxldmEntity?.list?.isNotEmpty() == true) {
                    showNormal("?????????????????????")
                    return
                }
            }
        }

        val prople_num = bind.etCdjl.text.toString()

        if (TextUtils.isEmpty(prople_num)) {
            showNormal("?????????????????????")
            return
        }

        if (!RegexUtils.isMatch(RegexConstants.REGEX_POSITIVE_INTEGER, prople_num)) {
            showNormal("????????????????????????")
            return
        }

        if (listDTOList.isNullOrEmpty() || listDTOList!!.size < 2) {
            showNormal("????????????????????????")
            return
        }

        val carNum = bind.etChuDongCheChuanShu.text.toString()

        if (TextUtils.isEmpty(carNum)) {
            showNormal("????????????????????????")
            return
        }

        if (!RegexUtils.isMatch(RegexConstants.REGEX_POSITIVE_INTEGER, carNum)) {
            showNormal("???????????????????????????")
            return
        }

        val fkrysList = ArrayList<JqJqfkRequest.Fkrys>()
        for (bean in listDTOList!!) {
            val fa = JqJqfkRequest.Fkrys()
            fa.mjjh = bean.pcard
            fa.mjxm = bean.name
            fkrysList.add(fa)
        }

        request.fkrys = fkrysList

        if (bean141 == null) {
            showNormal("?????????????????????")
            return
        }

        if (bean131 == null) {
            showNormal("?????????????????????")
            return
        }

        val fknr = bind.etFknr.text.toString()
        if (TextUtils.isEmpty(fknr)) {
            showNormal("?????????????????????")
            return
        }

        request.cdccs = (Integer.valueOf(carNum)) //???????????????

        request.cdrs = (Integer.valueOf(prople_num)) //????????????

        request.fkrys = fkrysList
        request.jqlb = bjlbdm //??????????????????
        request.jqlbmc = bind.tvJqlb.text.toString() //??????????????????
        request.jqlx = bjlxdm //??????????????????
        request.jqlxmc = bind.tvJqlx.text.toString() //??????????????????
        request.jqxl = bjxldm //??????????????????
        request.jqxlmc = bind.tvJqxl.text.toString() //??????????????????
        request.ajfssj = getTimeChuo(time) //??????????????????
        request.ajjssj = getDateNow()  //????????????????????????????????????????????????
        request.fsdd = bind.etFsdd.text.toString() //????????????
        request.cjqk = fknr //
        request.bjrzjhm = bind.etZjh.text.toString()
        request.jqbh = bind.tvJqbh.text.toString() //????????????
        val Fkdwdm: String = userBean.group.code
        request.fkdwdm = getStrings(Fkdwdm) //??????????????????  userInfo.getGxdwdm()
        val Mjdwmc: String = userBean.group.name
        request.mjdwmc = getStrings(Mjdwmc)  //??????????????????
        val Fkrbh: String = userBean.pcard
        request.fkrbh = getStrings(Fkrbh) //???????????????
        request.mjjh = Fkrbh //????????????
        request.fkrxm = userBean?.name //???????????????
        request.zkimei = DataHelper.imei//??????id
        request.yzb = "" //??????
        request.xzb = "" //??????
        request.bjrxm = bind.etBjr.text.toString()
        request.fscs = bean141?.dictkey
        request.fscsmc = bean141?.dictvalue
        request.cjjg = bean131?.dictkey
        request.cjjgmc = bean131?.dictvalue
        request.bjdh = bind.etBjdh.text.toString()
        if (!TextUtils.isEmpty(fk_police_alarm_flow_id)) {
            request.fk_police_alarm_flow_id = fk_police_alarm_flow_id
        }
        mViewModel.jqJqfk(request)
    }


}
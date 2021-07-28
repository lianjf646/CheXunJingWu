package com.hylink.chexunjingwu.ui.activity

import android.content.Intent
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.bigkoo.pickerview.view.TimePickerView
import com.blankj.utilcode.util.RegexUtils
import com.dylanc.viewbinding.binding
import com.hylink.chexunjingwu.R
import com.hylink.chexunjingwu.base.BaseViewModelActivity
import com.hylink.chexunjingwu.databinding.ActivityChuFaDanBinding
import com.hylink.chexunjingwu.dialog.YwDictDialog
import com.hylink.chexunjingwu.http.AndroidMqttClient
import com.hylink.chexunjingwu.http.api.HttpResponseState
import com.hylink.chexunjingwu.http.mqttresponse.PrintRquest
import com.hylink.chexunjingwu.http.request.JqCfjdRequest
import com.hylink.chexunjingwu.http.response.GetCfjdDetailResponse
import com.hylink.chexunjingwu.http.response.GetUserListResponse
import com.hylink.chexunjingwu.http.response.HomeLoginResponse
import com.hylink.chexunjingwu.http.response.YwDictResponse
import com.hylink.chexunjingwu.tools.*
import com.hylink.chexunjingwu.viewmodel.ChuFaDanViewModel
import com.google.gson.Gson

class ChuFaDanActivity : BaseViewModelActivity<ChuFaDanViewModel>() {
    var bamj: String? = null
    var jqbh: String? = null
    var code: String? = null
    var sex = "1"
    var zxfs = "1"
    var ajbh = ""
    var ywDictCode = ""
    var dataDTO: GetCfjdDetailResponse.DataDTO? = null
    var request: JqCfjdRequest? = null
    private val bind: ActivityChuFaDanBinding by binding();
    private var pvTimeBirth: TimePickerView? = null
    private var pvTimeChufa: TimePickerView? = null
    private var pvTimeSjsj: TimePickerView? = null
    var userInfo: HomeLoginResponse.Data.Data.User =
        DataHelper.getData(DataHelper.loginUserInfo) as HomeLoginResponse.Data.Data.User;


    private val getPoliceListInfo =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                listDTOList = it.data?.getParcelableArrayListExtra("policeListInfo")!!
                if (listDTOList.isNullOrEmpty()) {
                    bind.etPoliceName.setText("")
                    return@registerForActivityResult
                }
                var sb = StringBuffer();
                for (dto in listDTOList!!) {
                    if (!dto.choice) continue
                    sb.append("${dto.name}  ")
                }
                bind.etPoliceName.setText(sb.toString())
            }
        }
    private var listDTOList: ArrayList<GetUserListResponse.DataDTO.ListDTO>? = null

    private lateinit var userBean: HomeLoginResponse.Data.Data.User
    private lateinit var ywDictDialog: YwDictDialog
    override fun observe() {
        mViewModel.ywDictLiveData.observe(this, {
            if (it.httpResponseState != HttpResponseState.STATE_SUCCESS) return@observe
            ywDictDialog.listDTOList = it?.httpResponse?.data
            ywDictDialog.showDialog(supportFragmentManager, "ywDictDialog")
        })

        mViewModel.getAjbhLiveData.observe(this, {
            if (it.httpResponseState != HttpResponseState.STATE_SUCCESS) return@observe
            submit(it?.httpResponse?.ajbh!!)
        })

        mViewModel.jqCfjdLiveData.observe(this, {
            if (it.httpResponseState != HttpResponseState.STATE_SUCCESS) return@observe
            val printRquest = PrintRquest()
            printRquest.type = "chu_fa_dan"
            printRquest.content = (Gson().toJson(request))
            printRquest.printStateTopic = "PrintState/get/${DataHelper.imei}/${userInfo.pcard}"
            printRquest.code = code
            AndroidMqttClient.publish(
                topic = "Ret/print/Send/" + DataHelper.imei,
                msg = Gson().toJson(printRquest)
            )
        })

        mViewModel.getCfjdDetailLiveData.observe(this, {
            if (it.httpResponseState != HttpResponseState.STATE_SUCCESS) return@observe
            dataDTO = it?.httpResponse?.data
            if (dataDTO == null) return@observe
            bind.etNameCompany.setText(dataDTO?.xm)
            bind.etIdCard.setText(dataDTO?.sfzh)
            bind.etAge.setText(dataDTO?.nl.toString())
            bind.etCompanyLoc.setText(dataDTO?.xzz)
            bind.etEvidence.setText(dataDTO?.zj)
            bind.etPunishResult.setText(dataDTO?.cfjg)
            bind.etBank.setText(dataDTO?.jfyh)
            bind.etFuyiJigou.setText(dataDTO?.ssfyjg)
            bind.etFayuan.setText(dataDTO?.ssssfy)
            bind.etChafaLoc.setText(dataDTO?.cfdd)
            bind.etFindOutTime.setText(dataDTO?.cmsj)
            bind.etPoliceName.setText(dataDTO?.bamj);
            bind.etBcfr.setText(dataDTO?.bcfr);
            bind.etFddbr.setText(dataDTO?.fddbr);
            bind.etSjwpqd.setText(dataDTO?.sjwpqd);
            bind.tvDateOfBirth.text = dataDTO?.csrq;
            bind.tvChufaTime.text = dataDTO?.cfsj?.substring(0, 10);
            bind.tvSjsj.text = dataDTO?.sjsj;

            if (dataDTO?.xb == 1) {
                bind.rgSex.check(R.id.rbn_nan)
            } else {
                bind.rgSex.check(R.id.rbn_nv)
            }

            when (dataDTO?.zxfs) {
                1 -> {
                    bind.rgZx.check(R.id.rbn_zx_1)
                }
                2 -> {
                    bind.rgZx.check(R.id.rbn_zx_2)
                }
                3 -> {
                    bind.rgZx.check(R.id.rbn_zx_3)
                }
            }
        })

    }

    override fun initData() {
        bind.includeTitle.tvTitle.text = "处罚通知单"
        bind.includeTitle.ibnBack.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                finish()
            }
        })
        userBean = DataHelper.getData(DataHelper.loginUserInfo) as HomeLoginResponse.Data.Data.User

        bamj = intent.getStringExtra("bamj")

        jqbh = intent.getStringExtra("jqbh")!!
        code = intent.getStringExtra("gxdwdm")!!

        ywDictDialog = YwDictDialog(object : OnItemClickListenerYwDict {
            override fun onItemClick(bean: YwDictResponse.DataBean, pos: Int) {
                var name = bean.name;
                when (ywDictCode) {
                    "305004" -> {
                        bind.etFayuan.setText(name)
                    }
                    "305003" -> {
                        bind.etFuyiJigou.setText(name)
                    }
                    "305002" -> {
                        bind.etBank.setText(name)
                    }
                }
                ywDictDialog.dismiss()
            }
        })

        pvTimeBirth = initDataSelectChuFaDan1(activity, object : OnTimeSelectListener {
            override fun timeSelectListener(str: String) {
                bind.tvDateOfBirth.text = str
            }
        })

        pvTimeChufa = initDataSelectChuFaDan(activity, object : OnTimeSelectListener {
            override fun timeSelectListener(str: String) {
                bind.tvChufaTime.text = str
            }
        })

        pvTimeSjsj = initDataSelectChuFaDan(activity, object : OnTimeSelectListener {
            override fun timeSelectListener(str: String) {
                bind.tvSjsj.text = str
            }
        })

        mViewModel.getCfjdDetail(jqbh)

    }

    override fun viewOnClick() {

        ivInOrVi(bind.etNameCompany, bind.ivNameCompany)
        ivInOrVi(bind.etIdCard, bind.ivIdCard)
        ivInOrVi(bind.etAge, bind.ivAge)
        ivInOrVi(bind.etCompanyLoc, bind.ivCompanyLoc)
        ivInOrVi(bind.etEvidence, bind.ivEvidence)
        ivInOrVi(bind.etStatute, bind.ivStatute)
        ivInOrVi(bind.etPunishResult, bind.ivPunishResult)
        ivInOrVi(bind.etBank, bind.ivBank1)
        ivInOrVi(bind.etFuyiJigou, bind.ivFuyiJigou1)
        ivInOrVi(bind.etFayuan, bind.ivFayuan1)
        ivInOrVi(bind.etChafaLoc, bind.ivChafaLoc)
        ivInOrVi(bind.etPoliceName, bind.ivPoliceName)
        ivInOrVi(bind.etBcfr, bind.ivBcfr)
        ivInOrVi(bind.etFddbr, bind.ivFddbr)
        ivInOrVi(bind.etSjwpqd, bind.ivSjwpqd)
        ivInOrVi(bind.etFindOutTime, bind.ivFindOutTime)

        bind.etPoliceName.setText(bamj)

        bind.ivBank.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                ywDictCode = "305002"
                mViewModel.ywDict(ywDictCode)
            }
        })

        bind.ivFuyiJigou.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                ywDictCode = "305003"
                mViewModel.ywDict(ywDictCode)
            }
        })

        bind.ivFayuan.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                ywDictCode = "305004"
                mViewModel.ywDict(ywDictCode)

            }
        })

        bind.tvDateOfBirth.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                pvTimeBirth?.show()
            }

        })
        bind.tvChufaTime.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                pvTimeChufa?.show()
            }

        })
        bind.tvSjsj.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                pvTimeSjsj?.show()
            }
        })

        bind.rgSex.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                when (checkedId) {
                    R.id.rbn_nan -> {
                        sex = "1"
                    }
                    R.id.rbn_nv -> {
                        sex = "0"
                    }
                }
            }
        })

        bind.rgZx.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbn_zx_1 -> {
                    sex = "1"
                }
                R.id.rbn_zx_2 -> {
                    sex = "2"
                }
                R.id.rbn_zx_3 -> {
                    sex = "3"
                }
            }
        }

        bind.etNameCompany.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                bind.etBcfr.setText(s.toString())
                bind.etFddbr.setText(s.toString())
            }

        })

        bind.ivBamj.setOnClickListener(object : OnClickViewListener() {
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

        bind.etIdCard.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().length == 15) {
                    if (RegexUtils.isIDCard15(s.toString().trim())) {
                        bind.etIdCard.setText("身份证号:" + s.toString().trim())
                    }
                }

                if (s.toString().length == 18) {
                    if (RegexUtils.isIDCard18Exact(s.toString().trim())) {
                        bind.etIdCard.setText("身份证号:" + s.toString().trim());
                        var idCardInfoExtractor = IDCardInfoExtractor(
                            s.toString().trim()
                        );
                        bind.etAge.setText(idCardInfoExtractor.age.toString());
                        bind.tvDateOfBirth.text = getCsrq(idCardInfoExtractor.birthday);
                        if (idCardInfoExtractor.getGender().equals("男")) {
                            bind.rgSex.check(R.id.rbn_nan);
                        } else if (idCardInfoExtractor.getGender().equals("女")) {
                            bind.rgSex.check(R.id.rbn_nv);
                        }
                    }
                }
            }

        })

        bind.btnConfirm.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                mViewModel.getAjbh()
            }
        })
    }

    private fun ivInOrVi(et: EditText, iv: ImageView) {
        et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (TextUtils.isEmpty(s.toString())) {
                    iv.visibility = View.INVISIBLE
                } else {
                    iv.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }


    private fun submit(ajbh: String) {
        val xm: String = bind.etNameCompany.text.toString()
        val xb = sex
        val nl: String = bind.etAge.getText().toString()
        val csrq: String = bind.tvDateOfBirth.getText().toString()
        val xzz: String = bind.etCompanyLoc.getText().toString()
        val sfzh: String = bind.etIdCard.getText().toString()
        val cmsj: String = bind.etFindOutTime.getText().toString()
        val zj: String = bind.etEvidence.getText().toString()
        val gjfk: String = bind.etStatute.getText().toString()
        val cfjg: String = bind.etPunishResult.getText().toString()
        val jfyh: String = bind.etBank.getText().toString()
        val ssfyjg: String = bind.etFuyiJigou.getText().toString()
        val ssssfy: String = bind.etFayuan.getText().toString()
        val cfdd: String = bind.etChafaLoc.getText().toString()
        val bamj: String = bind.etPoliceName.getText().toString()
        val cfsj: String = bind.tvChufaTime.getText().toString()
        val fddbr: String = bind.etFddbr.getText().toString()
        val bcfr: String = bind.etBcfr.getText().toString()
        val sjsj: String = bind.tvSjsj.getText().toString()
        val sjwpqd: String = bind.etSjwpqd.getText().toString()

        request = JqCfjdRequest(
            jqbh = jqbh,
            xm = xm,
            xb = xb,
            nl = if (TextUtils.isEmpty(nl)) "0" else nl,
            age = nl,
            csrq = csrq,
            xzz = xzz,
            sfzh = sfzh,
            cmsj = cmsj,
            zj = zj,
            gjfk = gjfk,
            cfjg = cfjg,
            zxfs = zxfs,
            jfyh = jfyh,
            ssfyjg = ssfyjg,
            ssssfy = ssssfy,
            cfdd = cfdd,
            bamj = bamj,
            cfsj = cfsj,
            fddbr = fddbr,
            bcfr = bcfr,
            ajbh = ajbh,
            sjsj = sjsj,
            sjwpqd = sjwpqd
        );

        if (dataDTO == null) {
            mViewModel.jqCfjd(request!!)
        } else {
            mViewModel.cfjdEdit(request!!)
        }
    }
}
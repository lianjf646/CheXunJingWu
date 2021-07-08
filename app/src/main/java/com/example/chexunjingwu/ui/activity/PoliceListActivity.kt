package com.example.chexunjingwu.ui.activity

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dylanc.viewbinding.binding
import com.example.chexunjingwu.base.BaseViewModelActivity
import com.example.chexunjingwu.databinding.ActivityPoliceListBinding
import com.example.chexunjingwu.http.response.GetUserListResponse
import com.example.chexunjingwu.tools.OnClickViewListener
import com.example.chexunjingwu.tools.OnItemClickListener
import com.example.chexunjingwu.ui.adapter.GetUserListAdapter
import com.example.chexunjingwu.viewmodel.PoliceListViewModel

class PoliceListActivity : BaseViewModelActivity<PoliceListViewModel>() {

    private val bind: ActivityPoliceListBinding by binding();
    private lateinit var adapter: GetUserListAdapter
    private lateinit var dtoList: ArrayList<GetUserListResponse.DataDTO.ListDTO>
    private var pcardList: ArrayList<String>? = null
    override fun observe() {
        mViewModel.getUserListLiveData.observe(this, {
            dtoList = it?.httpResponse?.data?.list as ArrayList<GetUserListResponse.DataDTO.ListDTO>
            if (!pcardList.isNullOrEmpty()) {
                for (dto in dtoList) {
                    for (pcard in pcardList!!) {
                        if (pcard == dto.pcard) {
                            dto.choice = true
                            break
                        }
                    }
                }
            }

            adapter.submitList(dtoList)
        })
    }

    override fun initData() {
        //        DataHolder.getInstance().removeKey(DataHolder.GETUSERLISTRESPONSE_RESULTBEAN_DATADTO_LISTDTO);
        bind.includeTitle.tvTitle.text = "处警警员"
        bind.includeTitle.ibnBack.setOnClickListener { finish() }
        adapter = GetUserListAdapter(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                var b = adapter.getItem(position).choice
                dtoList[position].choice = !b
                adapter.submitList(dtoList)
            }
        });
        val code = intent.getStringExtra("code")

        pcardList = intent?.getStringArrayListExtra("nameList")

        bind.recyclerView.layoutManager = LinearLayoutManager(activity);
        var decoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL);
        bind.recyclerView.addItemDecoration(decoration)
        bind.recyclerView.adapter = adapter

        mViewModel.getUserList(code!!)


    }

    override fun viewOnClick() {

        bind.btnSub.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                var policeListInfo = ArrayList<GetUserListResponse.DataDTO.ListDTO>();
                for (dtoBean in dtoList) {
                    if (dtoBean.choice) {
                        policeListInfo.add(dtoBean)
                    }
                }
                var intent = Intent();
                intent.putExtra("policeListInfo", policeListInfo)
                setResult(RESULT_OK, intent)
                finish()
            }
        })
    }
}
package com.hylink.chexunjingwu.ui.activity

import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dylanc.viewbinding.binding
import com.hylink.chexunjingwu.base.BaseViewModelActivity
import com.hylink.chexunjingwu.databinding.ActivityMineEarlyWarningBinding
import com.hylink.chexunjingwu.http.api.HttpResponseState
import com.hylink.chexunjingwu.http.request.AlarmListRequest
import com.hylink.chexunjingwu.http.response.AlarmListResponse
import com.hylink.chexunjingwu.tools.OnClickViewListener
import com.hylink.chexunjingwu.ui.adapter.MineEarlyWarningAdapter
import com.hylink.chexunjingwu.viewmodel.MineEarlyWarningViewModel

class MineEarlyWarningActivity : BaseViewModelActivity<MineEarlyWarningViewModel>() {


    private val bind: ActivityMineEarlyWarningBinding by binding();
    private lateinit var imei: String
    private var currentPage = 1
    private val showCount = 10
    private var adaper = MineEarlyWarningAdapter()
    var beanList = mutableListOf<AlarmListResponse.ListBean>();
    override fun initData() {
        bind.includeTitle.tvTitle.text = "我的预警"
        bind.includeTitle.ibnBack.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                finish()
            }
        })
        imei = intent.getStringExtra("imei")!!
        bind.recycler.layoutManager = LinearLayoutManager(activity);
        bind.recycler.adapter = adaper
        bind.recycler.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
        alarmList()
    }


    override fun viewOnClick() {

        bind.refreshLayout.setOnRefreshListener {
            currentPage = 1
            alarmList()
        }

        bind.refreshLayout.setOnLoadMoreListener {
            currentPage++
            alarmList()
        }

    }

    override fun observe() {
        mViewModel.alarmListLiveData.observe(this, {
            bind.refreshLayout.finishRefresh() //结束刷新
            bind.refreshLayout.finishLoadMore() //结束加载
            if (it.httpResponseState == HttpResponseState.STATE_SUCCESS) {
                if (currentPage == 1) {
                    beanList.clear()
                }
                if (!it?.httpResponse?.list.isNullOrEmpty()) {
                    beanList.addAll(it?.httpResponse?.list!!)
                }
                adaper.submitList(beanList)
                bind.tvNotData.visibility = if (adaper.itemCount == 0) View.VISIBLE else View.GONE
                var size = it?.httpResponse?.list?.size
                bind.refreshLayout.setEnableLoadMore(size!! >= 10)
            }
        })
    }


    fun alarmList() {
        var request = AlarmListRequest()
        var pdBean = AlarmListRequest.PdBean(imei);
        request.currentPage = currentPage;
        request.showCount = showCount
        request.pd = pdBean
        mViewModel.alarmList(request)
    }

}



package com.example.chexunjingwu.ui.activity

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dylanc.viewbinding.binding
import com.example.chexunjingwu.R
import com.example.chexunjingwu.base.BaseViewModelActivity
import com.example.chexunjingwu.databinding.ActivityJieChuJingBinding
import com.example.chexunjingwu.http.api.HttpResponseState
import com.example.chexunjingwu.http.response.GetJqListResponse
import com.example.chexunjingwu.tools.OnClickViewListener
import com.example.chexunjingwu.tools.OnItemClickListener
import com.example.chexunjingwu.ui.adapter.GetJqListAdapter
import com.example.chexunjingwu.ui.adapter.JqJqfkzcListAdapter
import com.example.chexunjingwu.viewmodel.JieChuJingViewModel

class JieChuJingActivity : BaseViewModelActivity<JieChuJingViewModel>() {
    private val bind: ActivityJieChuJingBinding by binding();
    private lateinit var getJqListAdapter: GetJqListAdapter
    private lateinit var jqJqfkzcListAdapter: JqJqfkzcListAdapter
    private var currentPage: Int = 1;
    var beanLists: ArrayList<GetJqListResponse.ListBean> = ArrayList();

    override fun observe() {
        mViewModel.getJqListLiveData.observe(this, {
            if (it?.httpResponseState != HttpResponseState.STATE_SUCCESS) return@observe
            var beanList = it?.httpResponse?.list;
            if (!beanList.isNullOrEmpty()) {
                if (currentPage == 1) {
                    beanLists.clear()
                    beanLists.addAll(beanList)
                    getJqListAdapter.submitList(beanLists)
                } else {
                    beanLists.addAll(beanList)
                    getJqListAdapter.submitList(beanLists)
                }
            }
            bind.srl.finishRefresh() //结束刷新
            bind.srl.finishLoadMore() //结束加载
        })

        mViewModel.jqJqfkzcListResponseLiveData.observe(this, {

            if (it?.httpResponseState != HttpResponseState.STATE_SUCCESS) return@observe
            bind.srl.finishRefresh() //结束刷新
            bind.srl.finishLoadMore() //结束加载

            jqJqfkzcListAdapter.submitList(it?.httpResponse?.list)
            if (bind.recycler2.visibility == View.VISIBLE) {
                bind.tvNotData.visibility =
                    if (jqJqfkzcListAdapter.itemCount == 0) View.VISIBLE else View.GONE
            }
        })
    }

    override fun initData() {
        bind.includeTitle.tvTitle.text = "接处警"
        bind.includeTitle.ibnBack.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                finish()
            }
        })

        bind.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbn_1 -> {
                    bind.recycler1.visibility = View.VISIBLE
                    bind.recycler2.visibility = View.GONE
                    mViewModel.getJqList(currentPage)
                }
                R.id.rbn_2 -> {
                    bind.recycler1.setVisibility(View.GONE)
                    bind.recycler2.setVisibility(View.VISIBLE)
                    mViewModel.jqJqfkzcList()
                }
            }
        }

        bind.srl.setEnableRefresh(true) //是否启用下拉刷新功能
        bind.srl.setEnableLoadMore(false) //是否启用上拉加载功能
        bind.srl.setDisableContentWhenRefresh(false) //是否在刷新的时候禁止列表的操作
        bind.srl.setDisableContentWhenLoading(false) //是否在加载的时候禁止列表的操作

        getJqListAdapter = GetJqListAdapter(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
//                showNormal(getJqListAdapter?.getItem(position).jqbh)

                val bean: GetJqListResponse.ListBean = getJqListAdapter?.getItem(position)
                val intent = Intent()
                intent.setClass(
                    this@JieChuJingActivity,
                    PoliceInforartionDetailActivity::class.java
                )
                intent.putExtra("getjqlistresponse.resultbean.listbean", bean)
                startActivity(intent)
            }
        })

        bind.recycler1.layoutManager = LinearLayoutManager(activity);
        var decoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL);
        bind.recycler1.addItemDecoration(decoration)
        bind.recycler1.adapter = getJqListAdapter;

        jqJqfkzcListAdapter =JqJqfkzcListAdapter(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(
                    this@JieChuJingActivity,
                    JingQingFeedBackActivity::class.java
                )
                intent.putExtra("byId",jqJqfkzcListAdapter.currentList[position]?.jqbh)
                startActivity(intent)
            }
        });
        bind.recycler2.layoutManager = LinearLayoutManager(activity);
        bind.recycler2.addItemDecoration(decoration)
        bind.recycler2.adapter = jqJqfkzcListAdapter;
    }

    override fun viewOnClick() {
        bind.srl.setOnRefreshListener {
            currentPage = 1
            mViewModel.getJqList(currentPage)
            mViewModel.jqJqfkzcList()
        }

        bind.srl.setOnLoadMoreListener {
//            currentPage++
//            mViewModel.getJqList(currentPage)

        }
    }

    override fun onResume() {
        super.onResume()
        mViewModel.getJqList(currentPage)
        mViewModel.jqJqfkzcList()
    }
}
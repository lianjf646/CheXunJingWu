package com.hylink.chexunjingwu.ui.activity

import android.content.Intent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dylanc.viewbinding.binding
import com.hylink.chexunjingwu.base.BaseViewModelActivity
import com.hylink.chexunjingwu.databinding.ActivityInstructionsBinding
import com.hylink.chexunjingwu.http.api.HttpResponseState
import com.hylink.chexunjingwu.http.request.GetJqTztgListRequest
import com.hylink.chexunjingwu.http.response.GetJqTztgListResponse
import com.hylink.chexunjingwu.http.response.HomeLoginResponse
import com.hylink.chexunjingwu.http.response.SignStatusResponse
import com.hylink.chexunjingwu.tools.DataHelper
import com.hylink.chexunjingwu.tools.OnClickViewListener
import com.hylink.chexunjingwu.tools.OnItemClickListener
import com.hylink.chexunjingwu.tools.showNormal
import com.hylink.chexunjingwu.ui.adapter.InstructionsAdapter
import com.hylink.chexunjingwu.viewmodel.InstructionsViewModel

class InstructionsActivity : BaseViewModelActivity<InstructionsViewModel>() {
    val bind: ActivityInstructionsBinding by binding()
    var userBean: HomeLoginResponse.Data.Data.User =
        DataHelper.getData(DataHelper.loginUserInfo) as HomeLoginResponse.Data.Data.User
    var loginState: SignStatusResponse.DataBean =
        DataHelper.getData(DataHelper.carInfo) as SignStatusResponse.DataBean
    private var adapter: InstructionsAdapter? = null
    private var caron = ""
    private var mjjh = ""
    private var mjdwdm = ""
    private var search = ""
    private var currentPage = 1
    private val beanList = ArrayList<GetJqTztgListResponse.ListBean>();

    override fun observe() {
        mViewModel.getJqTztgListLiveData.observe(this, {

            bind.srl.finishRefresh();//结束刷新
            bind.srl.finishLoadMore();//结束加载
            if (it?.httpResponseState != HttpResponseState.STATE_SUCCESS) return@observe
            if (currentPage == 1) {
                beanList.clear()
                if (it?.httpResponse?.list?.isNullOrEmpty() == true) {
                    showNormal("暂无数据")
                    bind.tvNotData.visibility =
                        if (adapter?.currentList?.isNullOrEmpty() == true) View.VISIBLE else View.GONE
                }
            }
            beanList.addAll(it?.httpResponse?.list!!);
            adapter?.submitList(beanList)
            bind.srl.setEnableLoadMore(it?.httpResponse?.list?.size!! >= 10);//是否启用上拉加载功能
        })
    }

    override fun initData() {
        bind.includeTitle.tvTitle.text = "指令通知"
        bind.includeTitle.ibnBack.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                finish()
            }
        })

        caron = loginState.carNo;
        mjjh = userBean?.pcard
        mjdwdm = userBean?.group?.code
        adapter = InstructionsAdapter(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                DataHelper.putData(
                    DataHelper.GETJQTZTGLISTRESPONSE_RESULTBEAN_LISTBEAN,
                    adapter!!.currentList[position]
                )
                val intent =
                    Intent(this@InstructionsActivity, InstructionsDetailActivity::class.java)
                startActivity(intent)
            }
        })
        bind.lv.layoutManager = LinearLayoutManager(activity)
        bind.lv.adapter = adapter
        var decoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL);
        bind.lv.addItemDecoration(decoration)


    }

    override fun viewOnClick() {
        bind.srl.setOnRefreshListener {
            currentPage = 1
            getJqTztgList()
        }
        bind.srl.setOnLoadMoreListener {
            currentPage++
            getJqTztgList()
        }
        bind.srl.setEnableRefresh(true) //是否启用下拉刷新功能
        bind.srl.setEnableLoadMore(true) //是否启用上拉加载功能
        bind.srl.setDisableContentWhenRefresh(false) //是否在刷新的时候禁止列表的操作
        bind.srl.setDisableContentWhenLoading(false) //是否在加载的时候禁止列表的操作
        bind.etSearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                //                    //关闭软键盘
                //                    YUtils.closeSoftKeyboard();
                //                    //do something
                //                    //doSearch();
                //                    ToastUtil.showToast("点击了软键盘的搜索按钮");
                currentPage = 1
                search = bind.etSearch.text.toString()
                getJqTztgList()
                return@OnEditorActionListener true
            }
            false
        })
    }

    override fun onResume() {
        super.onResume()
        currentPage = 1
        getJqTztgList()
    }

    private fun getJqTztgList() {
        search = bind.etSearch.text.toString()
        val request = GetJqTztgListRequest()
        val pd: GetJqTztgListRequest.Pd = GetJqTztgListRequest.Pd()
        pd.carno = caron
        pd.imei = DataHelper.imei
        pd.search = search
        pd.mjdwdm = mjdwdm
        pd.mjjh = mjjh
        request.pd = pd
        request.currentPage = currentPage
        request.showCount = 10
        mViewModel.getJqTztgList(request)
    }
}



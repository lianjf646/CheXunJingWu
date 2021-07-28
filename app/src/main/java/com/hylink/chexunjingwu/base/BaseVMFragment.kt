package com.hylink.chexunjingwu.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

abstract class BaseVMFragment<VM : BaseViewModel>(@LayoutRes contentLayoutId: Int) :
    BaseFragment(contentLayoutId) {


    //是否需要懒加载
    private var lazyFlag = true

    protected lateinit var mViewModel: VM

    private lateinit var mActivity: AppCompatActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = createViewModel()
        observe()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        if (savedInstanceState == null) initData()
    }


    open fun initData() {}

    private fun createViewModel(): VM {
        return ViewModelProvider(this)[getVMCls(this)]
    }

    private fun <VM> getVMCls(cls: Any): VM {
        return (cls.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as VM
    }

    protected abstract fun observe() // LiveData发生变化通知界面改变
}
package com.example.chexunjingwu.base

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

abstract class BaseViewModelActivity<VM : BaseViewModel> : BaseActivity() {
    protected open lateinit var mViewModel: VM
    override fun onCreate(savedInstanceState: Bundle?) {
        initViewModel()
        super.onCreate(savedInstanceState)
        observe()
    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider(this)[getVMCls(this)]


    }

    private fun <VM> getVMCls(cls: Any): VM {
        return (cls.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as VM
    }


    protected abstract fun observe() // LiveData发生变化通知界面改变

}
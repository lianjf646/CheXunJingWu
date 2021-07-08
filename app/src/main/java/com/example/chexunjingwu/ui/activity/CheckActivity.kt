package com.example.chexunjingwu.ui.activity

import android.os.Bundle
import android.view.View
import com.dylanc.viewbinding.binding
import com.example.chexunjingwu.R
import com.example.chexunjingwu.base.BaseViewModelActivity
import com.example.chexunjingwu.databinding.ActivityCheckBinding
import com.example.chexunjingwu.ui.fragment.CheckCarFragment
import com.example.chexunjingwu.ui.fragment.CheckPersonFragment
import com.example.chexunjingwu.viewmodel.CheckViewModel

class CheckActivity : BaseViewModelActivity<CheckViewModel>() {

    var checkPersonFragment: CheckPersonFragment = CheckPersonFragment()
    var checkCarFragment: CheckCarFragment = CheckCarFragment()
    val bind: ActivityCheckBinding by binding()
    override fun observe() {

    }

    override fun initData() {
        bind.includeTitle.tvTitle.text = "核查"
        bind.includeTitle.ibnBack.setOnClickListener(View.OnClickListener { finish() })

        supportFragmentManager.beginTransaction()
            .add(R.id.frame_layout, checkPersonFragment)
            .add(R.id.frame_layout, checkCarFragment)
            .hide(checkCarFragment)
            .show(checkPersonFragment)
            .commitAllowingStateLoss()

        bind.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbn_video -> {
                    supportFragmentManager
                        .beginTransaction()
                        .show(checkPersonFragment)
                        .hide(checkCarFragment)
                        .commitAllowingStateLoss()
                }

                R.id.rbn_photo -> {
                    supportFragmentManager
                        .beginTransaction()
                        .show(checkCarFragment)
                        .hide(checkPersonFragment)
                        .commitAllowingStateLoss()
                }
            }
        }
    }

    fun clickRadio(idCard: String?) {
        bind.radioGroup.check(R.id.rbn_video)
        val bundle = Bundle()
        bundle.putString("idCard", idCard)
        checkPersonFragment.arguments = bundle
    }

    override fun viewOnClick() {

    }
}
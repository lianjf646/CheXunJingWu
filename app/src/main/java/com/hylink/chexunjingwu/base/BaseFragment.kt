package com.hylink.chexunjingwu.base

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    lateinit var activity: AppCompatActivity;
    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as AppCompatActivity;
    }
}
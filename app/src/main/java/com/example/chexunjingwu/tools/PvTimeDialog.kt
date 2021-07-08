package com.example.chexunjingwu.tools

import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.view.TimePickerView
import com.example.chexunjingwu.R
import java.text.SimpleDateFormat
import java.util.*

fun initDataSelectDetail(
    time: String,
    context: AppCompatActivity,
    tvAjfssj: TextView
): TimePickerView {
    //Dialog 模式下，在底部弹出
    var time = time.replace("-", "/")
    val selectedDate = Calendar.getInstance()
    val formatter = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
    val date = formatter.parse(time)
    selectedDate.time = date
    var pvTime = TimePickerBuilder(context) { date1: Date?, v: View? ->
        tvAjfssj.text = getTime(date1!!)
        Log.i("pvTime", "onTimeSelect")
    }
        .setType(booleanArrayOf(true, true, true, true, true, true))
        .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
        .setItemVisibleCount(7) //若设置偶数，实际值会加1（比如设置6，则最大可见条目为7）
        .setCancelText("取消") //取消按钮文字
        .setSubmitText("确认") //确认按钮文字
        .setBgColor(Colors.COLOR_0C0D3A)
        .setOutSideColor(Colors.COLOR_4CWHITE)
        .setTitleBgColor(Colors.COLOR_090A37)
        .setTextColorCenter(Colors.WHITE)
        .setTitleColor(Colors.WHITE)
        .setSubmitColor(Colors.COLOR_00CCFF) //确定按钮文字颜色
        .setCancelColor(Colors.COLOR_00CCFF) //取消按钮文字颜色
        .setTitleText("请选择时间") //标题文字
        .isDialog(true)
        .setLineSpacingMultiplier(2.0f)
        .isAlphaGradient(true)
        .setLabel("年", "月", "日", "时", "分", "秒") //默认设置为年月日时分秒
        .setDate(selectedDate)
        .build()
    val mDialog = pvTime?.dialog
    if (mDialog != null) {
        val params = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            Gravity.BOTTOM
        )
        val windowManager = context.windowManager
        val display = windowManager.defaultDisplay
        val lp = mDialog.window!!.attributes
        lp.width = display.width //设置宽度
        mDialog.window!!.attributes = lp
        params.leftMargin = 0
        params.rightMargin = 0
        pvTime?.getDialogContainerLayout()?.layoutParams = params
        val dialogWindow = mDialog.window
        if (dialogWindow != null) {
            dialogWindow.setWindowAnimations(R.style.picker_view_slide_anim) //修改动画样式
            dialogWindow.setGravity(Gravity.BOTTOM) //改成Bottom,底部显示
            //                dialogWindow.setDimAmount(0.3f);
        }
    }

    return pvTime
}

fun getTime(date: Date): String? { //可根据需要自行截取数据显示
    val format = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
    return format.format(date)
}

//时间转化
fun getTimeChuo(time: String): String? {
    var time = time
    time = time.replace("/", "-")
    return time
}

//    获取当前日期
fun getDateNow(): String? = SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Date())

fun getStrings(str: String?): String? = if (str.isNullOrEmpty()) "" else str

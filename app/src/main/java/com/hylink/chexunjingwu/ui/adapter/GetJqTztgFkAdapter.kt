package com.hylink.chexunjingwu.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hylink.chexunjingwu.base.BaseDataAdapter
import com.hylink.chexunjingwu.databinding.ItemInstrucitonsFeedbackBinding
import com.hylink.chexunjingwu.http.response.GetJqTztgFkListResponse

class GetJqTztgFkAdapter :
    BaseDataAdapter<ItemInstrucitonsFeedbackBinding, GetJqTztgFkListResponse.ListDTO>() {
    override fun getBind(parent: ViewGroup?): ItemInstrucitonsFeedbackBinding =
        ItemInstrucitonsFeedbackBinding.inflate(
            LayoutInflater.from(parent?.context), parent, false
        )

    override fun showView(
        bind: ItemInstrucitonsFeedbackBinding,
        position: Int,
        data: GetJqTztgFkListResponse.ListDTO
    ) {

        bind.tvTime.text = "反馈时间:${data.feedback_time}"
        bind.tvContent.text = "反馈内容:${data?.feedback_message?.fknr}"
        bind.tvPos.text = (position + 1).toString()
    }
}
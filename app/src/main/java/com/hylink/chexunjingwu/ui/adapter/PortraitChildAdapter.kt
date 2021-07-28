package com.hylink.chexunjingwu.ui.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dylanc.viewbinding.BindingViewHolder
import com.hylink.chexunjingwu.R
import com.hylink.chexunjingwu.databinding.ItemPotraitchildBinding
import com.hylink.chexunjingwu.http.response.AlarmMessage0
import com.hylink.chexunjingwu.tools.OnClickViewListener
import com.hylink.chexunjingwu.tools.glideLoad
import com.hylink.chexunjingwu.ui.activity.PersonnelInfoActivity

class PortraitChildAdapter :
    ListAdapter<AlarmMessage0.VerificationPortraitDataListBean, RecyclerView.ViewHolder>(
        DiffCallback()
    ) {
    var context: Context? = null
    var byId: String? = null

    class DiffCallback : DiffUtil.ItemCallback<AlarmMessage0.VerificationPortraitDataListBean>() {
        override fun areItemsTheSame(
            oldItem: AlarmMessage0.VerificationPortraitDataListBean,
            newItem: AlarmMessage0.VerificationPortraitDataListBean
        ) = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: AlarmMessage0.VerificationPortraitDataListBean,
            newItem: AlarmMessage0.VerificationPortraitDataListBean
        ) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return BindingViewHolder<ItemPotraitchildBinding>(parent)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as BindingViewHolder<ItemPotraitchildBinding>
        var bean = currentList[position]
        glideLoad(bean.path!!, holder.binding.ivHeard1)
        holder.binding.tvBeSimilar.text = "${bean.score}%"
        holder.binding.tvBeSimilar.setBackgroundColor(Color.parseColor("#FF0101"))
        holder.binding.linear1.removeAllViews();

        var comparison_exception = bean?.comparisonData?.comparison_exception
        for (s in bean.comparisonData?.Tags!!) {
            val textView = TextView(context)
            val lp = LinearLayout.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            lp.setMargins(0, 0, 0, 5) // 设置间距

            textView.layoutParams = lp
            textView.setText(s)
            textView.setTextColor(Color.parseColor("#ffffff"))
            textView.minEms = 3
            if (comparison_exception == 1) {
                textView.setBackgroundColor(Color.parseColor("#FF0101"))
            } else {
                textView.setBackgroundColor(Color.parseColor("#0D9F52"))
            }
            textView.gravity = Gravity.CENTER
            holder.binding.linear1.addView(textView)
        }

        if (comparison_exception == 1) {
            holder.binding.ivHeard1.setBackgroundResource(R.drawable.bg_red_line)
            holder.binding.tvBeSimilar.setBackgroundColor(Color.parseColor("#F30404"))
        } else {
            holder.binding.ivHeard1.setBackgroundResource(R.drawable.bg_blue_line)
            holder.binding.tvBeSimilar.setBackgroundColor(Color.parseColor("#3C4297"))
        }

        holder.binding.ivHeard1.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                if (byId.isNullOrEmpty()) return
                var intent = Intent(context, PersonnelInfoActivity::class.java)
                intent.putExtra("byId", byId);
                intent.putExtra("pos", position);
                context?.startActivity(intent);
            }

        })
    }
}
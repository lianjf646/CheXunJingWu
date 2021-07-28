package com.hylink.chexunjingwu.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hylink.chexunjingwu.R
import com.hylink.chexunjingwu.http.response.JqDictResponse
import com.hylink.chexunjingwu.tools.OnClickViewListener
import com.hylink.chexunjingwu.tools.OnItemClickListenerT
import com.hylink.chexunjingwu.ui.adapter.JqTypeListAdapter

class JqDictDialog(onItemClickListener: OnItemClickListenerT ) : DialogFragment() {


    private var jqTypeListAdapter: JqTypeListAdapter? = null

    var listDTOList: List<JqDictResponse.ListBean>? = null
    var dictid: String? = null
    private lateinit var recyclerView: RecyclerView;
    private lateinit var iv_colse: ImageView;


    init {
        jqTypeListAdapter = JqTypeListAdapter(onItemClickListener);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return inflater.inflate(R.layout.dialog_jqdict, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.fragment_dialog)
    }


    override fun onStart() {
        super.onStart()
        dialog!!.window!!
            .setLayout(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
            )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        jqTypeListAdapter?.submitList(listDTOList)
        recyclerView = view.findViewById(R.id.recyclerView)
        var decoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(decoration)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = jqTypeListAdapter


        iv_colse = view.findViewById(R.id.iv_colse);
        iv_colse.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                dismiss()
            }
        })

    }

    fun showDialog(manager: FragmentManager, tag: String?) {
        manager.beginTransaction().remove(this).commit()
        super.show(manager, tag)
    }

}
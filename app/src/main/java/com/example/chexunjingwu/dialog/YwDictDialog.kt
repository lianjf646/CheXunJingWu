package com.example.chexunjingwu.dialog

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
import com.example.chexunjingwu.R
import com.example.chexunjingwu.http.response.YwDictResponse
import com.example.chexunjingwu.tools.OnClickViewListener
import com.example.chexunjingwu.tools.OnItemClickListenerYwDict
import com.example.chexunjingwu.ui.adapter.YwDictListAdapter

class YwDictDialog(onItemClickListener: OnItemClickListenerYwDict) : DialogFragment() {


    private var ywDictListAdapter: YwDictListAdapter? = null

    var listDTOList: List<YwDictResponse.DataBean>? = null
    private lateinit var recyclerView: RecyclerView;
    private lateinit var ivColse: ImageView;


    init {
        ywDictListAdapter = YwDictListAdapter(onItemClickListener);
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

        ywDictListAdapter?.submitList(listDTOList)
        recyclerView = view.findViewById(R.id.recyclerView)
        var decoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(decoration)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = ywDictListAdapter


        ivColse = view.findViewById(R.id.iv_colse);
        ivColse.setOnClickListener(object : OnClickViewListener() {
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
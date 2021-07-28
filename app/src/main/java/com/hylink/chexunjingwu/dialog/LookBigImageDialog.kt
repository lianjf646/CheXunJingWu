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
import com.hylink.chexunjingwu.R
import com.hylink.chexunjingwu.tools.glideLoad

class LookBigImageDialog : DialogFragment() {

    private lateinit var imageView: ImageView
    private var path: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.fragment_dialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return inflater.inflate(R.layout.dialog_look_big_image, container, false)
    }

    override fun onStart() {
        super.onStart()
        dialog!!.window!!.setLayout(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageView = view.findViewById(R.id.iv)


    }

    override fun onResume() {
        super.onResume()
        glideLoad(path!!, imageView)

    }

    fun setPath(path: String?) {
        this.path = path
    }

    fun showDialog(manager: FragmentManager, tag: String?) {
        manager.beginTransaction().remove(this).commit()
        super.show(manager, tag)
    }

}
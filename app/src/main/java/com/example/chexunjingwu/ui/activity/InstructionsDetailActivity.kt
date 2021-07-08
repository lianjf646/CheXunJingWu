package com.example.chexunjingwu.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Environment
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.dylanc.viewbinding.binding
import com.example.chexunjingwu.R
import com.example.chexunjingwu.base.BaseViewModelActivity
import com.example.chexunjingwu.databinding.ActivityInstructionsDetailBinding
import com.example.chexunjingwu.dialog.LookBigImageDialog
import com.example.chexunjingwu.http.api.HttpResponseState
import com.example.chexunjingwu.http.request.GetJqTztgDetailRequest
import com.example.chexunjingwu.http.request.GetJqTztgFkListRequest
import com.example.chexunjingwu.http.request.InstructionMessageReadRequest
import com.example.chexunjingwu.http.response.GetJqTztgListResponse
import com.example.chexunjingwu.http.response.HomeLoginResponse
import com.example.chexunjingwu.tools.*
import com.example.chexunjingwu.ui.adapter.AudioAdapter
import com.example.chexunjingwu.ui.adapter.GetJqTztgFkAdapter
import com.example.chexunjingwu.ui.adapter.PictureAdapter
import com.example.chexunjingwu.ui.adapter.VideoInstructionsAdapter
import com.example.chexunjingwu.viewmodel.InstructionsDetailViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class InstructionsDetailActivity : BaseViewModelActivity<InstructionsDetailViewModel>() {
    val bind: ActivityInstructionsDetailBinding by binding()
    lateinit var lookBigImageDialog: LookBigImageDialog

    val pictureAdapter = PictureAdapter()
    val videoInstructionsAdapter = VideoInstructionsAdapter()
    val audioAdapter = AudioAdapter()
    val getJqTztgFkAdapter = GetJqTztgFkAdapter()

    var bean: GetJqTztgListResponse.ListBean =
        DataHelper.getData(DataHelper.GETJQTZTGLISTRESPONSE_RESULTBEAN_LISTBEAN) as GetJqTztgListResponse.ListBean;

    //
    private var posStart = -1
    private var isAudioLoading = false

    var userBean: HomeLoginResponse.Data.Data.User =
        DataHelper.getData(DataHelper.loginUserInfo) as HomeLoginResponse.Data.Data.User

    private val feedbackState =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                finish()
            }
        }

    override fun observe() {
        mViewModel.instructionMessageReadResponse.observe(this, {
            if (it?.httpResponseState != HttpResponseState.STATE_SUCCESS) return@observe
        })

        mViewModel.getJqTztgDetailResponseResponse.observe(this, {
            if (it?.httpResponseState != HttpResponseState.STATE_SUCCESS) return@observe
            showNormal("确认成功")
            finish()
        })

        mViewModel.getJqTztgFkListResponse.observe(this, {
            if (it?.httpResponseState != HttpResponseState.STATE_SUCCESS) return@observe
            bind.llFeedBack.visibility =
                if (it?.httpResponse?.list.isNullOrEmpty()) View.GONE else View.VISIBLE
            getJqTztgFkAdapter.dataList = it?.httpResponse?.list
            getJqTztgFkAdapter.notifyDataSetChanged()

        })
    }

    override fun initData() {
        bind.includeTitle.tvTitle.text = "指令详情"
        bind.includeTitle.ibnBack.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                finish()
            }
        })

        when (bean.level) {
            1 -> {
                bind.tvZllx.text = "普通指令"
                bind.tvZllx.setTextColor(Color.BLUE)
            }
            2 -> {
                bind.tvZllx.text = "红色指令"
                bind.tvZllx.setTextColor(Color.RED)
            }
            else -> {
                bind.tvZllx.text = "普通指令"
                bind.tvZllx.setTextColor(Color.BLUE)
            }
        }

        if (!TextUtils.isEmpty(bean.feedback_status)) {
            when (bean.feedback_status) {
                "0" -> {
                    bind.tvState.text = "未确认"
                    bind.tvState.setTextColor(activity.resources.getColor(R.color.red))
                    bind.tvState.setBackgroundResource(R.drawable.style_red)
                    bind.btnConfirm.text = "确认指令"
                }
                "1" -> {
                    bind.tvState.text = "已确认"
                    bind.tvState.setTextColor(activity.resources.getColor(R.color.green))
                    bind.tvState.setBackgroundResource(R.drawable.style_green)
                    bind.btnConfirm.text = "反馈指令"
                }
                "2" -> {
                    bind.tvState.text = "已反馈"
                    bind.tvState.setTextColor(activity.resources.getColor(R.color.color_0FA8CE))
                    bind.tvState.setBackgroundResource(R.drawable.style_lan)
                    bind.btnConfirm.text = "反馈指令"
                }
            }
        }
//
        bind.tvFbnr.text = bean.nr;
        bind.tvFbsj.text = bean.fbsj;
        bind.tvFbdw.text = bean.fbdwmc;
        bind.tvFbry.text = bean.fbrxm;

        lookBigImageDialog = LookBigImageDialog()
        VoicePlayer.onPreparedListener = object : OnPreparedListener {
            override fun onPrepared() {

            }

            override fun onCompletion() {
                posStart = -1;
                audioAdapter.posStart = posStart
                audioAdapter.notifyDataSetChanged();
            }

        }

        bind.llYuyin.visibility =
            if (bean.audio_message.isNullOrEmpty()) View.GONE else View.VISIBLE
        bind.lvYuyin.adapter = audioAdapter
        audioAdapter.dataList = bean.audio_message
        audioAdapter.notifyDataSetChanged()

        bind.llPicture.visibility =
            if (bean?.attachment.isNullOrEmpty()) View.GONE else View.VISIBLE
        bind.gvPicture.adapter = pictureAdapter;
        pictureAdapter.dataList = bean?.attachment!!
        pictureAdapter.notifyDataSetChanged()

        bind.llVideo.visibility =
            if (bean?.video_message!!.isNullOrEmpty()) View.GONE else View.VISIBLE
        bind.gvVideo.adapter = videoInstructionsAdapter
        videoInstructionsAdapter.dataList = bean?.video_message!!
        videoInstructionsAdapter.notifyDataSetChanged()

        bind.lvFeedback.adapter = getJqTztgFkAdapter

        var request = GetJqTztgFkListRequest(feedback_imei = DataHelper.imei, notice_id = bean.notice_id);
        mViewModel.getJqTztgFkList(request)

    }


    override fun viewOnClick() {
        bind.btnConfirm.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                if (bean.feedback_status.equals("0")) {
                    val request = GetJqTztgDetailRequest()
                    request.imei = DataHelper.imei
                    request.mjdwdm = userBean.group.code
                    request.mjdwmc = userBean.group.name
                    request.mjjh = userBean.pcard
                    request.mjxm = userBean.name
                    request.notice_id = bean.notice_id
                    request.xzb = ""
                    request.yzb = ""
                    mViewModel.getJqTztgDetail(request)
                    return
                }
                val intent = Intent(activity, InstructionsFeedBackActivity::class.java)
                intent.putExtra("notice_id", bean.notice_id)
                feedbackState.launch(intent)
            }
        })

        bind.lvYuyin.onItemClickListener = object : OnItemClickViewListener() {
            override fun onItemClickView(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var audioMessageBean = audioAdapter.getItem(position)
                if (audioMessageBean.isread == 0) {
                    audioMessageBean.isread = 1
                    var request = InstructionMessageReadRequest(
                        bean.notice_id,
                        audioMessageBean.id,
                        "audio"
                    )
                    mViewModel.instructionMessageRead(request)
                    audioMessageBean.isread = 1
                }

                if (VoicePlayer.isPlaying()) {
                    if (posStart == position) {
                        VoicePlayer.stop()
                        isAudioLoading = false
                    } else {
                        isAudioLoading = true
                        //VoicePlayer.getInstance().playUrlNet(message.getPath());

                        if (audioMessageBean?.path!!.startsWith("http")) {

                            VoicePlayer.playUrlNet(audioMessageBean.path)
                        } else {
                            playUrlautio(audioMessageBean.path!!, "mp3")
                        }
                    }
                } else {
                    isAudioLoading = true
                    if (audioMessageBean.path!!.startsWith("http")) {

                        VoicePlayer.playUrlNet(audioMessageBean.path)
                    } else {
                        playUrlautio(audioMessageBean.path!!, "mp3")
                    }
                    //VoicePlayer.getInstance().playUrlNet(message.getPath());
                }
                posStart = position
                audioAdapter.posStart = posStart
                audioAdapter.isAudioLoading = isAudioLoading
                audioAdapter.notifyDataSetChanged()

            }

        }

        bind.gvPicture.onItemClickListener = object : OnItemClickViewListener() {
            override fun onItemClickView(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val path: String = pictureAdapter.getItem(position)
                lookBigImageDialog.setPath(path)
                lookBigImageDialog.showDialog(supportFragmentManager, "lookBigImageDialog")
            }
        }

        bind.gvVideo.onItemClickListener = object : OnItemClickViewListener() {
            override fun onItemClickView(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                //                if (videoDialog.isAdded()) return;
                //                if (videoDialog.isVisible()) return;
                //                if (videoDialog.isRemoving()) return;
                val base64Info: String = videoInstructionsAdapter.getItem(position).path!!
                Thread {
                    val path: String = if (base64Info.startsWith("http")) {
                        base64Info
                    } else {
                        base64ToVideo(base64Info, "mp4")
                    }
                    runOnUiThread {
                        if (TextUtils.isEmpty(path)) {
                            Toast.makeText(activity, "视频解析失败", Toast.LENGTH_SHORT).show()
                            return@runOnUiThread
                        }
                        val intent = Intent()
                        intent.setClass(activity, VideoActivity::class.java)
                        intent.putExtra("path", path)
                        startActivity(intent)
                    }
                }.start()
            }
        }
    }

    private fun playUrlautio(base64Info: String, type: String) {
        Thread {
//            String type = base64Info.substring(base64Info.indexOf("audio/") + 6, base64Info.indexOf(";base64,"));
//            String base64Infos = base64Info.substring(base64Info.indexOf(";base64,") + 8);
            val path: String = base64ToVideo(base64Info, type)
            runOnUiThread {
                if (TextUtils.isEmpty(path)) {
                    Toast.makeText(activity, "解析失败", Toast.LENGTH_SHORT).show()
                    return@runOnUiThread
                }
                VoicePlayer.playUrlNet(path)
            }
        }.start()
    }

    private fun base64ToVideo(base64: String, type: String): String {
        Log.e(">>>>>>>>>>>>", "base64ToVideo: $base64")
        return try {
            //base解密
            val videoByte = Base64.decode(base64.toByteArray(), Base64.DEFAULT)
            val videoFile =
                File(Environment.getExternalStorageDirectory().toString() + "/yuyin11." + type)
            if (videoFile.exists()) {
                videoFile.delete()
            }
            try {
                //创建文件
                videoFile.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
                Log.e("creatXMLFileException", e.message!!)
            }
            //输入视频文件
            val fos = FileOutputStream(videoFile)
            fos.write(videoByte, 0, videoByte.size)
            fos.flush()
            fos.close()
            Log.e(">>>>", "视屏保存的地址--$videoFile")
            videoFile.path
        } catch (e: IOException) {
            Log.e(">>>>", "base64转换为视频异常", e)
            ""
        }
    }
}
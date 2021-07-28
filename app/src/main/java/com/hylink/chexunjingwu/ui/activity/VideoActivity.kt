package com.hylink.chexunjingwu.ui.activity

import android.view.View
import android.widget.MediaController
import com.dylanc.viewbinding.binding
import com.hylink.chexunjingwu.base.BaseActivity
import com.hylink.chexunjingwu.databinding.ActivityVideoBinding
import com.hylink.chexunjingwu.tools.OnClickViewListener

class VideoActivity : BaseActivity() {
    val bind: ActivityVideoBinding by binding()
    override fun initData() {
        val path = intent.getStringExtra("path")

        bind.includeTitle.tvTitle.text = "视频播放"
        bind.includeTitle.ibnBack.setOnClickListener(object : OnClickViewListener() {
            override fun onClickSuc(v: View?) {
                finish()
            }
        })

        val mediaController = MediaController(this)
        mediaController.setAnchorView(bind.videoView)
        mediaController.keepScreenOn = true
        mediaController.visibility = View.VISIBLE
        mediaController.setMediaPlayer(bind.videoView)
        mediaController.isEnabled = true
        bind.videoView.setMediaController(mediaController)
        bind.videoView.setVideoPath(path)
        bind.ibnClose.setOnClickListener { v ->
            if (bind.videoView.isPlaying) {
                bind.videoView.pause()
            }
            finish()
        }

        bind.videoView.setOnPreparedListener { //视频加载完成,准备好播放视频的回调
            //视频播放完成后的回调
            bind.progressBar.visibility = View.GONE
        }
        bind.videoView.setOnCompletionListener { }
        bind.videoView.setOnErrorListener { mp, what, extra ->
            //异常回调
            false //如果方法处理了错误，则为true；否则为false。返回false或根本没有OnErrorListener，将导致调用OnCompletionListener。
        }
        bind.videoView.start()
    }

    override fun onResume() {
        super.onResume()
        bind.videoView.resume()
    }

    override fun onPause() {
        super.onPause()
        bind.videoView.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        bind.videoView.stopPlayback() //停止播放视频,并且释放
        bind.videoView.suspend() //在任何状态下释放媒体播放器
    }

    override fun viewOnClick() {

    }
}
package com.example.chexunjingwu.tools

import android.media.AudioManager
import android.media.MediaPlayer
import java.io.IOException

object VoicePlayer {

    var mediaPlayer: MediaPlayer? = null
    private var isCompletion = true
    private var isPrepared = false
    var onPreparedListener: OnPreparedListener? = null

    init {
        mediaPlayer = MediaPlayer();
        mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer!!.setOnCompletionListener {
            isCompletion = true
            if (onPreparedListener != null) {
                onPreparedListener!!.onCompletion()
            }
        };
        mediaPlayer!!.setOnPreparedListener {
            isPrepared = true
            play()
            if (onPreparedListener != null) {
                onPreparedListener!!.onPrepared()
            }
        };


    }

    fun isPlaying(): Boolean {
        return if (mediaPlayer == null) false else mediaPlayer!!.isPlaying
    }

    /**
     * 播放
     */
    fun play() {
        if (mediaPlayer != null) {
            mediaPlayer!!.start()
        }
    }

    fun playUrl(videoUrl: String?) {
        if (mediaPlayer == null) {
            return
        }
        try {
            isCompletion = false
            mediaPlayer!!.reset()
            mediaPlayer!!.setDataSource(videoUrl)
            //            mediaPlayer.prepare();
            mediaPlayer!!.prepareAsync()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * 暂停
     */
    fun pause() {
        if (mediaPlayer != null) {
            mediaPlayer!!.pause()
        }
    }

    fun stop() {
        if (mediaPlayer != null) {
            if (mediaPlayer!!.isPlaying) {
                mediaPlayer!!.stop()
            }
        }
    }

    fun onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer!!.stop()
            mediaPlayer!!.release()
            mediaPlayer = null
        }
    }

    fun onStopDe() {
        mediaPlayer!!.stop()
    }

    /**
     * 重播
     */
    fun replay() {
        if (mediaPlayer != null) {
            mediaPlayer!!.seekTo(0)
        }
    }


    /**
     * 是否准备完毕
     */
    fun isPrepared(): Boolean {
        return isPrepared
    }

    /**
     * 是否播放完毕
     */
    fun isCompletion(): Boolean {
        return isCompletion
    }

    fun playUrlNet(url: String?) {
        stop()
        playUrl(url)
    }
}

interface OnPreparedListener {
    fun onPrepared()
    fun onCompletion()
}
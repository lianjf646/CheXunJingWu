package com.hylink.chexunjingwu.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import com.google.gson.Gson
import com.hylink.chexunjingwu.R
import com.hylink.chexunjingwu.base.App
import com.hylink.chexunjingwu.http.mqttresponse.MqttCarWarningBean
import com.hylink.chexunjingwu.http.mqttresponse.MqttNoticeBean
import com.hylink.chexunjingwu.http.mqttresponse.MqttPersonWarningBean
import com.hylink.chexunjingwu.http.response.GetJqListResponse
import com.hylink.chexunjingwu.http.response.HomeLoginResponse
import com.hylink.chexunjingwu.tools.DataHelper
import com.hylink.chexunjingwu.tools.showNormal
import com.hylink.chexunjingwu.ui.activity.*

object PushBroadcastReceiver {


    fun messageArrived(topic: String?, message: String?) {
        var personA = "Ret/verificationResults/personA/${DataHelper.imei}" //人脸预警
        var PAlarm = "Ret/PAlarm/Send/${DataHelper.imei}"                  //处警
        var car = "Ret/verificationResults/car/${DataHelper.imei}"         //车预警
        var noticeSend = "Ret/Notice/Send/${DataHelper.imei}"              //指令通知
        var userBean = DataHelper.getData(DataHelper.loginUserInfo) as HomeLoginResponse.Data.Data.User;
        var jqZcInfo = "Jq/zc/info/${DataHelper.imei}/${userBean.pcard}"                //警情暂存
        var printState = "PrintState/get/${DataHelper.imei}/${userBean.pcard}";         //打印机状态


        when (topic) {
            personA -> {
                var bean =
                    Gson().fromJson(message.toString(), MqttPersonWarningBean::class.java)
                if (bean.verificationPortraitDataList.isNullOrEmpty()) return
                sendPersonWarning(
                    "人像预警",
                    bean.verificationPortraitDataList?.last()?.name!!,
                    bean.portrait_id!!
                )
            }

            car -> {
                var bean =
                    Gson().fromJson(message.toString(), MqttCarWarningBean::class.java)
                sendCarWarning(
                    "车牌预警",
                    bean.comparison_message?.hphm!!,
                    bean.comparison_id!!
                )
            }

            noticeSend -> {
                var bean =
                    Gson().fromJson(message.toString(), MqttNoticeBean::class.java);
                sendNotice(bean.bt!!, bean.nr!!, bean.notice_id!!);
            }

            PAlarm -> {
                var bean = Gson().fromJson(
                    message.toString(),
                    GetJqListResponse.ListBean::class.java
                )
                sendYuJing(bean.bjlbmc, bean.bjnr, bean);
            }

            printState -> {
                showNormal(message.toString())
            }

            jqZcInfo -> {
                sendNotiZc(message.toString());
            }

        }

    }

    private fun sendYuJing(
        title: String,
        content: String,
        bean: GetJqListResponse.ListBean
    ) {
        val hangIntent = Intent()
        hangIntent.flags = Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        hangIntent.setClass(App.app, PoliceInforartionDetailActivity::class.java)
        hangIntent.putExtra("getjqlistresponse.resultbean.listbean", bean)
        receiveMqttInfo(title, content, hangIntent, 6)
    }


    private fun sendPersonWarning(title: String, content: String, byId: String) {
        var hangIntent = Intent();
        hangIntent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        hangIntent.setClass(App.app, PersonnelInfoActivity::class.java)
        hangIntent.putExtra("comparison_id", byId)
        hangIntent.putExtra("pos", -1)
        receiveMqttInfo(title, content, hangIntent, 1);
    }

    private fun sendCarWarning(title: String, content: String, byId: String) {
        val hangIntent = Intent()
        hangIntent.flags = Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        hangIntent.setClass(App.app, CarInfoActivity::class.java)
        hangIntent.putExtra("comparison_id", byId)
        receiveMqttInfo(title, content, hangIntent, 3)
    }

    private fun sendNotice(title: String, content: String, byId: String) {
        val hangIntent = Intent()
        hangIntent.flags = Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        hangIntent.setClass(App.app, InstructionsActivity::class.java)
        hangIntent.putExtra("byId", byId)
        receiveMqttInfo(title, content, hangIntent, 5)
    }

    private fun sendNotiZc(byId: String) {
        var hangIntent = Intent();
        hangIntent.flags = Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        hangIntent.setClass(App.app, JingQingFeedBackActivity::class.java);
        hangIntent.putExtra("byId", byId);
        receiveMqttInfo("反馈信息", "你有一条来自车机的反馈信息，请您及时处理", hangIntent, 4);
    }

    private var a = 1

    /**
     * 接受mqtt消息
     *
     * @param title
     * @param content
     */
    private fun receiveMqttInfo(title: String, content: String, hangIntent: Intent, type: Int) {
        val CHANNEL_ID = "车巡警务协同ID$type"
        val CHANNEL_NAME = "车巡警务协同$type"
        val CHANNEL_DESCRIPTION = "车巡警务协同DES$type"
        val hangPendingIntent =
            PendingIntent.getActivity(App.app, a++, hangIntent, PendingIntent.FLAG_CANCEL_CURRENT)
        val notificationManager =
            App.app.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // 步骤1：告诉 Notification.Builder 我们需要的通知内容
        val builder: Notification.Builder = Notification.Builder(App.app)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    App.app.getResources(),
                    R.mipmap.ic_launcher
                )
            )
            .setAutoCancel(true)
            .setContentTitle(title)
            .setContentText(content)
            .setWhen(System.currentTimeMillis())
            .setContentIntent(hangPendingIntent)
        //                .setDeleteIntent(deletePendingIntent)
//                .addAction(Notification.Action.SEMANTIC_ACTION_CALL);
        // 步骤2：Android 8.0 新增了 NotificationChannel,这里需要做版本区分
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel =
                NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            mChannel.description = CHANNEL_DESCRIPTION
            notificationManager.createNotificationChannel(mChannel)
            builder.setChannelId(CHANNEL_ID)
        }
        // 步骤3：拿到我们前面让 Notification.Builder 去帮忙构建的通知
        val notification = builder.build()
        // 步骤4：调用 NotificationManager.notify() 发送通知
//        notificationManager.notify(String.valueOf(notifyId++), notifyId++, notification);
        notificationManager.notify(type.toString(), type, notification)
    }
}
package com.example.chexunjingwu.http

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import com.example.chexunjingwu.BuildConfig
import com.example.chexunjingwu.R
import com.example.chexunjingwu.base.App
import com.example.chexunjingwu.http.mqttresponse.MqttCarWarningBean
import com.example.chexunjingwu.http.mqttresponse.MqttNoticeBean
import com.example.chexunjingwu.http.mqttresponse.MqttPersonWarningBean
import com.example.chexunjingwu.http.response.GetJqListResponse
import com.example.chexunjingwu.tools.showError
import com.example.chexunjingwu.tools.showNormal
import com.example.chexunjingwu.ui.activity.*
import com.google.gson.Gson
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import java.util.*

object AndroidMqttClient {

    private const val TAG = "AndroidMqttClient"
    private var mqttClient: MqttAndroidClient
    val serverURI = BuildConfig.TCP_MQTT
    var personA: String? = null      //人像预警
    var PAlarm: String? = null       //处警
    var car: String? = null          //车预警
    var noticeSend: String? = null   //指令通知
    var printState: String? = null   //打印状态
    var jqZcInfo: String? = null     //警情暂存


    init {
        var kotlin_client = UUID.randomUUID().toString();
        mqttClient = MqttAndroidClient(App.app, serverURI, kotlin_client)
        mqttClient.setCallback(object : MqttCallback {
            override fun messageArrived(topic: String?, message: MqttMessage?) {
                Log.d(TAG, "Receive message: ${message.toString()} from topic: $topic")

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

            override fun connectionLost(cause: Throwable?) {
                Log.d(TAG, "Connection lost ${cause.toString()}")
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {

            }
        })
    }

    /**
     *连接 MQTT 服务器
     */
    fun connect(imei: String, pcard: String) {
        val options = MqttConnectOptions()
        mqttClient.connect(options, null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Log.d(TAG, "Connection success")
                subscribe(imei, pcard)
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Log.d(TAG, "Connection failure")
                showError("mqtt连接失败")
            }
        })
    }

    /**
     * 创建 MQTT 订阅
     */
    fun subscribe(imei: String, pcard: String) {
        if (mqttClient == null || !mqttClient.isConnected()) {
            return
        }
        personA = "Ret/verificationResults/personA/${imei}" //人脸预警
        PAlarm = "Ret/PAlarm/Send/${imei}"                  //处警
        car = "Ret/verificationResults/car/${imei}"         //车预警
        noticeSend = "Ret/Notice/Send/${imei}"              //指令通知
        jqZcInfo = "Jq/zc/info/$imei/$pcard"                //警情暂存
        printState = "PrintState/get/$imei/$pcard";         //打印机状态

        var topic = arrayOf(car, PAlarm, personA, noticeSend, jqZcInfo, jqZcInfo)
        var qos = IntArray(topic.size);
        for (i in topic.indices) {
            qos[i] = 1
        }
        mqttClient.unsubscribe(topic)
        mqttClient.subscribe(topic, qos, null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Log.d(TAG, "Subscribed to $topic")
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Log.d(TAG, "Failed to subscribe $topic")
            }
        })

    }

    /**
     * 取消订阅
     */
    fun unsubscribe(topic: String) {
        mqttClient.unsubscribe(topic, null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Log.d(TAG, "Unsubscribed to $topic")
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Log.d(TAG, "Failed to unsubscribe $topic")
            }
        })
    }

    /**
     * 发布消息
     */
    fun publish(topic: String, msg: String, qos: Int = 1, retained: Boolean = false) {
        val message = MqttMessage()
        message.payload = msg.toByteArray()
        message.qos = qos
        message.isRetained = retained
        mqttClient.publish(topic, message, null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Log.d(TAG, "$msg published to $topic")
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Log.d(TAG, "Failed to publish $msg to $topic")
            }
        })

    }

    /**
     * 断开 MQTT 连接
     */
    fun disconnect() {
        mqttClient.disconnect(null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Log.d(TAG, "Disconnected")
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Log.d(TAG, "Failed to disconnect")
            }
        })

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


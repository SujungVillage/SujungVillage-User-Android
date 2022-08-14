package kr.co.sujungvillage.api.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kr.co.sujungvillage.MainActivity
import kr.co.sujungvillage.R

class MyFirebaseMessagingService : FirebaseMessagingService() {
    // 메시지 수신 시 호출 함수
    override fun onMessageReceived(message: RemoteMessage) {
        // 알람 설정이 false일 경우 종료
        val shared = this.getSharedPreferences("SujungVillage", Context.MODE_PRIVATE)
        if (!shared?.getBoolean("alarm", true)!!) {
            return
        }

        if (message.data.isNotEmpty()) {
            Log.d("MESSAGE_RECEIVED", "message data payload: ${message.data}")
        }

        // 알림 제목, 내용 설정
        var notificationInfo: Map<String, String> = mapOf()
        message.notification?.let {
            Log.d("MESSAGE_RECEIVED", "message notification body: ${it.body}")
            notificationInfo = mapOf(
                "title" to it.title.toString(),
                "body" to it.body.toString()
            )
            sendNotification(notificationInfo)
        }
    }

    override fun onNewToken(token: String) {
        Log.d("ON_NEW_TOKEN", "Refreshed token: $token")
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String?) {
        Log.d("SEND_REGISTRATION_TO_SERVER", "sendRegistrationTokenToServer($token)")
    }

    // 알림 전송 함수
    private fun sendNotification(messageBody: Map<String, String>) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT)

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        // 알림 설정
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(messageBody["title"])
            .setContentText(messageBody["body"])
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        // 안드로이드 오레오 알림채널에 필요한 추가사항
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }
}
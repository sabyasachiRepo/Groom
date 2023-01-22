package edu.student.groom;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String token) {
        Log.d("FCM token: ", token);
        super.onNewToken(token);
    }

    @Override
    public void handleIntent(Intent intent) {
        super.handleIntent(intent);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Notification notification = new NotificationCompat.Builder(this, "Groom")
                .setContentTitle("Groom Notification")
                .setContentText(remoteMessage.getData().get("content"))
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();

        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel
                    = new NotificationChannel("Groom","Groom", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(notificationChannel);

        }
        manager.notify(1, notification);
    }
}

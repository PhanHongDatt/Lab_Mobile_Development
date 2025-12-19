package com.example.lab05_bai3_23520266_phanhongdat;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.telephony.SmsManager;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import java.util.ArrayList;

public class SmsAutoReplyService extends Service {

    private static final String CHANNEL_ID = "AUTO_SMS_CHANNEL";

    @Override
    public void onCreate() {
        super.onCreate();

        // tạo Notification Channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Auto SMS Reply",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Auto SMS Reply")
                .setContentText("Sending automatic response...")
                .setSmallIcon(android.R.drawable.sym_action_email)
                .build();

        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        ArrayList<String> addresses =
                intent.getStringArrayListExtra(SmsReceiver.SMS_MESSAGE_ADDESS_KEY);

        if (addresses != null &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                        == PackageManager.PERMISSION_GRANTED) {

            SmsManager sms = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    ? getSystemService(SmsManager.class)
                    : SmsManager.getDefault();

            for (String phone : addresses) {
                sms.sendTextMessage(
                        phone,
                        null,
                        "I am safe and well. Worry not.",
                        null,
                        null
                );
            }
        }

        stopForeground(true);
        stopSelf();
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

package com.example.lab05_bai3_23520266_phanhongdat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.util.ArrayList;

public class SmsReceiver extends BroadcastReceiver {

    public static final String SMS_MESSAGE_ADDESS_KEY = "sms_messages_key";
    public static final String SMS_FORWARD_BROADCAST_RECEIVER = "sms_forward_broadcast_receiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        String queryString = "are you ok";
        Bundle bundle = intent.getExtras();

        if (bundle == null) return;

        Object[] pdus = (Object[]) bundle.get("pdus");
        String format = bundle.getString("format");

        if (pdus == null) return;

        SmsMessage[] messages = new SmsMessage[pdus.length];

        for (int i = 0; i < pdus.length; i++) {
            if (Build.VERSION.SDK_INT >= 23) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
            } else {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
            }
        }

        ArrayList<String> addresses = new ArrayList<>();
        for (SmsMessage msg : messages) {
            if (msg != null &&
                    msg.getMessageBody() != null &&
                    msg.getMessageBody().toLowerCase().contains(queryString)) {

                addresses.add(msg.getOriginatingAddress());
            }
        }

        if (addresses.isEmpty()) return;

        // Đọc trạng thái Auto Response
        SharedPreferences prefs =
                context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        boolean autoResponse = prefs.getBoolean("auto_response", false);

        if (!MainActivity.isRunning && autoResponse) {
            // App tắt
            Intent serviceIntent = new Intent(context, SmsAutoReplyService.class);
            serviceIntent.putStringArrayListExtra(SMS_MESSAGE_ADDESS_KEY, addresses);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(serviceIntent);
            } else {
                context.startService(serviceIntent);
            }
        } else if (MainActivity.isRunning) {
            // app mở
            Intent forward = new Intent(SMS_FORWARD_BROADCAST_RECEIVER);
            forward.putStringArrayListExtra(SMS_MESSAGE_ADDESS_KEY, addresses);
            forward.setPackage(context.getPackageName());
            context.sendBroadcast(forward);
        }
    }
}

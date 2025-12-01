package com.example.lab05_23520266_phanhongdat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.pm.PackageManager;
import android.os.Build;
import android.Manifest; // Chú ý dòng này
public class MainActivity extends AppCompatActivity {
    private BroadcastReceiver broadcastReceiver;
    private IntentFilter filter;
    public void processRecive(Context context, Intent intent)
    {
        Toast.makeText(context, getString(R.string.you_have_a_new_message), Toast.LENGTH_SHORT).show();
        TextView tvContent = findViewById(R.id.tv_content);
        final String SMS_EXTRA_NAME = "pdus";
        Bundle bundle = intent.getExtras();
        Object[] messages = (Object[]) bundle.get(SMS_EXTRA_NAME);
        String sms = "";
        SmsMessage smsMsg;
        for (int i = 0; i < messages.length; i++) {
            if(Build.VERSION.SDK_INT >= 23) {
                smsMsg = SmsMessage.createFromPdu((byte[]) messages[i]);
            } else {
                smsMsg = SmsMessage.createFromPdu((byte[]) messages[i]);
            }
            String msgBody=smsMsg.getMessageBody();
            String address=smsMsg.getOriginatingAddress();
            sms +=address+":\n"+msgBody+"\n\n";
        }
        tvContent.setText(sms);
    }
    private void initBroadcastReceiver() {
        filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                processRecive(context, intent);
            }
        };
    }
    @Override
    protected void onResume() {
        super.onResume();
       if(broadcastReceiver != null) registerReceiver(broadcastReceiver, filter);

    }
    @Override
    protected void onStop() {
        super.onStop();
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        initBroadcastReceiver();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(android.Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{android.Manifest.permission.RECEIVE_SMS}, 1000);
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tv_content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
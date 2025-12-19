package com.example.lab05_bai3_23520266_phanhongdat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class MainActivity extends AppCompatActivity {
    private ReentrantLock reentrantLock;
    private Switch swAutoResponse;
    private LinearLayout llButtons;
    private Button btnSafe, btnMayday;
    private ArrayList<String> requesters;
    private ArrayAdapter<String> adapter;
    private ListView lvMessages;
    private BroadcastReceiver broadcastReceiver;
    public static boolean isRunning = false;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    private final String AUTO_RESPONSE = "auto_response";

    // Action thống nhất giữa MainActivity và SmsReceiver
    public static final String UPDATE_UI_ACTION = "UPDATE_UI_ACTION";

    private void findViewsByIds() {
        swAutoResponse = findViewById(R.id.sw_auto_response);
        llButtons = findViewById(R.id.ll_buttons);
        lvMessages = findViewById(R.id.lv_messages);
        btnSafe = findViewById(R.id.btn_safe);
        btnMayday = findViewById(R.id.btn_mayday);
    }

    //XỬ LÝ NHẬN DỮ LIỆU TỪ SMS RECEIVER
    private void handleIncomingIntent(Intent intent) {
        if (intent != null) {
            // Lấy danh sách số điện thoại từ Intent
            ArrayList<String> addresses = intent.getStringArrayListExtra(SmsReceiver.SMS_MESSAGE_ADDESS_KEY);
            if (addresses != null) {
                processReceiveAddresses(addresses);
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent); // Cập nhật Intent mới nhất
        handleIncomingIntent(intent);
    }

    private void respond(String to, String response) {
        // Kiểm tra quyền SEND_SMS trước khi gửi
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Chưa có quyền gửi SMS", Toast.LENGTH_SHORT).show();
            // Xin quyền nếu chưa có
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 101);
            return;
        }

        try {
            SmsManager sms;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                sms = getSystemService(SmsManager.class);
            } else {
                sms = SmsManager.getDefault();
            }
            sms.sendTextMessage(to, null, response, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi khi gửi tin nhắn", Toast.LENGTH_SHORT).show();
        }

        reentrantLock.lock();
        try {
            requesters.remove(to);
            adapter.notifyDataSetChanged();
        } finally {
            reentrantLock.unlock();
        }
    }

    public void respond(Boolean ok) {
        String okString = getString(R.string.i_am_safe_and_well_worry_not);
        String notOkString = getString(R.string.tell_my_mother_i_love_her);
        String response = ok ? okString : notOkString;

        reentrantLock.lock();
        try {
            ArrayList<String> requestersCopy = (ArrayList<String>) requesters.clone();
            requesters.clear();
            adapter.notifyDataSetChanged();
            for (String to : requestersCopy) {
                sendSmsBackground(to, response);
            }
        } finally {
            reentrantLock.unlock();
        }
    }

    private void sendSmsBackground(String to, String response) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            try {
                SmsManager sms;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    sms = getSystemService(SmsManager.class);
                } else {
                    sms = SmsManager.getDefault();
                }
                sms.sendTextMessage(to, null, response, null, null);
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    private void handleOnClickListenner() {
        btnSafe.setOnClickListener(view -> respond(true));
        btnMayday.setOnClickListener(view -> respond(false));

        swAutoResponse.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) llButtons.setVisibility(View.GONE);
            else llButtons.setVisibility(View.VISIBLE);
            editor.putBoolean(AUTO_RESPONSE, isChecked);
            editor.commit();
        });
    }

    /*private void initBroadcastReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Nhận broadcast khi app đang chạy (Foreground)
                if (UPDATE_UI_ACTION.equals(intent.getAction())) {
                    ArrayList<String> addresses = intent.getStringArrayListExtra(SmsReceiver.SMS_MESSAGE_ADDESS_KEY);
                    processReceiveAddresses(addresses);
                }
            }
        };
    }*/
    private void initBroadcastReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (SmsReceiver.SMS_FORWARD_BROADCAST_RECEIVER.equals(intent.getAction())) {
                    ArrayList<String> addresses = intent.getStringArrayListExtra(SmsReceiver.SMS_MESSAGE_ADDESS_KEY);
                    processReceiveAddresses(addresses);
                }
            }
        };
    }

    private void processReceiveAddresses(ArrayList<String> addresses) {
        if (addresses != null) {
            reentrantLock.lock();
            try {
                for (String address : addresses) {
                    if (!requesters.contains(address)) {
                        requesters.add(address);
                    }
                }
            } finally {
                reentrantLock.unlock();
            }
            // Cập nhật giao diện trên Main
            runOnUiThread(() -> {
                adapter.notifyDataSetChanged();
                if (swAutoResponse.isChecked()) {
                    respond(true);
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
        if (broadcastReceiver == null) initBroadcastReceiver();


        IntentFilter intentFilter = new IntentFilter(SmsReceiver.SMS_FORWARD_BROADCAST_RECEIVER);

        // Code bảo mật cho Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(broadcastReceiver, intentFilter, Context.RECEIVER_NOT_EXPORTED);
        } else {
            registerReceiver(broadcastReceiver, intentFilter);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        isRunning = false;
        if (broadcastReceiver != null) {
            try {
                unregisterReceiver(broadcastReceiver);
            } catch (IllegalArgumentException e) { }
        }
    }

    private void initVariables() {
        sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        reentrantLock = new ReentrantLock();
        requesters = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, requesters);
        lvMessages.setAdapter(adapter);

        boolean autoResponse = sharedPreferences.getBoolean(AUTO_RESPONSE, false);
        swAutoResponse.setChecked(autoResponse);
        if (autoResponse) llButtons.setVisibility(View.GONE);

        initBroadcastReceiver();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewsByIds();
        initVariables();
        handleOnClickListenner();

        checkAndRequestPermissions();

        handleIncomingIntent(getIntent());
    }

    // Hàm xin quyền
    private void checkAndRequestPermissions() {
        String[] permissions = {
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS,
                Manifest.permission.SEND_SMS
        };

        ArrayList<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            if (ActivityCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[0]), 100);
        }
    }
}
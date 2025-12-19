package com.example.lab05_23520266_phanhongdat;

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
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Bai3 extends AppCompatActivity {
    private ReentrantLock reentrantLock;
    private Switch swAutoResponse;
    private LinearLayout llButtons;
    private Button btnSafe, btnMayday;
    private ArrayList<String> requesters;
    private ArrayAdapter<String> adapter;
    private ListView lvMessages;
    private BroadcastReceiver broadcastReceiver;
    public static Boolean isRunning = false;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    private final String AUTO_RESPONSE = "auto_response";

    private void findViewsByIds() {
        swAutoResponse = findViewById(R.id.sw_auto_response);
        llButtons = findViewById(R.id.ll_buttons);
        lvMessages = findViewById(R.id.lv_messages);
        btnSafe = findViewById(R.id.btn_safe);
        btnMayday = findViewById(R.id.btn_mayday);
    }

    // Hàm gửi tin nhắn cho 1 người
    private void respond(String to, String response) {
        // [FIX 1] Kiểm tra quyền hoặc Try-Catch để tránh Crash
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Chưa cấp quyền gửi SMS!", Toast.LENGTH_SHORT).show();
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
            e.printStackTrace(); // Bắt lỗi để app không bị văng
        }

        // Cập nhật UI: Xóa khỏi danh sách
        reentrantLock.lock();
        try {
            requesters.remove(to);
            adapter.notifyDataSetChanged();
        } finally {
            reentrantLock.unlock();
        }
    }

    // Hàm gửi tin nhắn cho TẤT CẢ (Nút bấm)
    public void respond(Boolean ok) {
        String okString = getString(R.string.i_am_safe_and_well_worry_not);
        String notOkString = getString(R.string.tell_my_mother_i_love_her);
        String response = ok ? okString : notOkString;

        reentrantLock.lock();
        try {
            // [FIX 2] Logic đúng: Copy danh sách -> Xóa danh sách gốc -> Gửi tin cho danh sách Copy
            ArrayList<String> requestersCopy = (ArrayList<String>) requesters.clone();
            requesters.clear();
            adapter.notifyDataSetChanged(); // Cập nhật UI 1 lần duy nhất là đủ

            // Gửi tin nhắn ngầm (không gọi hàm respond(String, String) để tránh lỗi adapter)
            for (String to : requestersCopy) {
                sendSmsBackground(to, response);
            }
        } finally {
            reentrantLock.unlock();
        }
    }

    // Hàm phụ trợ gửi SMS không đụng vào UI
    private void sendSmsBackground(String to, String response) {
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

    private void initBroadcastReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // [Lưu ý] Đảm bảo tên KEY khớp với bên SmsReceiver (ADDRESS có 2 chữ D, 1 chữ R, 2 chữ S)
                ArrayList<String> addresses = intent.getStringArrayListExtra(SmsReceiver.SMS_MESSAGE_ADDESS_KEY);
                processReceiveAddresses(addresses);
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
            adapter.notifyDataSetChanged();

            // Nếu Auto Response bật -> Tự động trả lời
            if (swAutoResponse.isChecked()) {
                respond(true);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
        if (broadcastReceiver == null) initBroadcastReceiver();

        IntentFilter intentFilter = new IntentFilter("UPDATE_UI_ACTION"); // Action phải khớp bên SmsReceiver

        // KIỂM TRA PHIÊN BẢN ANDROID ĐỂ THÊM CỜ BẢO MẬT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+ bắt buộc phải có cờ này
            registerReceiver(broadcastReceiver, intentFilter, Context.RECEIVER_NOT_EXPORTED);
        } else {
            // Android cũ hơn thì đăng ký như bình thường
            registerReceiver(broadcastReceiver, intentFilter);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        isRunning = false;
        // [FIX 3] Bắt buộc Try-Catch khi unregisterReceiver
        if (broadcastReceiver != null) {
            try {
                unregisterReceiver(broadcastReceiver);
            } catch (IllegalArgumentException e) {
                // Receiver chưa được đăng ký hoặc đã hủy từ trước -> Bỏ qua lỗi này
            }
        }
    }

    private void initVariables() {
        sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE); // Nên dùng getSharedPreferences có tên cụ thể
        editor = sharedPreferences.edit();
        reentrantLock = new ReentrantLock();
        requesters = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, requesters);
        lvMessages.setAdapter(adapter);

        boolean autoResponse = sharedPreferences.getBoolean(AUTO_RESPONSE, false);
        swAutoResponse.setChecked(autoResponse);
        if (autoResponse) llButtons.setVisibility(View.GONE);

        initBroadcastReceiver();
        Intent intent =getIntent();
        if(intent!=null)
        {
            ArrayList<String> addresses=intent.getStringArrayListExtra(SmsReceiver.SMS_MESSAGE_ADDESS_KEY);
            // Nếu có dữ liệu thì xử lý
            if(addresses!=null)
            {
                processReceiveAddresses(addresses);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Nếu API cũ thì EdgeToEdge có thể gây lỗi, nếu lỗi hãy comment dòng này lại
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bai3);

        findViewsByIds();
        initVariables();
        handleOnClickListenner();

        // [QUAN TRỌNG] Nhận dữ liệu khi App tự bật lên từ SmsReceiver (TH2)
        Intent intent = getIntent();
        if (intent != null) {
            ArrayList<String> addresses = intent.getStringArrayListExtra(SmsReceiver.SMS_MESSAGE_ADDESS_KEY);
            processReceiveAddresses(addresses);
        }
    }
}
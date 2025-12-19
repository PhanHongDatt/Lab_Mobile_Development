package com.example.lab05_23520266_phanhongdat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Bai2 extends AppCompatActivity {
    private PowerStateChangeReceiver myReceiver;
    public class PowerStateChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(context==null||intent==null) return;
            String action=intent.getAction();
            if(Intent.ACTION_POWER_CONNECTED.equals(action))
                Toast.makeText(context, getString(R.string.power_connected), Toast.LENGTH_SHORT).show();
            else if(Intent.ACTION_POWER_DISCONNECTED.equals(action))
                Toast.makeText(context, getString(R.string.power_disconnected), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        if(myReceiver==null) myReceiver=new PowerStateChangeReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        registerReceiver( myReceiver, filter);
    }
    @Override
    protected void onStop()
    {
        super.onStop();
        if(myReceiver!=null) try {
            unregisterReceiver(myReceiver);
        }
    catch (IllegalArgumentException e)
    {
        e.printStackTrace();
    }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bai2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
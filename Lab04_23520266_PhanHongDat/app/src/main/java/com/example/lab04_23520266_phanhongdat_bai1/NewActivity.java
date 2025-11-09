package com.example.lab04_23520266_phanhongdat_bai1;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lab04_23520266_phanhongdat_bai1.R;

public class NewActivity extends AppCompatActivity {

    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        btnBack = findViewById(R.id.btn_back);

        // Khi nhấn nút Back → trở lại Activity cũ + hiệu ứng đẩy ngược
        btnBack.setOnClickListener(v -> finish());
    }

    @Override
    public void finish() {
        super.finish();
        // Áp dụng hiệu ứng ngược lại khi quay về
       overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
}

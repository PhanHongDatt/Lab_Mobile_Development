package com.example.bai1_lab02_23520266_phanhongdat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        //Khai báo các Button
        AppCompatButton BtnCase1=findViewById(R.id.btn_case1);
        AppCompatButton BtnCase2=findViewById(R.id.btn_case2);
        AppCompatButton BtnCase3=findViewById(R.id.btn_case3);
        AppCompatButton BtnCase4=findViewById(R.id.btn_case4);
        AppCompatButton BtnCase5=findViewById(R.id.btn_case5);
        AppCompatButton BtnBai6=findViewById(R.id.btn_Bai6);

        //Lắng nghe sự kiện
        BtnCase1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Case1_Lab02.class);
                startActivity(intent);
            }
        }
        );
        BtnCase2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent=new Intent(MainActivity.this,case2_lab02.class);
                                            startActivity(intent);
                                        }
                                    }
        );
        BtnCase3.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent=new Intent(MainActivity.this,case3_lab02.class);
                                            startActivity(intent);
                                        }
                                    }
        );
        BtnCase4.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent=new Intent(MainActivity.this,case4_lab02.class);
                                            startActivity(intent);
                                        }
                                    }
        );
        BtnCase5.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent=new Intent(MainActivity.this, case5_lab02.class);
                                            startActivity(intent);
                                        }
                                    }
        );
        BtnBai6.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent=new Intent(MainActivity.this, case06_lab02.class);
                                            startActivity(intent);
                                        }
                                    }
        );
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main),
                (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
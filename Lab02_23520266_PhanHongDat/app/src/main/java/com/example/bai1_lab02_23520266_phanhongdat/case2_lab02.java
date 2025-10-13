package com.example.bai1_lab02_23520266_phanhongdat;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class case2_lab02 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_case2_lab02);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
           //Khai báo các phần tử được sử dụng
            ListView lvNamePerson=(ListView) findViewById(R.id.lv_list_name);
            EditText etInputName=(EditText) findViewById(R.id.et_input);
            AppCompatButton btnInput=(AppCompatButton) findViewById(R.id.btn_input);
            //Tạo Array List Object
            ArrayList<String> names = new ArrayList<String>();
            //Set Adapter
            ArrayAdapter<String> adapter=new ArrayAdapter<String>
                    (this,android.R.layout.simple_list_item_1,names);
            lvNamePerson.setAdapter(adapter);
            //Tạo sự kiện cho Button
            btnInput.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = etInputName.getText().toString();
                    names.add(name);
                    adapter.notifyDataSetChanged();
                }
            }
                );
            lvNamePerson.setOnItemLongClickListener(
                    (adapterView, view, i, l) -> {
                        names.remove(i);
                        adapter.notifyDataSetChanged();
                        return true;
                    }
            );

            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
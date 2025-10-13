package com.example.bai1_lab02_23520266_phanhongdat;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
// Import thư viện để sử dụng ArrayAdapter
import android.widget.ArrayAdapter;
import android.widget.TextView;
public class Case1_Lab02 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_case1_lab02);

        // Tiến hành tạo listview và tham chiếu đến XML
        ListView lvNamePerson=(ListView) findViewById(R.id.lv_list_name);
        //Khai báo textview hiển thị tên
        TextView tvShowName=(TextView) findViewById(R.id.tv_show_name);
        //Khai báo chuỗi tên
        final String[] namePerson={"Phan Hồng Đạt","Nguyễn Văn A","Nguyễn Văn B","Nguyễn Văn C","Nguyễn Văn D"};

        // Xây dựng Adapter
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,namePerson);
        //Set Adapter
        lvNamePerson.setAdapter(adapter);
        //Xử lý sự kiện
        lvNamePerson.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?>
                                                    arg0, View arg1, int arg2, long arg3) {
                        //đối số arg2 là vị trí phần tử trong Data Source (arr)
                        tvShowName.setText("position :" + arg2 + " value =" + namePerson[arg2]);
                    }
                });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
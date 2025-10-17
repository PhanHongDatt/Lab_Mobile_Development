package com.example.bai1_lab02_23520266_phanhongdat;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

// Sửa đổi lớp cha một chút cho rõ ràng
class Employee {
    protected String manv;
    protected String tennv;

    public Employee(String manv, String tennv) {
        this.manv = manv;
        this.tennv = tennv;
    }

    // Phương thức này nên là abstract hoặc có một giá trị mặc định
    public double Tinhluong() {
        return 0; // Lương cơ bản, các lớp con sẽ ghi đè
    }

    @Override
    public String toString() {
        return manv + " - " + tennv;
    }


}

// Sửa lại lớp FulltimeEmployees
class FulltimeEmployees extends Employee {
    public FulltimeEmployees(String manv, String tennv) {
        super(manv, tennv);
    }

    @Override
    public double Tinhluong() {
        return 500; // Ví dụ lương cứng
    }

    // SỬA LỖI Ở ĐÂY: Kết hợp thông tin cha và con
    @Override
    public String toString() {
        // Lấy chuỗi "manv - tennv" từ lớp cha và nối thêm thông tin lương
        return super.toString() + " --> FullTime = " + Tinhluong();
    }
}

// Sửa lại lớp ParttimeEmployees
class ParttimeEmployees extends Employee {
    public ParttimeEmployees(String manv, String tennv) {
        super(manv, tennv);
    }

    @Override
    public double Tinhluong() {
        return 150; // Ví dụ lương theo giờ
    }

    // SỬA LỖI Ở ĐÂY: Kết hợp thông tin cha và con
    @Override
    public String toString() {
        return super.toString() + " --> PartTime = " + Tinhluong();
    }
}


public class case3_lab02 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_case3_lab02);

        EditText etManv = findViewById(R.id.et_manv);
        EditText etTennv = findViewById(R.id.et_tennv);
        RadioButton rb_Chinhthuc = findViewById(R.id.rb_chinhthuc);
        RadioButton rb_Thoivu = findViewById(R.id.rb_tamthoi);
        AppCompatButton btn_Input = findViewById(R.id.btn_nhap);
        ListView lv_Ds = findViewById(R.id.lv_ds_nhanvien);

        // SỬA LỖI Ở ĐÂY: Khởi tạo ArrayList trước
        ArrayList<Employee> dsNhanvien = new ArrayList<>();

        // SỬA LỖI Ở ĐÂY: Khởi tạo Adapter với ArrayList ở trên
        // Sử dụng Employee thay vì String để quản lý đối tượng dễ hơn
        ArrayAdapter<Employee> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dsNhanvien);
        lv_Ds.setAdapter(adapter);

        btn_Input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String manv = etManv.getText().toString().trim();
                String tennv = etTennv.getText().toString().trim();

                // Kiểm tra xem người dùng đã nhập đủ thông tin chưa
                if (manv.isEmpty() || tennv.isEmpty()) {
                    Toast.makeText(case3_lab02.this, "Vui lòng nhập đủ Mã và Tên NV", Toast.LENGTH_SHORT).show();
                    return;
                }

                Employee employee; // Tạo một biến employee chung

                if (rb_Chinhthuc.isChecked()) {
                    employee = new FulltimeEmployees(manv, tennv);
                } else { // Mặc định là thời vụ nếu chính thức không được chọn
                    // SỬA LỖI Ở ĐÂY: Thêm logic cho nhân viên thời vụ
                    employee = new ParttimeEmployees(manv, tennv);
                }

                // Thêm đối tượng employee vào danh sách
                dsNhanvien.add(employee);

                // SỬA LỖI Ở ĐÂY: Báo cho Adapter biết dữ liệu đã thay đổi
                adapter.notifyDataSetChanged();

                // Xóa chữ trong EditText để người dùng nhập tiếp
                etManv.setText("");
                etTennv.setText("");
                etManv.requestFocus();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
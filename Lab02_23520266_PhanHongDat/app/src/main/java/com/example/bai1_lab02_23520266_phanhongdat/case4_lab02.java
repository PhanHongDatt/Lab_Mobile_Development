package com.example.bai1_lab02_23520266_phanhongdat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// Đảm bảo bạn import đúng lớp model và adapter
import com.example.bai1_lab02_23520266_phanhongdat.Adapters.EmployeeAdapter;
import com.example.bai1_lab02_23520266_phanhongdat.models.module_employees;

import java.util.ArrayList;

public class case4_lab02 extends AppCompatActivity {

    EditText etId, etName;
    CheckBox cbIsManager;
    Button btnAdd;
    ListView lvEmployees;

    // THAY ĐỔI Ở ĐÂY: Sử dụng ArrayList<module_employees>
    ArrayList<module_employees> employeeList;
    EmployeeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case4_lab02);

        etId = findViewById(R.id.et_manv);
        etName = findViewById(R.id.et_tennv);
        cbIsManager = findViewById(R.id.cb_ismanager);
        btnAdd = findViewById(R.id.btn_nhap);
        lvEmployees = findViewById(R.id.lv_ds_nhanvien);

        // THAY ĐỔI Ở ĐÂY: Khởi tạo danh sách cho module_employees
        employeeList = new ArrayList<>();
        adapter = new EmployeeAdapter(this, R.layout.item_employee, employeeList);

        lvEmployees.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewEmployee();
            }
        });
    }

    private void addNewEmployee() {
        String id = etId.getText().toString().trim();
        String name = etName.getText().toString().trim();
        boolean isManager = cbIsManager.isChecked();

        if (id.isEmpty() || name.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ ID và Tên", Toast.LENGTH_SHORT).show();
            return;
        }

        // THAY ĐỔI Ở ĐÂY: Tạo đối tượng mới từ lớp module_employees
        module_employees newEmployee = new module_employees(id, name, isManager);

        employeeList.add(newEmployee);
        adapter.notifyDataSetChanged();

        etId.setText("");
        etName.setText("");
        cbIsManager.setChecked(false);
        etId.requestFocus();
    }
}
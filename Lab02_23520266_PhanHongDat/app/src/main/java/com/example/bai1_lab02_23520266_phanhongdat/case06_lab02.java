package com.example.bai1_lab02_23520266_phanhongdat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bai1_lab02_23520266_phanhongdat.Adapters.EmployeeRecyclerAdapter;
import com.example.bai1_lab02_23520266_phanhongdat.models.module_employees;

import java.util.ArrayList;

public class case06_lab02 extends AppCompatActivity {

    // Khai báo
    private EditText etId, etName;
    private CheckBox cbIsManager;
    private Button btnAdd;
    private RecyclerView rvEmployees;
    private static ArrayList<module_employees> employeeList = new ArrayList<>();
    private EmployeeRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case06_lab02);

        // Ánh xạ
        etId = findViewById(R.id.et_manv);
        etName = findViewById(R.id.et_tennv);
        cbIsManager = findViewById(R.id.cb_ismanager);
        btnAdd = findViewById(R.id.btn_nhap);
        rvEmployees = findViewById(R.id.rv_ds_nhanvien);

        // Thiết lập RecyclerView
        rvEmployees.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EmployeeRecyclerAdapter(this, employeeList);
        rvEmployees.setAdapter(adapter);

        // Sự kiện nút thêm
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

        // Thêm nhân viên mới
        module_employees newEmployee = new module_employees(id, name, isManager);
        employeeList.add(newEmployee);
        adapter.notifyItemInserted(employeeList.size() - 1);
        rvEmployees.scrollToPosition(employeeList.size() - 1);

        // Xóa nội dung nhập
        etId.setText("");
        etName.setText("");
        cbIsManager.setChecked(false);
        etId.requestFocus();
    }
}

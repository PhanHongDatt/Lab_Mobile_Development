package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText etName, etClass, etDateOfBirth, etMajor;
    private Button btnAdd, btnView;
    private Spinner spAcademic, spFactly;
    private DatabaseHelper dbHelper;
    private int editingStudentId = -1; // -1 = chế độ thêm, khác -1 = sửa

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spAcademic = findViewById(R.id.sp_academic_year);
        spFactly = findViewById(R.id.sp_factly);
        etName = findViewById(R.id.et_student_name);
        etClass = findViewById(R.id.et_student_class);
        etDateOfBirth = findViewById(R.id.et_dateofbirth);
        etMajor = findViewById(R.id.et_major);
        btnAdd = findViewById(R.id.btn_add);
        btnView = findViewById(R.id.btn_view);

        ArrayList<String> academicYears = new ArrayList<>();
        academicYears.add("Select Academic Year");
        academicYears.add("K2022");
        academicYears.add("K2023");
        academicYears.add("K2024");
        academicYears.add("K2025");

        ArrayList<String> factlyList = new ArrayList<>();
        factlyList.add("Select Faculty");
        factlyList.add("Faculty of Computer Networks and Communication");
        factlyList.add("Faculty of Computer Science");
        factlyList.add("Faculty of Information Systems");
        factlyList.add("Faculty of Computer Engineering");

        ArrayAdapter<String> academicAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, academicYears);
        academicAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAcademic.setAdapter(academicAdapter);

        ArrayAdapter<String> factlyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, factlyList);
        factlyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFactly.setAdapter(factlyAdapter);

        dbHelper = new DatabaseHelper(this);

        //  Kiểm tra nếu Intent có STUDENT_ID → chế độ sửa
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("STUDENT_ID")) {
            editingStudentId = intent.getIntExtra("STUDENT_ID", -1);
            loadStudentForEditing(editingStudentId);
        }

        // Add/ Update
        btnAdd.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String studentClass = etClass.getText().toString().trim();
            String dateOfBirth = etDateOfBirth.getText().toString().trim();
            String major = etMajor.getText().toString().trim();

            String selectedAcademic = spAcademic.getSelectedItem().toString();
            String selectedFactly = spFactly.getSelectedItem().toString();

            if (name.isEmpty() || studentClass.isEmpty() || dateOfBirth.isEmpty() || major.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedAcademic.equals("Select Academic Year") || selectedFactly.equals("Select Faculty")) {
                Toast.makeText(MainActivity.this, "Please select Academic Year and Faculty!", Toast.LENGTH_SHORT).show();
                return;
            }

            Student student = new Student();
            student.setName(name);
            student.setStudentClass(studentClass);
            student.setDateofBirth(dateOfBirth);
            student.setAcademicYear(selectedAcademic);
            student.setFactly(selectedFactly);
            student.setMajor(major);

            if (editingStudentId == -1) {
                // Thêm
                dbHelper.addStudent(student);
                Toast.makeText(MainActivity.this, "Student added successfully!", Toast.LENGTH_SHORT).show();
            } else {
                // Cập nhật sinh viên
                student.setId(editingStudentId);
                int result = dbHelper.updateStudent(student);
                if (result > 0)
                    Toast.makeText(MainActivity.this, "Student updated successfully!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Failed to update student!", Toast.LENGTH_SHORT).show();

                // Reset về chế độ thêm mới
                editingStudentId = -1;
                btnAdd.setText("Add Student");
            }

            clearInputFields();
        });

        // Nút xem danh sách
        btnView.setOnClickListener(v -> {
            Intent viewIntent = new Intent(MainActivity.this, StudentListActivity.class);
            startActivity(viewIntent);
        });
    }
    private void loadStudentForEditing(int id) {
        Student s = dbHelper.getStudentById(id);
        if (s != null) {
            etName.setText(s.getName());
            etClass.setText(s.getStudentClass());
            etDateOfBirth.setText(s.getDateofBirth());
            etMajor.setText(s.getMajor());

            setSpinnerSelection(spAcademic, s.getAcademicYear());
            setSpinnerSelection(spFactly, s.getFactly());

            btnAdd.setText("Update Student");
        }
    }


    private void setSpinnerSelection(Spinner spinner, String value) {
        ArrayAdapter adapter = (ArrayAdapter) spinner.getAdapter();
        int pos = adapter.getPosition(value);
        if (pos >= 0) spinner.setSelection(pos);
    }

    private void clearInputFields() {
        etName.setText("");
        etClass.setText("");
        etDateOfBirth.setText("");
        etMajor.setText("");
        spAcademic.setSelection(0);
        spFactly.setSelection(0);
    }
}

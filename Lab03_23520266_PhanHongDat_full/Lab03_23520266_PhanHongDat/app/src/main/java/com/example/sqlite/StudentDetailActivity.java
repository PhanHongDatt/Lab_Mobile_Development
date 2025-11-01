package com.example.sqlite;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class StudentDetailActivity extends AppCompatActivity {

    private TextView tvId, tvName, tvStudentClass, tvDateOfBirth, tvAcademicYear,
            tvFactly, tvMajor, tvEmail;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        // Ánh xạ view
        tvId = findViewById(R.id.tv_id);
        tvName = findViewById(R.id.tv_name);
        tvStudentClass = findViewById(R.id.tv_class);
        tvDateOfBirth = findViewById(R.id.tv_dob);
        tvAcademicYear = findViewById(R.id.tv_academic);
        tvFactly = findViewById(R.id.tv_factly);
        tvMajor = findViewById(R.id.tv_major);
        tvEmail = findViewById(R.id.tv_email);

        dbHelper = new DatabaseHelper(this);

        int studentId = getIntent().getIntExtra("STUDENT_ID", -1);
        if (studentId != -1) {
            Student s = dbHelper.getStudentById(studentId);
            if (s != null) {
                tvId.setText("ID: " + s.getId());
                tvName.setText("Name: " + s.getName());
                tvStudentClass.setText("Class: " + s.getStudentClass());
                tvDateOfBirth.setText("Date of Birth: " + s.getDateofBirth());
                tvAcademicYear.setText("Academic Year: " + s.getAcademicYear());
                tvFactly.setText("Faculty: " + s.getFactly());
                tvMajor.setText("Major: " + s.getMajor());
                tvEmail.setText("Email: " + s.getEmail());
            }
        }
    }
}

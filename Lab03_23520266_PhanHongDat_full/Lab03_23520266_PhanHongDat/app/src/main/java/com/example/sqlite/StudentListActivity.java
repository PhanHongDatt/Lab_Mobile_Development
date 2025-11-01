package com.example.sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class StudentListActivity extends AppCompatActivity {

    private RecyclerView rvStudentList;
    private DatabaseHelper dbHelper;
    private ArrayList<Student> students;
    private StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        rvStudentList = findViewById(R.id.rv_student_list);
        dbHelper = new DatabaseHelper(this);

        // Lấy danh sách sinh viên từ DB
        students = dbHelper.getAllStudents();

        adapter = new StudentAdapter(this, students, new StudentAdapter.OnStudentActionListener() {
            @Override
            public void onEdit(Student student) {
                // ✅ Mở lại MainActivity với dữ liệu student (để sửa)
                Intent intent = new Intent(StudentListActivity.this, MainActivity.class);
                intent.putExtra("STUDENT_ID", student.getId()); // ✅ KEY đúng
                startActivity(intent);
            }

            @Override
            public void onDelete(Student student) {
                showDeleteDialog(student);
            }
        });

        rvStudentList.setLayoutManager(new LinearLayoutManager(this));
        rvStudentList.setAdapter(adapter);
    }

    private void showDeleteDialog(Student student) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Student")
                .setMessage("Are you sure you want to delete " + student.getName() + "?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    dbHelper.deleteStudent(student);
                    students.remove(student);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(this, "Student deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Tự động refresh khi quay lại
        students.clear();
        students.addAll(dbHelper.getAllStudents());
        adapter.notifyDataSetChanged();
    }
}

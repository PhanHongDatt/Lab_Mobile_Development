package com.example.sqlite;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private Context context;
    private ArrayList<Student> studentList;
    private OnStudentActionListener listener; // callback xử lý sự kiện

    // Interface cho Edit / Delete
    public interface OnStudentActionListener {
        void onEdit(Student student);
        void onDelete(Student student);
    }

    // Constructor
    public StudentAdapter(Context context, ArrayList<Student> studentList, OnStudentActionListener listener) {
        this.context = context;
        this.studentList = studentList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_list_item, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = studentList.get(position);

        holder.tvName.setText(student.getName());
        holder.tvClass.setText("Class: " + student.getStudentClass());
        holder.tvMajor.setText("Major: " + student.getMajor());
        holder.tvDob.setText("DOB: " + student.getDateofBirth());

        // 🔹 Nhấn vào item để mở chi tiết sinh viên
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, StudentDetailActivity.class);
            intent.putExtra("STUDENT_ID", student.getId());
            context.startActivity(intent);
        });

        // 🔹 Nút Edit
        holder.btnEdit.setOnClickListener(v -> listener.onEdit(student));

        // 🔹 Nút Delete
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(student));
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public void updateData(ArrayList<Student> newList) {
        this.studentList = newList;
        notifyDataSetChanged();
    }

    // 🔹 ViewHolder
    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvClass, tvMajor, tvDob;
        ImageButton btnEdit, btnDelete;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name_item);
            tvClass = itemView.findViewById(R.id.tv_class_item);
            tvMajor = itemView.findViewById(R.id.tv_major_item);
            tvDob = itemView.findViewById(R.id.tv_dob_item);
            btnEdit = itemView.findViewById(R.id.btn_edit_item);
            btnDelete = itemView.findViewById(R.id.btn_delete_item);
        }
    }
}

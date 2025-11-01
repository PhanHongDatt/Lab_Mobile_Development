package com.example.lab03_23520266_phanhongdat_bai1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ContactAdapter contactAdapter;
    private DatabaseHandler db;
    private List<Contact> contactList;
    private EditText etName, etPhone;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHandler(this);
        recyclerView = findViewById(R.id.recyclerView);
        etName = findViewById(R.id.et_name);
        etPhone = findViewById(R.id.et_phonenumber);
        btnAdd = findViewById(R.id.btn_add);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        if (db.getContactsCount() == 0) {
            db.addContact(new Contact("Ravi", "9100000000"));
            db.addContact(new Contact("Srinivas", "9199999999"));
            db.addContact(new Contact("Tommy", "9522222222"));
            db.addContact(new Contact("Karthik", "9533333333"));
        }

        contactList = db.getAllContacts();

        contactAdapter = new ContactAdapter(contactList, new ContactAdapter.OnContactActionListener() {
            @Override
            public void onItemClick(Contact contact) {
                showEditDialog(contact);
            }

            @Override
            public void onItemLongClick(Contact contact) {
                // Khi nhấn giữ: xác nhận xóa
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Xóa liên hệ")
                        .setMessage("Bạn có chắc muốn xóa " + contact.getName() + "?")
                        .setPositiveButton("Xóa", (dialog, which) -> {
                            db.deleteContact(contact);
                            contactList = db.getAllContacts();
                            contactAdapter.updateData(contactList);
                            Toast.makeText(MainActivity.this, "Đã xóa " + contact.getName(), Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Hủy", null)
                        .show();
            }
        });

        recyclerView.setAdapter(contactAdapter);

        btnAdd.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();

            if (name.isEmpty() || phone.isEmpty()) {
                Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            db.addContact(new Contact(name, phone));
            contactList = db.getAllContacts();
            contactAdapter.updateData(contactList);
            etName.setText("");
            etPhone.setText("");
            Toast.makeText(MainActivity.this, "Đã thêm liên hệ mới!", Toast.LENGTH_SHORT).show();
        });
    }

    private void showEditDialog(Contact contact) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sửa thông tin liên hệ");

        final EditText inputName = new EditText(this);
        inputName.setHint("Tên");
        inputName.setText(contact.getName());

        final EditText inputPhone = new EditText(this);
        inputPhone.setHint("Số điện thoại");
        inputPhone.setText(contact.getPhoneNumber());

        android.widget.LinearLayout layout = new android.widget.LinearLayout(this);
        layout.setOrientation(android.widget.LinearLayout.VERTICAL);
        layout.setPadding(50, 20, 50, 10);
        layout.addView(inputName);
        layout.addView(inputPhone);
        builder.setView(layout);

        builder.setPositiveButton("Cập nhật", (dialog, which) -> {
            contact.setName(inputName.getText().toString().trim());
            contact.setPhoneNumber(inputPhone.getText().toString().trim());
            db.updateContact(contact);

            contactList = db.getAllContacts();
            contactAdapter.updateData(contactList);
            Toast.makeText(MainActivity.this, "Đã cập nhật liên hệ!", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Hủy", null);
        builder.show();
    }
}

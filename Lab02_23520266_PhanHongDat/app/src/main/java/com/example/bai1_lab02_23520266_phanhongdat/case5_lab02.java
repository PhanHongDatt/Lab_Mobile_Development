package com.example.bai1_lab02_23520266_phanhongdat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bai1_lab02_23520266_phanhongdat.Adapters.DishAdapter;
import com.example.bai1_lab02_23520266_phanhongdat.Adapters.ThumbnailAdapter;
import com.example.bai1_lab02_23520266_phanhongdat.models.Dish;
import com.example.bai1_lab02_23520266_phanhongdat.models.Thumbnail;
import java.util.ArrayList;

public class case5_lab02 extends AppCompatActivity {

    // Khai báo
    EditText etDishName;
    Spinner spinnerThumbnail;
    CheckBox cbPromotion;
    Button btnAddDish;
    GridView gridViewDishes;

    ArrayList<Dish> dishList;
    DishAdapter dishAdapter;
    ThumbnailAdapter thumbnailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case5_lab02);

        // Ánh xạ
        etDishName = findViewById(R.id.et_dish_name);
        spinnerThumbnail = findViewById(R.id.spinner_thumbnail);
        cbPromotion = findViewById(R.id.cb_promotion);
        btnAddDish = findViewById(R.id.btn_add_dish);
        gridViewDishes = findViewById(R.id.grid_view_dishes);

        // Thiết lập Spinner
        Thumbnail[] thumbnails = Thumbnail.values();
        thumbnailAdapter = new ThumbnailAdapter(this, thumbnails);
        spinnerThumbnail.setAdapter(thumbnailAdapter);

        // Thiết lập GridView
        dishList = new ArrayList<>();
        dishAdapter = new DishAdapter(this, dishList);
        gridViewDishes.setAdapter(dishAdapter);

        //Xử lý sự kiện
        btnAddDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDish();
            }
        });
    }

    private void addDish() {
        String name = etDishName.getText().toString().trim();
        Thumbnail selectedThumbnail = (Thumbnail) spinnerThumbnail.getSelectedItem();
        boolean isPromotion = cbPromotion.isChecked();

        if (name.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên món ăn", Toast.LENGTH_SHORT).show();
            return;
        }

        Dish newDish = new Dish(name, selectedThumbnail.getImageResID(), isPromotion);

        // Thêm vào danh sách và cập nhật GridView
        dishList.add(newDish);
        dishAdapter.notifyDataSetChanged();

        // Hiển thị thông báo và reset các trường
        Toast.makeText(this, "Added successfully", Toast.LENGTH_SHORT).show();
        resetFields();
    }

    private void resetFields() {
        etDishName.setText("");
        spinnerThumbnail.setSelection(0);
        cbPromotion.setChecked(false);
        etDishName.requestFocus();
    }
}
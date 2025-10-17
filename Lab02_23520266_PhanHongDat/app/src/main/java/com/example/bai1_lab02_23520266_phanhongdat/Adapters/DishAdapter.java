package com.example.bai1_lab02_23520266_phanhongdat.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.bai1_lab02_23520266_phanhongdat.R;
import com.example.bai1_lab02_23520266_phanhongdat.models.Dish;
import java.util.List;

public class DishAdapter extends ArrayAdapter<Dish> {

    public DishAdapter(Context context, List<Dish> dishes) {
        super(context, 0, dishes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item_dish, parent, false);
        }

        ImageView ivThumbnail = convertView.findViewById(R.id.iv_dish_thumbnail);
        TextView tvName = convertView.findViewById(R.id.tv_dish_name);
        ImageView ivStar = convertView.findViewById(R.id.iv_promotion_star);

        Dish dish = getItem(position);

        if (dish != null) {
            ivThumbnail.setImageResource(dish.getThumnailResId());
            tvName.setText(dish.getName());
            // Quan trọng: Phải gọi setSelected(true) để chữ chạy hoạt động
            tvName.setSelected(true);

            // Logic hiển thị ngôi sao khuyến mãi
            if (dish.isPromotion()) {
                ivStar.setVisibility(View.VISIBLE);
            } else {
                ivStar.setVisibility(View.GONE);
            }
        }
        return convertView;
    }
}
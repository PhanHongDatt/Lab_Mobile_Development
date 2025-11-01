package com.example.sqlite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class SelectableAdapter extends RecyclerView.Adapter<SelectableAdapter.SelectableViewHolder> {

    private Context context;
    private ArrayList<String> items;
    private int selectedPosition = -1; // Không chọn gì ban đầu
    private OnItemSelectedListener listener;

    public interface OnItemSelectedListener {
        void onItemSelected(String selectedValue);
    }

    public SelectableAdapter(Context context, ArrayList<String> items, OnItemSelectedListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SelectableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_thumbnail, parent, false);
        return new SelectableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectableViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String value = items.get(position);
        holder.tvItem.setText(value);

        // Đổi nền nếu được chọn
        if (selectedPosition == position) {
            holder.tvItem.setBackgroundResource(R.drawable.bg_item_selected);
            holder.tvItem.setTextColor(context.getResources().getColor(android.R.color.white));
        } else {
            holder.tvItem.setBackgroundResource(R.drawable.bg_item_unselected);
            holder.tvItem.setTextColor(context.getResources().getColor(android.R.color.black));
        }

        holder.itemView.setOnClickListener(v -> {
            selectedPosition = position;
            notifyDataSetChanged();
            if (listener != null) {
                listener.onItemSelected(value);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class SelectableViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem;
        public SelectableViewHolder(@NonNull View itemView) {
            super(itemView);
            //tvItem = itemView.findViewById(R.id.tv_item_selectable);
        }
    }
}

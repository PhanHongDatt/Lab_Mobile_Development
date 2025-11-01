package com.example.sqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ThumbnailAdapter extends ArrayAdapter<String> {

    private final LayoutInflater inflater;
    private final List<String> items;

    public ThumbnailAdapter(@NonNull Context context, @NonNull List<String> items) {
        super(context, 0, items);
        this.inflater = LayoutInflater.from(context);
        this.items = items;
    }

    // View hiển thị item được chọn (trên Spinner)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(R.layout.item_selected_thumbnail, parent, false);

        TextView tv = convertView.findViewById(R.id.tv_item_selected);
        tv.setText(items.get(position));

        return convertView;
    }

    // View hiển thị các item trong dropdown (khi người dùng bấm chọn)
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(R.layout.item_thumbnail, parent, false);

        TextView tv = convertView.findViewById(R.id.tv_item);
        tv.setText(items.get(position));

        return convertView;
    }
}

package com.example.bai1_lab02_23520266_phanhongdat.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.bai1_lab02_23520266_phanhongdat.R;
import com.example.bai1_lab02_23520266_phanhongdat.case5_lab02;
import com.example.bai1_lab02_23520266_phanhongdat.models.Thumbnail;
import java.util.List;

public class ThumbnailAdapter extends ArrayAdapter<Thumbnail> {

    public ThumbnailAdapter(Context context, List<Thumbnail> thumbnails) {
        super(context, 0, thumbnails);
    }

    public ThumbnailAdapter(case5_lab02 context, Thumbnail[] thumbnails) {
        super(context,0,thumbnails);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createView(position, convertView, parent, false);
    }

    // Hiển thị cho danh sách lựa chọn
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createView(position, convertView, parent, true);
    }

    private View createView(int position, View convertView, ViewGroup parent, boolean isDropDown) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.spinner_item_thumbnail, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.iv_spinner_image);
        TextView textView = convertView.findViewById(R.id.tv_spinner_name);

        Thumbnail thumbnail = getItem(position);

        if (thumbnail != null) {
            textView.setText(thumbnail.getName());
            if (isDropDown) {
                //hiển thị hình trong danh sách dropdown
                imageView.setImageResource(thumbnail.getImageResID());
                imageView.setVisibility(View.VISIBLE);
            } else {
                // Ẩn hình khi Spinner đã đóng lại
                imageView.setVisibility(View.GONE);
            }
        }
        return convertView;
    }
}
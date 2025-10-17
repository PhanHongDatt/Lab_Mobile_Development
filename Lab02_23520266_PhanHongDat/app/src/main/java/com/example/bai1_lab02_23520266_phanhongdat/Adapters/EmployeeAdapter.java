package com.example.bai1_lab02_23520266_phanhongdat.Adapters;
import android.app.Activity;
import android.widget.ArrayAdapter;

import com.example.bai1_lab02_23520266_phanhongdat.R;
import com.example.bai1_lab02_23520266_phanhongdat.models.module_employees;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EmployeeAdapter extends ArrayAdapter<module_employees> {
    private Activity context;

    public EmployeeAdapter(Activity context, int layoutID, List<module_employees>
            objects) {
        super(context, layoutID, objects);
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        if (convertView == null) {
            convertView =
                    LayoutInflater.from(context).inflate(R.layout.item_employee, null,
                            false);
        }

        // Get item
        module_employees employee = getItem(position);

        // Get view
        TextView tvFullName = (TextView)
                convertView.findViewById(R.id.item_employee_tv_fullname);
        TextView tvPosition = (TextView)
                convertView.findViewById(R.id.item_employee_tv_position);
        ImageView ivManager = (ImageView)
                convertView.findViewById(R.id.item_employee_iv_manager);
        LinearLayout llParent = (LinearLayout)
                convertView.findViewById(R.id.item_employee_ll_parent);

        // Set fullname
        if (employee.getFullName()!=null) {
            tvFullName.setText(employee.getFullName());
        }
        else tvFullName.setText("");

        // If this is a manager -> show icon manager. Otherwise, show Staff in
        if (employee.isManager())
        {
            ivManager.setVisibility(View.VISIBLE);
            tvPosition.setVisibility(View.GONE);
        }
        else
        {
            ivManager.setVisibility(View.GONE);
            tvPosition.setVisibility(View.VISIBLE);
            tvPosition.setText(context.getString(R.string.staff));
        }

        // Show different color backgrounds for 2 continuous employees
        if (position%2==0)
        {
            llParent.setBackgroundResource(R.color.white);
        }
        else
        {
        llParent.setBackgroundResource(R.color.normal_blue);
        }
        return convertView;
    }
}
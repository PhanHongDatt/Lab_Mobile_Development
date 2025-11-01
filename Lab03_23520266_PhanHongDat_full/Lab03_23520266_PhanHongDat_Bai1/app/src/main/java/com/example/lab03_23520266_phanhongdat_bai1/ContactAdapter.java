package com.example.lab03_23520266_phanhongdat_bai1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    public interface OnContactActionListener {
        void onItemClick(Contact contact);       // Khi nhấn -> sửa
        void onItemLongClick(Contact contact);   // Khi giữ lâu -> xóa
    }

    private List<Contact> contactList;
    private final OnContactActionListener listener;

    public ContactAdapter(List<Contact> contactList, OnContactActionListener listener) {
        this.contactList = contactList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        holder.tvName.setText(contact.getName());
        holder.tvPhone.setText(contact.getPhoneNumber());

        // Sự kiện click
        holder.itemView.setOnClickListener(v -> listener.onItemClick(contact));

        // Sự kiện nhấn giữ
        holder.itemView.setOnLongClickListener(v -> {
            listener.onItemLongClick(contact);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    //
    static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPhone;
        ContactViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
        }
    }

    public void updateData(List<Contact> newList) {
        contactList = newList;
        notifyDataSetChanged();
    }
}

package com.example.packettracer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.packettracer.model.BordoreauQRDTO;

import java.util.List;

public class BordoreauAdapterForDash extends ArrayAdapter<BordoreauQRDTO> {
    public BordoreauAdapterForDash(Context context, int resource, List<BordoreauQRDTO> objects) {
        super(context, resource, objects);
    }

    static class ViewHolder {
        TextView listId;
        TextView listDate;
        TextView listStatus;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_view, parent, false);
            holder = new ViewHolder();
            holder.listId = convertView.findViewById(R.id.listId);
            holder.listDate = convertView.findViewById(R.id.listDate);
            holder.listStatus = convertView.findViewById(R.id.listTime);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BordoreauQRDTO item = getItem(position);
        if (item != null) {
            holder.listId.setText(item.getNumeroBordoreau().toString());

            holder.listDate.setText(item.getDate());

            holder.listStatus.setText(item.getStatus().toString());
        }

        return convertView;
    }
}

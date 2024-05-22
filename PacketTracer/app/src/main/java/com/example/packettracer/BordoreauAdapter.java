package com.example.packettracer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.packettracer.model.BordoreauQRDTO;
import com.example.packettracer.model.PacketStatus;

import java.util.List;

public class BordoreauAdapter extends ArrayAdapter<BordoreauQRDTO> {
    public BordoreauAdapter(Context context, int resource, List<BordoreauQRDTO> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_view, parent, false);
        }

        BordoreauQRDTO item = getItem(position);
        TextView listId = convertView.findViewById(R.id.listId);
        TextView listDate = convertView.findViewById(R.id.listDate);
        TextView listStatus = convertView.findViewById(R.id.listTime);

        assert item != null;
        listId.setText(String.valueOf(item.getNumeroBordoreau()));
        listDate.setText(item.getDate());
        listStatus.setText(item.getStatus().toString());

        return convertView;
    }
}

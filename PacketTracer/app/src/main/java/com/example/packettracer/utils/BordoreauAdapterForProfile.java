package com.example.packettracer.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.packettracer.R;
import com.example.packettracer.model.Bordoreau;
import com.example.packettracer.model.Packet;

import java.time.LocalDateTime;
import java.util.List;

public class BordoreauAdapterForProfile extends ArrayAdapter<Bordoreau> {

    public BordoreauAdapterForProfile(Context context, List<Bordoreau> bordoreaus) {
        super(context, 0, bordoreaus);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Bordoreau bordoreau = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_view, parent, false);
        }

        TextView tvBordoreauStatus = convertView.findViewById(R.id.listTime);
        TextView tvBordoreauId = convertView.findViewById(R.id.listId);
        TextView tvBordoreauDetail = convertView.findViewById(R.id.listDate);

        if (bordoreau != null) {
            tvBordoreauStatus.setText(bordoreau.getStatus());
            tvBordoreauId.setText(String.valueOf(bordoreau.getNumeroBordoreau()));
            LocalDateTime d = LocalDateTime.parse(bordoreau.getDate());
            String detail = "Date: " + d.toLocalDate().toString() + "\n" +
                    "Livreur: " + bordoreau.getStringLivreur() + "\n" +
                    "Nombres de Packet: " + bordoreau.getPackets().size()  + "\n" ;
            tvBordoreauDetail.setText(detail);
        }
        return convertView;
    }

    private String formatPackets(List<Packet> packets) {
        if (packets == null || packets.isEmpty()) {
            return "No packets";
        }
        StringBuilder builder = new StringBuilder();
        for (Packet packet : packets) {
            if (builder.length() > 0) {
                builder.append(", ");
            }
            builder.append(packet.toString()); // Ensure Packet has a meaningful toString implementation
        }
        return builder.toString();
    }

}


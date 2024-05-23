/*
package com.example.packettracer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.packettracer.model.PacketDetailDTO;

import java.util.List;

public class PacketDetailAdapter extends ArrayAdapter<PacketDetailDTO> {
    public PacketDetailAdapter(Context context, List<PacketDetailDTO> packets) {
        super(context, 0, packets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.packet_detail_item_view, parent, false);
        }
        PacketDetailDTO packet = getItem(position);

        TextView tvNumeroBL = convertView.findViewById(R.id.tvNumeroBL);
        TextView tvCodeClient = convertView.findViewById(R.id.tvCodeClient);
        TextView tvNbrColis = convertView.findViewById(R.id.tvNbrColis);
        TextView tvNbrSachets = convertView.findViewById(R.id.tvNbrSachets);

        if (packet != null) {
            tvNumeroBL.setText(String.valueOf(packet.getNumeroBL()));
            tvCodeClient.setText(packet.getCodeClient());
            tvNbrColis.setText(String.valueOf(packet.getNbrColis()));
            tvNbrSachets.setText(String.valueOf(packet.getNbrSachets()));
        }
        return convertView;
    }
}
*/

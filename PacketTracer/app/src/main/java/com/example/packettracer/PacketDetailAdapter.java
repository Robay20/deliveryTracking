package com.example.packettracer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.packettracer.model.PacketDetailDTO;

import java.util.List;

public class PacketDetailAdapter extends RecyclerView.Adapter<PacketDetailAdapter.ViewHolder> {
    private List<PacketDetailDTO> packets;
    private LayoutInflater inflater;

    public PacketDetailAdapter(Context context, List<PacketDetailDTO> packets) {
        this.inflater = LayoutInflater.from(context);
        this.packets = packets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.packet_detail_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PacketDetailDTO packet = packets.get(position);
        holder.tvNumeroBL.setText(String.valueOf(packet.getNumeroBL()));
        holder.tvCodeClient.setText(packet.getCodeClient());
        holder.tvNbrColis.setText(String.valueOf(packet.getNbrColis()));
        holder.tvNbrSachets.setText(String.valueOf(packet.getNbrSachets()));
    }

    @Override
    public int getItemCount() {
        return packets.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNumeroBL, tvCodeClient, tvNbrColis, tvNbrSachets;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumeroBL = itemView.findViewById(R.id.tvNumeroBL);
            tvCodeClient = itemView.findViewById(R.id.tvCodeClient);
            tvNbrColis = itemView.findViewById(R.id.tvNbrColis);
            tvNbrSachets = itemView.findViewById(R.id.tvNbrSachets);
        }
    }
}

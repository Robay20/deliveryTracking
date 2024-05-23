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
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public PacketDetailAdapter(Context context, List<PacketDetailDTO> packets, OnItemClickListener onItemClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.packets = packets;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.packet_detail_item_view, parent, false);
        return new ViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PacketDetailDTO packet = packets.get(position);
        holder.tvNumeroBL.setText(String.valueOf(packet.getNumeroBL()));
        holder.tvCodeClient.setText(packet.getCodeClient());
        holder.tvNbrColis.setText(String.valueOf(packet.getNbrColis()));
        holder.tvNbrSachets.setText(String.valueOf(packet.getNbrSachets()));
        holder.tvStatus.setText(packet.getStatus().toString());
    }

    @Override
    public int getItemCount() {
        return packets.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvNumeroBL, tvCodeClient, tvNbrColis, tvNbrSachets, tvStatus;
        OnItemClickListener onItemClickListener;

        public ViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            tvNumeroBL = itemView.findViewById(R.id.tvNumeroBL);
            tvCodeClient = itemView.findViewById(R.id.tvCodeClient);
            tvNbrColis = itemView.findViewById(R.id.tvNbrColis);
            tvNbrSachets = itemView.findViewById(R.id.tvNbrSachets);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }
}

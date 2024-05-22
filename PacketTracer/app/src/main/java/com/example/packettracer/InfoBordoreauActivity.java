package com.example.packettracer;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.packettracer.model.BordoreauQRDTO;

public class InfoBordoreauActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PacketDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_bordoreau);  // Ensure this layout is correctly referenced

        // Deserialize BordoreauQRDTO from JSON passed through intent
        String bordoreauJson = getIntent().getStringExtra("BordoreauData");
        BordoreauQRDTO bordoreau = BordoreauQRDTO.fromJson(bordoreauJson);  // Ensure Gson is properly set up to handle this

        // Initialize TextViews
        TextView tvNumeroBordoreau = findViewById(R.id.tvNumeroBordoreau);
        TextView tvDate = findViewById(R.id.tvDate);
        TextView tvStringLivreur = findViewById(R.id.tvStringLivreur);
        TextView tvCodeSecteur = findViewById(R.id.tvCodeSecteur);
        TextView tvStatus = findViewById(R.id.tvStatus);

        // Set text to TextViews
        tvNumeroBordoreau.setText(String.valueOf(bordoreau.getNumeroBordoreau()));
        tvDate.setText(bordoreau.getDate());
        tvStringLivreur.setText(bordoreau.getStringLivreur());
        tvCodeSecteur.setText(String.valueOf(bordoreau.getCodeSecteur()));
        tvStatus.setText(bordoreau.getStatus().toString());

        // Setup ListView and Adapter for PacketDetails
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new PacketDetailAdapter(this, bordoreau.getPackets());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}

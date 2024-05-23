package com.example.packettracer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.packettracer.model.BordoreauQRDTO;
import com.example.packettracer.model.PacketDetailDTO;
import com.example.packettracer.model.PacketStatus;
import com.example.packettracer.utils.BordoreauApi;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;

public class InfoBordoreauActivity extends AppCompatActivity implements PacketDetailAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private PacketDetailAdapter adapter;
    private BordoreauQRDTO bordoreau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_bordoreau);

        // Deserialize BordoreauQRDTO from JSON passed through intent
        String bordoreauJson = getIntent().getStringExtra("BordoreauData");
        bordoreau = BordoreauQRDTO.fromJson(bordoreauJson);

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
        adapter = new PacketDetailAdapter(this, bordoreau.getPackets(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Button
        Button btStart = findViewById(R.id.btStart);
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update status to IN_TRANSIT
                bordoreau.setStatus(PacketStatus.IN_TRANSIT);
                for (PacketDetailDTO packet : bordoreau.getPackets()) {
                    packet.setStatus(PacketStatus.IN_TRANSIT);
                }

                sendBordoreauToBackend(bordoreau);
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        // Update the status of the clicked packet to DONE
        bordoreau.getPackets().get(position).setStatus(PacketStatus.DONE);
        adapter.notifyItemChanged(position);

        // Optionally, you can send the updated packet status to the backend here
        // sendPacketUpdateToBackend(bordoreau.getPackets().get(position));
    }

    // Method to send the modified BordoreauQRDTO object to the backend
    private void sendBordoreauToBackend(BordoreauQRDTO bordoreau) {
        Log.d("TAG", "'sendBordoreauToBackend' Status updated to IN_TRANSIT Sending to backend...");

        // Ensure the status is set to IN_TRANSIT

        // Base URL of your backend server
        String baseUrl = "http://192.168.1.111:8080/";

        // Create Retrofit instance
        Retrofit retrofit = RetrofitClient.getClient(baseUrl);

        // Create API service
        BordoreauApi service = retrofit.create(BordoreauApi.class);

        // Make the network call
        Call<BordoreauQRDTO> call = service.updateBordoreau(bordoreau.getNumeroBordoreau(), bordoreau);

        call.enqueue(new retrofit2.Callback<BordoreauQRDTO>() {
            @Override
            public void onResponse(Call<BordoreauQRDTO> call, retrofit2.Response<BordoreauQRDTO> response) {
                if (response.isSuccessful()) {
                    Log.d("TAG", "Bordoreau updated successfully: " + response.body());
                } else {
                    Log.e("TAG", "Error updating bordoreau: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<BordoreauQRDTO> call, Throwable t) {
                Log.e("TAG", "Failed to update bordoreau: " + t.getMessage());
            }
        });
    }

}

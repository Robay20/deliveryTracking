package com.example.packettracer;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.packettracer.model.BordoreauQRDTO;
import com.example.packettracer.model.Packet;
import com.example.packettracer.model.PacketDetailDTO;
import com.example.packettracer.model.PacketStatus;
import com.example.packettracer.model.TransfertRequest;
import com.example.packettracer.utils.BordoreauApi;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InfoBordoreauActivity extends AppCompatActivity implements PacketDetailAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private PacketDetailAdapter adapter;
    private BordoreauQRDTO bordoreau;
    private String currentDriverId ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_bordoreau);



        // Deserialize BordoreauQRDTO from JSON passed through intent
        String bordoreauJson = getIntent().getStringExtra("BordoreauData");
        bordoreau = BordoreauQRDTO.fromJson(bordoreauJson);

        currentDriverId=getIntent().getStringExtra("cinDriver");


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

                tvNumeroBordoreau.setText(String.valueOf(bordoreau.getNumeroBordoreau()));
                tvDate.setText(bordoreau.getDate());
                tvStringLivreur.setText(bordoreau.getStringLivreur());
                tvCodeSecteur.setText(String.valueOf(bordoreau.getCodeSecteur()));
                tvStatus.setText(bordoreau.getStatus().toString());

                // Setup ListView and Adapter for PacketDetails
                recyclerView = findViewById(R.id.recyclerView);
                adapter = new PacketDetailAdapter(InfoBordoreauActivity.this, bordoreau.getPackets(), InfoBordoreauActivity.this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(InfoBordoreauActivity.this));

            }
        });

        Button btTransfertto = findViewById(R.id.btTransfertto);
        btTransfertto.setOnClickListener(v -> showQrCodeDialog(currentDriverId,bordoreau.getNumeroBordoreau()));


    }

    private void showQrCodeDialog(String data,Long a) {


        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_qr_code);

        ImageView qrCodeImageView = dialog.findViewById(R.id.qrCodeImageView);
        Button closeButton = dialog.findViewById(R.id.closeButton);

        data= data +","+ a.toString();
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(data, BarcodeFormat.QR_CODE, 400, 400);
            qrCodeImageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        closeButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
    @Override
    public void onItemClick(int position) {
        PacketDetailDTO packetDetail = bordoreau.getPackets().get(position);
        if(packetDetail.getStatus()!=PacketStatus.DONE)
            showPacketDialog(position);
    }

    private void showPacketDialog(int position) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_packet);

        Button doneButton = dialog.findViewById(R.id.buttonDone);
        Button closeButton = dialog.findViewById(R.id.closeButton);

        closeButton.setOnClickListener(v -> dialog.dismiss());


        doneButton.setOnClickListener(v -> {
            PacketDetailDTO packetDetail = bordoreau.getPackets().get(position);
            createTransfert(packetDetail.getCodeClient(),currentDriverId,Collections.singleton(packetDetail.getNumeroBL()));
            updatePacketStatus(bordoreau.getPackets().get(position).getNumeroBL(), PacketStatus.DONE);
            dialog.dismiss();
                });

        /*// Handle Transfert button click
        transfertButton.setOnClickListener(v -> {
            PacketDetailDTO packetDetail = bordoreau.getPackets().get(position);
            Long packetId = packetDetail.getNumeroBL();
            // Replace 'currentDriverId' and 'codeSecteur' with appropriate values
            createTransfert(currentDriverId, bordoreau.getCodeSecteur(), Collections.singleton(packetId));
            dialog.dismiss();
        });

        // Handle Done button click
        doneButton.setOnClickListener(v -> {
            // Update the status of the clicked packet to DONE
            bordoreau.getPackets().get(position).setStatus(PacketStatus.DONE);
            adapter.notifyItemChanged(position);

            // Call the backend API to update the packet status
            updatePacketStatus(bordoreau.getPackets().get(position).getNumeroBL(), PacketStatus.DONE);
            dialog.dismiss();
        });
*/
        dialog.show();
    }


    // Method to send the modified BordoreauQRDTO object to the backend
    private void sendBordoreauToBackend(BordoreauQRDTO bordoreau) {
        Log.d("TAG", "'sendBordoreauToBackend' Status updated to IN_TRANSIT Sending to backend...");

        // Ensure the status is set to IN_TRANSIT

        // Base URL of your backend server
        String baseUrl = "http://192.168.43.207:8080/";

        // Create Retrofit instance
        Retrofit retrofit = RetrofitClient.getClient(baseUrl);

        // Create API service
        BordoreauApi service = retrofit.create(BordoreauApi.class);

        // Make the network call
        Call<BordoreauQRDTO> call = service.updateBordoreau(bordoreau.getNumeroBordoreau(), bordoreau);

        call.enqueue(new Callback<BordoreauQRDTO>() {
            @Override
            public void onResponse(Call<BordoreauQRDTO> call, Response<BordoreauQRDTO> response) {
                if (response.isSuccessful()) {
                } else {
                    Log.e("TAG", "Error updating bordoreau: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<BordoreauQRDTO> call, Throwable t) {
                Log.e("TAG", "Failed to update bordoreau: " + t.getMessage());
            }
        });
        List<PacketDetailDTO> packetIds = bordoreau.getPackets();
        Set<Long> ids = new HashSet<>();  // Initialize the set

        for (PacketDetailDTO packetDetail : packetIds) {
            ids.add(packetDetail.getNumeroBL());
        }
        createTransfert(currentDriverId, bordoreau.getCodeSecteur().toString(), ids);

    }

    private void createTransfert(String currentDriverId, String codeSecteur, Set<Long> ids) {
        String baseUrl = "http://192.168.43.207:8080/";

        Retrofit retrofit = RetrofitClient.getClient(baseUrl);
        BordoreauApi service = retrofit.create(BordoreauApi.class);

        TransfertRequest transfertRequest = new TransfertRequest();
        transfertRequest.setCodeSecteur(codeSecteur.toString());
        transfertRequest.setIdDriver(currentDriverId);
        transfertRequest.setPackets(ids);// Include packets

        Call<Void> call = service.createTransfert(transfertRequest);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("TAG", "Transfert created successfully");
                } else {
                    Log.e("TAG", "Error creating transfert: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("TAG", "Failed to create transfert: " + t.getMessage());
            }
        });
    }

    private void updatePacketStatus(Long packetId, PacketStatus status) {
        String baseUrl = "http://192.168.43.207:8080/";

        Retrofit retrofit = RetrofitClient.getClient(baseUrl);
        BordoreauApi service = retrofit.create(BordoreauApi.class);

        Call<Void> call = service.updatePacketStatus(packetId, status);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("TAG", "Packet status updated successfully");
                } else {
                    Log.e("TAG", "Error updating packet status: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("TAG", "Failed to update packet status: " + t.getMessage());
            }
        });
    }
}

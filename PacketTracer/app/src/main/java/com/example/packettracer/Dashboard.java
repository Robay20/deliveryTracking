package com.example.packettracer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.example.packettracer.CaptureAct;
import com.example.packettracer.R;
import com.example.packettracer.model.BordoreauQRDTO;
import com.example.packettracer.model.PacketDetailDTO;
import com.example.packettracer.model.PacketStatus;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Dashboard extends AppCompatActivity {
    private ImageView btn_scan;
    private ListView listView;
    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private Set<BordoreauQRDTO> bordoreauSet = new HashSet<>();
    private ArrayAdapter<BordoreauQRDTO> adapter;

    private ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            processScannedData(result.getContents());
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        listView = findViewById(R.id.list_packet);
        btn_scan = findViewById(R.id.btn_qr_code);
        btn_scan.setOnClickListener(this::scanCode);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>(bordoreauSet));
        listView.setAdapter(adapter);
    }

    public void scanCode(View view) {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private void processScannedData(String data) {
        BordoreauQRDTO bordoreau = parseBordoreauData(data);
        if (bordoreau == null) {
            Toast.makeText(Dashboard.this, "Invalid QR code format.", Toast.LENGTH_LONG).show();
            return;
        }

        if (!verifyDriver(bordoreau.getStringLivreur())) {
            Toast.makeText(Dashboard.this, "You are not the driver", Toast.LENGTH_LONG).show();
            return;
        }

        fetchBordoreauData(bordoreau.getNumeroBordoreau());
    }

    private boolean verifyDriver(String qrDriverId) {
        String currentDriverId = getIntent().getStringExtra("cinDriver");
        return qrDriverId.equals(currentDriverId);
    }

    private void fetchBordoreauData(Long bordoreauId) {
        String url = "http://192.168.43.207:8080/api/bordoreaux/" + bordoreauId + "/qr";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        executorService.execute(() -> {
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String jsonData = response.body().string();
                    JSONObject jsonObject = new JSONObject(jsonData);
                    BordoreauQRDTO responseBordoreau = parseJSONToBordoreau(jsonObject);
                    if (responseBordoreau != null) {
                        mainHandler.post(() -> {
                            bordoreauSet.add(responseBordoreau);
                            updateListView();
                        });
                    }
                } else {
                    mainHandler.post(() -> Toast.makeText(Dashboard.this, "Bordoreau not found or error fetching data", Toast.LENGTH_SHORT).show());
                }
            } catch (Exception e) {
                e.printStackTrace();
                mainHandler.post(() -> Toast.makeText(Dashboard.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }
        });
    }

    private BordoreauQRDTO parseJSONToBordoreau(JSONObject jsonObject) throws JSONException {
        Long numeroBordoreau = jsonObject.getLong("numeroBordoreau");
        String date = jsonObject.getString("date");
        String stringLivreur = jsonObject.getString("stringLivreur");
        Long codeSecteur = jsonObject.getLong("codeSecteur");

        List<PacketDetailDTO> packets = new ArrayList<>();
        JSONArray packetsArray = jsonObject.getJSONArray("packets");
        for (int i = 0; i < packetsArray.length(); i++) {
            JSONObject packetObject = packetsArray.getJSONObject(i);
            Long numeroBL = packetObject.getLong("numeroBL");
            String codeClient = packetObject.getString("codeClient");
            int nbrColis = packetObject.getInt("nbrColis");
            int nbrSachets = packetObject.getInt("nbrSachets");
            PacketStatus status = PacketStatus.fromString(packetObject.getString("status"));

            packets.add(new PacketDetailDTO(numeroBL, codeClient, nbrColis, nbrSachets,status));
        }

        return new BordoreauQRDTO(numeroBordoreau, date, stringLivreur, codeSecteur, packets);
    }

    private BordoreauQRDTO parseBordoreauData(String data) {
        // Assuming the data format: {500001, 220824, 100001, 200001, {{300001, 400002, 2,1}, {300002, 400002, 0,3}, {300003, 400003, 10,1}}}
        try {
            // Removing the outer braces and splitting the top-level data
            String trimmedData = data.substring(1, data.length() - 1);
            String[] parts = trimmedData.split(", ", 5);  // Split into 5 parts

            Long numeroBordoreau = Long.parseLong(parts[0].trim());
            String date = parts[1].trim();
            String stringLivreur = parts[2].trim();
            Long codeSecteur = Long.parseLong(parts[3].trim());

            String packetsData = parts[4].trim();
            packetsData = packetsData.substring(2, packetsData.length() - 2); // Remove {{ and }}
            String[] packetParts = packetsData.split("}, \\{");

            List<PacketDetailDTO> packets = new ArrayList<>();
            for (String packet : packetParts) {
                String[] details = packet.split(", ");
                Long numeroBL = Long.parseLong(details[0].trim());
                String codeClient = (details[1].trim());
                int nbrColis = Integer.parseInt(details[2].trim());
                int nbrSachets = Integer.parseInt(details[3].trim());

                packets.add(new PacketDetailDTO(numeroBL, codeClient, nbrColis, nbrSachets,null));
            }

            return new BordoreauQRDTO(numeroBordoreau, date, stringLivreur, codeSecteur, packets);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }


    private void updateListView() {
        adapter.clear();
        adapter.addAll(bordoreauSet);
        adapter.notifyDataSetChanged();
    }
}

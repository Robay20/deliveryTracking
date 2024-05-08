package com.example.packettracer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

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

import com.example.packettracer.model.AppDatabase;
import com.example.packettracer.model.BordoreauDao;

public class Dashboard extends AppCompatActivity {
    private AppDatabase db;
    private BordoreauDao bordoreauDao;

    String currentDriverId ;
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

        db = AppDatabase.getDatabase(this);
        bordoreauDao = db.bordoreauDao();

        currentDriverId=getIntent().getStringExtra("cinDriver");

        listView = findViewById(R.id.list_packet);
        btn_scan = findViewById(R.id.btn_qr_code);
        btn_scan.setOnClickListener(this::scanCode);
        adapter = new BordoreauAdapter(this, R.layout.list_item_view, new ArrayList<>(bordoreauSet));
        listView.setAdapter(adapter);

        loadBordoreauData();
    }

    private void loadBordoreauData() {
        executorService.execute(() -> {
            List<BordoreauQRDTO> bordoreaus = bordoreauDao.getAll();
            mainHandler.post(() -> {
                bordoreauSet.addAll(bordoreaus);
                updateListView();
                for (BordoreauQRDTO bordoreau : bordoreaus) {
                    Log.d("LoadData", "Loaded Bordoreau: " + bordoreau.toString());
                }
            });
        });
    }

    private void saveBordoreauData(BordoreauQRDTO bordoreau) {
        executorService.execute(() -> {
            bordoreauDao.insertAll(bordoreau);
        });
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
                        bordoreauDao.insertAll(responseBordoreau);
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
        PacketStatus status = PacketStatus.fromString(jsonObject.getString("status"));


        List<PacketDetailDTO> packets = new ArrayList<>();
        JSONArray packetsArray = jsonObject.getJSONArray("packets");
        for (int i = 0; i < packetsArray.length(); i++) {
            JSONObject packetObject = packetsArray.getJSONObject(i);
            Long numeroBL = packetObject.getLong("numeroBL");
            String codeClient = packetObject.getString("codeClient");
            int nbrColis = packetObject.getInt("nbrColis");
            int nbrSachets = packetObject.getInt("nbrSachets");

            packets.add(new PacketDetailDTO(numeroBL, codeClient, nbrColis, nbrSachets));
        }

        return new BordoreauQRDTO(numeroBordoreau,status, date, stringLivreur, codeSecteur, packets);
    }

    private BordoreauQRDTO parseBordoreauData(String data) {
        Log.d("ScanData", "Scanned QR Data: " + data);

        try {
            // First, clean the data by removing curly braces and all whitespace
            String cleanedData = data.replaceAll("\\s+", ""); // remove all white spaces
            cleanedData = cleanedData.replaceAll("\\{", "").replaceAll("\\}", "");
            String[] parts = cleanedData.split(",", 5); // Split into 5 parts directly

            Long numeroBordoreau = Long.parseLong(parts[0]);
            String date = parts[1];
            String stringLivreur = parts[2];
            Long codeSecteur = Long.parseLong(parts[3]);
            String packetsData = parts[4];

            List<PacketDetailDTO> packets = new ArrayList<>();
            String[] packetParts = packetsData.split(",(?=\\d)"); // Split at commas followed by a digit

            for (int i = 0; i < packetParts.length; i += 4) {
                Long numeroBL = Long.parseLong(packetParts[i].replaceAll(",", "").trim());
                String codeClient = packetParts[i + 1].replaceAll(",", "").trim();
                int nbrColis = Integer.parseInt(packetParts[i + 2].replaceAll(",", "").trim());
                int nbrSachets = Integer.parseInt(packetParts[i + 3].replaceAll(",", "").trim());

                packets.add(new PacketDetailDTO(numeroBL, codeClient, nbrColis, nbrSachets));
            }

            return new BordoreauQRDTO(numeroBordoreau, null, date, stringLivreur, codeSecteur, packets);
        } catch (Exception e) {
            Log.e("ParseError", "Error parsing bordoreau data", e);
            return null;
        }
    }


    private void updateListView() {
        mainHandler.post(() -> {
            adapter.clear();
            adapter.addAll(bordoreauSet);
            adapter.notifyDataSetChanged();
        });
    }
}

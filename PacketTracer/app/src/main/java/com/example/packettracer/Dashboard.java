package com.example.packettracer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.example.packettracer.model.Driver;
import com.example.packettracer.model.Packet;
import com.example.packettracer.utils.JsonParser;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class Dashboard extends AppCompatActivity {
    private Button btn_scan;
    private ListView listView;
    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private List<Packet> driverList = new ArrayList<>();
    private ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            String driverId = result.getContents(); // Assuming the QR code contains the driver ID directly
            fetchDriverData(driverId);
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        listView = findViewById(R.id.list_packet);
        btn_scan = findViewById(R.id.btn_qr_code);
        btn_scan.setOnClickListener(this::scanCode);
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

    private void fetchDriverData(String driverId) {
        OkHttpClient client = new OkHttpClient();
        String url = "http://192.168.43.207:8080/api/drivers/" + driverId;
        Request request = new Request.Builder().url(url).build();

        executorService.execute(() -> {
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String jsonData = response.body().string();

                    Log.d("Dashboard", "Raw JSON data: " + jsonData);

                    JSONObject jsonObject = new JSONObject(jsonData);
                    Driver driver = JsonParser.parseDriver(jsonObject);
                    driverList.clear();
                    driverList.addAll(driver.getPackets());

                    mainHandler.post(this::updateListView);
                } else {
                    mainHandler.post(() -> Toast.makeText(Dashboard.this, "Driver not found or error fetching data", Toast.LENGTH_SHORT).show());
                }
            } catch (Exception e) {
                e.printStackTrace();
                mainHandler.post(() -> Toast.makeText(Dashboard.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }
        });
    }

    private void updateListView() {
        ArrayAdapter<Packet> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, driverList);
        listView.setAdapter(adapter);
    }
}

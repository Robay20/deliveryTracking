package com.example.packettracer;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.example.packettracer.model.Bordoreau;
import com.example.packettracer.utils.BordoreauAdapterForProfile;
import com.google.gson.Gson;
import android.widget.TextView;

import com.example.packettracer.model.Driver;

public class InfoProfileActivity extends AppCompatActivity {

    private String currentDriverId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_profile);

        currentDriverId = getIntent().getStringExtra("idProfile");

        if (currentDriverId != null && !currentDriverId.isEmpty()) {
            fetchDriverData(currentDriverId);
        } else {
            Toast.makeText(this, "No Driver ID provided", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchDriverData(String driverId) {
        OkHttpClient client = new OkHttpClient();
        String url = "http://192.168.1.111:8080/api/drivers/" + driverId + "/mobile";  // Ensure the URL is accessible

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("HTTP", "Error fetching driver data", e);
                runOnUiThread(() -> Toast.makeText(InfoProfileActivity.this, "Failed to fetch data: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String responseData = response.body().string();
                    runOnUiThread(() -> updateUI(responseData));
                } else {
                    runOnUiThread(() -> Toast.makeText(InfoProfileActivity.this, "Error fetching data: " + response.message(), Toast.LENGTH_LONG).show());
                }
            }
        });
    }

    private void updateUI(String data) {
        Gson gson = new Gson();
        Driver driver = gson.fromJson(data, Driver.class);

        // Set text views with driver information
        // Store TextView references in variables
        EditText tvDriverFirstName = findViewById(R.id.etFirstName);
        EditText tvDriverLastName = findViewById(R.id.etLastName);
        EditText tvDriverEmail = findViewById(R.id.etEmail);
        EditText tvDateOfBirth = findViewById(R.id.etDOB);
        EditText tvLicenseNumber = findViewById(R.id.etLicenseNumber);
        EditText tvLicensePlate = findViewById(R.id.etLicensePlate);

// Append driver information to the existing text in each TextView
        tvDriverFirstName.setText(driver.getFirstName());
        tvDriverLastName.setText(driver.getLastName());
        tvDriverEmail.setText(driver.getEmail());
        tvDateOfBirth.setText(driver.getDateOfBirth());
        tvLicenseNumber.setText(driver.getLicenseNumber());
        tvLicensePlate.setText(driver.getLicensePlate());

        ListView listView = findViewById(R.id.tvBordoreauHeader);

        // Initialize adapter with the list from the driver, ensuring it's not null
        List<Bordoreau> bordoreauList = driver.getBordoreauQRDTOS();
        if (bordoreauList == null) {
            bordoreauList = new ArrayList<>(); // ensure the list is not null
        }
        Log.d("HTTP", "Bordoreau List: " + bordoreauList);

        BordoreauAdapterForProfile adapter = new BordoreauAdapterForProfile(this, bordoreauList);
        listView.setAdapter(adapter);
    }
}
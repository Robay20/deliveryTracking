package com.example.packettracer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if the user is already logged in
        SharedPreferences sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE);
        if (sharedPreferences.getBoolean("logged_in", false)) {
            // If already logged in, go to Dashboard
            goToDashboard(sharedPreferences.getString("cinDriver", ""));
        }

        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.txtUsername);
        passwordEditText = findViewById(R.id.txtPassword);
    }

    public void attemptLogin(View view) {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(MainActivity.this, "Username or password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        String requestBody = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);
        RequestBody body = RequestBody.create(mediaType, requestBody);
        Request request = new Request.Builder()
                .url("http://192.168.1.109:8080/api/drivers/login")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, "Login failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("LoginStatus", "Login request failed: " + e.getMessage());
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String responseBody = response.body().string(); // Read the response body
                if (response.isSuccessful()) {
                    runOnUiThread(() -> {
                        try {
                            JSONObject jsonObject = new JSONObject(responseBody);
                            String message = jsonObject.getString("message");
                            String cinDriver = jsonObject.getString("cinDriver");
                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                            Log.d("LoginStatus", message);

                            // Save login state and credentials
                            SharedPreferences sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("logged_in", true);
                            editor.putString("cinDriver", cinDriver);
                            editor.apply();

                            goToDashboard(cinDriver);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Failed to parse response", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(MainActivity.this, "Login failed: " + responseBody, Toast.LENGTH_LONG).show();
                        Log.e("LoginStatus", "Login failed: " + responseBody);
                    });
                }
            }
        });
    }

    private void goToDashboard(String cinDriver) {
        Intent intent = new Intent(MainActivity.this, Dashboard.class);
        intent.putExtra("cinDriver", cinDriver);
        startActivity(intent);
        finish();
    }
}

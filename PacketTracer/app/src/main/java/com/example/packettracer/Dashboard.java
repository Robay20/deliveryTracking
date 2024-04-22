package com.example.packettracer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Dashboard extends AppCompatActivity {
    Button btn_scan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        listView = findViewById(R.id.list_packet);
        fetchJsonData();

        btn_scan =findViewById(R.id.btn_qr_code);
        btn_scan.setOnClickListener(v->
        {
            scanCode(null);
        });


    }

    public void scanCode(View view)
    {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLaucher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLaucher = registerForActivityResult(new ScanContract(), result->
    {
        if(result.getContents() !=null)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
            builder.setTitle("Result");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    dialogInterface.dismiss();
                }
            }).show();
        }
    });

    public class Packet {
        private int cinPacket;
        private User sender;
        private User client;
        private String sentTime;
        private String city;
        private String status;
        private String description;

        // Constructor, getters, and setters
        public Packet(int cinPacket, User sender, User client, String sentTime, String city, String status, String description) {
            this.cinPacket = cinPacket;
            this.sender = sender;
            this.client = client;
            this.sentTime = sentTime;
            this.city = city;
            this.status = status;
            this.description = description;
        }

        @Override
        public String toString() {
            return "Packet " + cinPacket + ": " + status;
        }
    }
    private ListView listView;

    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private List<Packet> personList = new ArrayList<>();

    public class User {
        private String username;
        private String firstName;
        private String lastName;
        private String email;
        private String role;
        private boolean active;

        // Constructor, getters, and setters
        public User(String username, String firstName, String lastName, String email, String role, boolean active) {
            this.username = username;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.role = role;
            this.active = active;
        }
    }

    private void fetchJsonData() {
        new Thread(() -> {
            OkHttpClient clienthttp = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://192.168.43.207:8080/api/packets")  // Change to your API URL
                    .build();
            try (Response response = clienthttp.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String jsonData = response.body().string();
                    JSONArray jsonArray = new JSONArray(jsonData);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        User sender = parseUser(jsonObject.getJSONObject("sender"));
                        User client = parseUser(jsonObject.getJSONObject("client"));
                        Packet packet = new Packet(
                                jsonObject.getInt("cinPacket"),
                                sender,
                                client,
                                jsonObject.getString("sentTime"),
                                jsonObject.getString("city"),
                                jsonObject.getString("status"),
                                jsonObject.getString("description")
                        );
                        personList.add(packet);
                    }
                    mainHandler.post(this::updateListView);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    private void updateListView() {
        ArrayAdapter<Packet> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, personList);
        listView.setAdapter(adapter);
    }


    private User parseUser(JSONObject jsonObject) throws JSONException {
        return new User(
                jsonObject.getString("username"),
                jsonObject.getString("firstName"),
                jsonObject.getString("lastName"),
                jsonObject.getString("email"),
                jsonObject.getString("role"),
                jsonObject.getBoolean("active")
        );
    }

}

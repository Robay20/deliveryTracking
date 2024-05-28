package com.example.packettracer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import retrofit2.Callback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.example.packettracer.model.BordoreauQRDTO;
import com.example.packettracer.model.PacketDetailDTO;
import com.example.packettracer.model.PacketStatus;
import com.example.packettracer.model.TransfertRequest;
import com.example.packettracer.utils.BordoreauApi;
import com.example.packettracer.utils.BordoreauCallback;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
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

import retrofit2.Call;
import retrofit2.Retrofit;

public class Dashboard extends AppCompatActivity {
    private BordoreauDao bordoreauDao;
    private String currentDriverId ;
    private ImageView btn_scan;
    private ImageView profileimage;
    private ListView listView;
    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private Set<BordoreauQRDTO> bordoreauSet;
    private ArrayAdapter<BordoreauQRDTO> adapter;

    private Button btnDeleteAll; // Add this line


    private ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            processScannedData(result.getContents());
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        bordoreauSet = new HashSet<>();

        AppDatabase db = AppDatabase.getDatabase(this);
        bordoreauDao = db.bordoreauDao();

        currentDriverId=getIntent().getStringExtra("cinDriver");


        profileimage = findViewById(R.id.profileimage);
        listView = findViewById(R.id.list_packet);
        btn_scan = findViewById(R.id.btn_qr_code);
        btn_scan.setOnClickListener(this::scanCode);
        adapter = new BordoreauAdapterForDash(this, R.layout.list_item_view, new ArrayList<>(bordoreauSet));
        listView.setAdapter(adapter);

        profileimage.setOnClickListener(view -> {
            Intent  intent = new Intent(Dashboard.this, InfoProfileActivity.class);
            intent.putExtra("idProfile",currentDriverId);
            startActivity(intent);
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            BordoreauQRDTO selectedBordoreau = adapter.getItem(position);
            if (selectedBordoreau != null) {
                Intent intent = new Intent(Dashboard.this, InfoBordoreauActivity.class);
                intent.putExtra("BordoreauData", selectedBordoreau.toJson());  // Serialize the object
                intent.putExtra("cinDriver", currentDriverId);
                startActivity(intent);
            }
        });

        // Initialize and set the delete all button
        btnDeleteAll = findViewById(R.id.btn_delete_all);
        btnDeleteAll.setOnClickListener(view -> deleteAllBordoreaux());

        loadBordoreauData();
    }

    private void deleteAllBordoreaux() {
        executorService.execute(() -> {
            bordoreauDao.deleteAllBordoreaux();
            mainHandler.post(() -> {
                bordoreauSet.clear();
                updateListView();
                Toast.makeText(Dashboard.this, "All bordereaux deleted", Toast.LENGTH_SHORT).show();
            });
        });
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
            bordoreauDao.insert(bordoreau);
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

    BordoreauCallback bordoreauCallback = new BordoreauCallback() {
        @Override
        public void onResponse(BordoreauQRDTO bordoreau, String oldoo) {
            // Handle the responseBordoreau here
            // For example, you can update UI with the fetched Bordoreau
            bordoreau.setStringLivreur(currentDriverId);
            executorService.execute(() -> {
                bordoreauDao.insert(bordoreau);
                mainHandler.post(() -> {
                    bordoreauSet.add(bordoreau);
                    updateListView();
                });
            });

            List<PacketDetailDTO> packetIds = bordoreau.getPackets();
            Set<Long> ids = new HashSet<>();

            for (PacketDetailDTO packetDetail : packetIds) {
                ids.add(packetDetail.getNumeroBL());
            }

            createTransfert(currentDriverId, oldoo, ids);

        }

        @Override
        public void onError(String errorMessage) {
            // Handle the error here
            // For example, you can show a toast message with the error
            Toast.makeText(Dashboard.this, errorMessage, Toast.LENGTH_SHORT).show();
        }
    };

    private void processScannedData(String data) {
        try {
            Log.d("TAG", "processScannedData: " + data);
            if (!data.contains("{")) {
                String[] parts = data.split(",");
                Log.d("TAG", "processScannedData: length " + parts.length);
                if (parts.length != 2) {
                    Toast.makeText(Dashboard.this, "A Invalid QR code format.", Toast.LENGTH_LONG).show();
                    return;
                }
                String olddriver = parts[0].trim();
                Long idbordo = Long.parseLong(parts[1].trim());
                fetchBordoreau(idbordo,bordoreauCallback,olddriver);
            } else {
                BordoreauQRDTO bordoreau = parseBordoreauData(data);
                if (bordoreau == null) {
                    Toast.makeText(Dashboard.this, "Invalid QR code format.", Toast.LENGTH_LONG).show();
                } else if (!verifyDriver(bordoreau.getStringLivreur())) {
                    Toast.makeText(Dashboard.this, "You are not the driver", Toast.LENGTH_LONG).show();
                } else if (bordoreau.getStatus() == PacketStatus.INITIALIZED) {
                    fetchBordoreauData(bordoreau.getNumeroBordoreau());

                }  else {
                    Toast.makeText(Dashboard.this, "Bordoreau status not handled.", Toast.LENGTH_LONG).show();
                }
            }
        } catch (NumberFormatException e) {
            Log.e("TAG", "Invalid number format in QR code: " + data, e);
            Toast.makeText(Dashboard.this, "Invalid number format in QR code.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e("TAG", "Exception occurred while processing QR code: " + data, e);
            Toast.makeText(Dashboard.this, "An error occurred while processing the QR code.", Toast.LENGTH_LONG).show();
        }
    }




/*// Handle Transfert button click
        transfertButton.setOnClickListener(v -> {
            PacketDetailDTO packetDetail = bordoreau.getPackets().get(position);
            Long packetId = packetDetail.getNumeroBL();
            // Replace 'currentDriverId' and 'codeSecteur' with appropriate values
            createTransfert(currentDriverId, bordoreau.getCodeSecteur(), Collections.singleton(packetId));
            dialog.dismiss();
        });
*/


    private void handleInTransitBordoreau(String oldDriverId) {
        // Find the bordereau associated with the old driver ID
        BordoreauQRDTO bordoreauToUpdate = null;
        for (BordoreauQRDTO bordoreau : bordoreauSet) {
            if (bordoreau.getStringLivreur().equals(oldDriverId)) {
                bordoreauToUpdate = bordoreau;
                break;
            }
        }

        if (bordoreauToUpdate == null) {
            Toast.makeText(Dashboard.this, "Bordereau not found for the old driver ID: " + oldDriverId, Toast.LENGTH_LONG).show();
            return;
        }

        // Update stringLivreur value to currentDriverId
        String newStringLivreur = currentDriverId;

        // Base URL of your backend server
        String baseUrl = "http://192.168.43.207:8080/";

        // Create Retrofit instance
        Retrofit retrofit = RetrofitClient.getClient(baseUrl);

        // Create API service
        BordoreauApi service = retrofit.create(BordoreauApi.class);

        // Make the network call to update stringLivreur
        Call<Void> call = service.updateBordoreauStringLivreur(bordoreauToUpdate.getNumeroBordoreau(), newStringLivreur);
        BordoreauQRDTO finalBordoreauToUpdate = bordoreauToUpdate;
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.isSuccessful()) {
                    // Handle successful response
                    Log.d("TAG", "Bordoreau stringLivreur updated successfully");
                    // Fetch updated Bordoreau data
                    // Introduce a delay before fetching the updated data
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        fetchBordoreauData(finalBordoreauToUpdate.getNumeroBordoreau());
                    }, 2000); // 2000 milliseconds delay (2 seconds)                } else {
                    // Handle unsuccessful response
                    Log.e("TAG", "Error updating Bordoreau stringLivreur: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle network failures
                Log.e("TAG", "Failed to update Bordoreau stringLivreur: " + t.getMessage());
            }
        });
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
                        DateTimeFormatter oldFormatter = DateTimeFormatter.ofPattern("yyMMdd");
                        LocalDate date = LocalDate.parse(responseBordoreau.getDate(), oldFormatter);
                        responseBordoreau.setDate(date.toString());
                        //bordoreauDao.delete(bordoreauDao.getAll().get(0));
                        bordoreauDao.insert(responseBordoreau);
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

    private void fetchBordoreau(Long bordoreauId, BordoreauCallback callback,String oldoo) {
        String url = "http://192.168.43.207:8080/api/bordoreaux/" + bordoreauId + "/qr";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        executorService.execute(() -> {
            try (Response response = client.newCall(request).execute()) {
                Log.d("response", response.toString());
                if (response.isSuccessful() && response.body() != null) {
                    String jsonData = response.body().string();
                    JSONObject jsonObject = new JSONObject(jsonData);
                    BordoreauQRDTO responseBordoreau = parseJSONToBordoreau(jsonObject);
                    if (responseBordoreau != null) {
                        DateTimeFormatter oldFormatter = DateTimeFormatter.ofPattern("yyMMdd");
                        LocalDate date = LocalDate.parse(responseBordoreau.getDate(), oldFormatter);
                        responseBordoreau.setDate(date.toString());

                        Log.d("fetchBordoreau", "Bordoreau successfully fetched and processed: " + responseBordoreau);
                        // Invoke the callback with the responseBordoreau
                        callback.onResponse(responseBordoreau,oldoo);
                    } else {
                        Log.e("fetchBordoreau", "Failed to parse Bordoreau JSON");
                        mainHandler.post(() -> Toast.makeText(Dashboard.this, "Bordoreau not found or error fetching data", Toast.LENGTH_SHORT).show());
                        // Invoke the callback with null indicating an error
                        callback.onError("Failed to parse Bordoreau JSON");
                    }
                } else {
                    Log.e("fetchBordoreau", "Response unsuccessful or body is null");
                    mainHandler.post(() -> Toast.makeText(Dashboard.this, "Bordoreau not found or error fetching data", Toast.LENGTH_SHORT).show());
                    // Invoke the callback with null indicating an error
                    callback.onError("Response unsuccessful or body is null");
                }
            } catch (Exception e) {
                Log.e("fetchBordoreau", "Error fetching Bordoreau: " + e.getMessage(), e);
                mainHandler.post(() -> Toast.makeText(Dashboard.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show());
                // Invoke the callback with the error message
                callback.onError("Error fetching Bordoreau: " + e.getMessage());
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


            packets.add(new PacketDetailDTO(numeroBL, codeClient, nbrColis, nbrSachets,PacketStatus.INITIALIZED));
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


                packets.add(new PacketDetailDTO(numeroBL, codeClient, nbrColis, nbrSachets,PacketStatus.INITIALIZED));
            }

            return new BordoreauQRDTO(numeroBordoreau, PacketStatus.INITIALIZED, date, stringLivreur, codeSecteur, packets);
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

    private void synchronizeWithServer() {
        String url = "http://192.168.43.207:8080/api/bordoreaux/Dashboard/"+currentDriverId;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        executorService.execute(() -> {
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String jsonData = response.body().string();
                    JSONArray jsonArray = new JSONArray(jsonData);

                    List<BordoreauQRDTO> bordereaux = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        BordoreauQRDTO bordoreau = parseJSONToBordoreau(jsonObject);
                        if (bordoreau != null) {
                            bordereaux.add(bordoreau);
                            logAllBordoreaux();

                        }
                    }

                    // Update the local database and UI
                    bordoreauDao.insertAll(bordereaux);
                    mainHandler.post(() -> {
                        bordoreauSet.clear();
                        bordoreauSet.addAll(bordereaux);
                        updateListView();
                    });
                } else {
                    mainHandler.post(() -> Toast.makeText(Dashboard.this, "Error fetching data from server", Toast.LENGTH_SHORT).show());
                }
            } catch (Exception e) {
                e.printStackTrace();
                mainHandler.post(() -> Toast.makeText(Dashboard.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show());
                Log.e("ERROR", "synchronizeWithServer: "+e.getMessage() );
            }
        });
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
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
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

    private void logAllBordoreaux() {
        executorService.execute(() -> {
            List<BordoreauQRDTO> allBordoreaux = bordoreauDao.getAll();
            for (BordoreauQRDTO bordoreau : allBordoreaux) {
                Log.d("Bordoreau", bordoreau.toString());
                for (PacketDetailDTO packet : bordoreau.getPackets()) {
                    Log.d("PacketDetail", packet.toString());
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        synchronizeWithServer();
    }

    private void logout() {
        SharedPreferences sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Clear all stored data
        editor.apply();

        Intent intent = new Intent(Dashboard.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void logout(View view) {
        logout();
    }

}

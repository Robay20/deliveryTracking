package com.example.packet_tracer;

import com.example.packet_tracer.admin.AccountController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class LoginController {
    @FXML
    private Button loginButton;
    @FXML
    private TextField usernameTxt;
    @FXML
    private PasswordField passwordTxt;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void switchToFXML2(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/account.fxml"));
        Parent root = loader.load();
        AccountController accountController = loader.getController();
        accountController.setStage(stage);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
    @FXML
    private void login() {
        try {
            String username = usernameTxt.getText();
            String password = passwordTxt.getText();

            // Create JSON payload
            String jsonPayload = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";

            // API endpoint URL
            String endpointUrl = "http://localhost:8080/api/admins/login"; // Change to appropriate endpoint URL

            // Send POST request
            URL url = new URL(endpointUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);

            // Write JSON payload to request body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Get response code
            int responseCode = connection.getResponseCode();

            // Read response
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Login successful
                // Do something here, like navigating to another scene or displaying a success message
                FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/account.fxml"));
                Parent root = loader.load();
                AccountController accountController = loader.getController();
                accountController.setStage(stage);
                Scene scene = new Scene(root);
                stage.setScene(scene);
                System.out.println("Login successful");
            } else {
                // Login failed
                // Display error message
                System.out.println("Login failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
package com.example.packet_tracer;

import com.example.packet_tracer.admin.AccountController;
import com.example.packet_tracer.expediteur.ExpediterController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
    private Scene scene;
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void switchToScene(ActionEvent event) throws IOException {
        seconnecter(event);
    }
    private boolean colorToggled = true;

    @FXML
    private Button toggleButton;
    @FXML
    private Button toggleButton1;
    @FXML
    private void handleToggle(ActionEvent event) {
        if (colorToggled) {
            toggleButton.setStyle("-fx-background-color: #2bc1cf;");
            toggleButton1.setStyle("-fx-background-color: #008897;");// Clear the custom style to revert to default
        } else {
            toggleButton.setStyle("-fx-background-color: #008897;");
            toggleButton1.setStyle("-fx-background-color: #2bc1cf;");// Set custom background color
        }
        userType = "admin";
         // Toggle the boolean flag
    }
    @FXML
    private void handleToggle1(ActionEvent event) {
        if (colorToggled) {
            toggleButton.setStyle("-fx-background-color: #008897;");
            toggleButton1.setStyle("-fx-background-color: #2bc1cf;");// Clear the custom style to revert to default
        } else {
            toggleButton.setStyle("-fx-background-color: #2bc1cf;");
            toggleButton1.setStyle("-fx-background-color: #008897;");// Set custom background color
        }
        userType = "sender";
         // Toggle the boolean flag
    }


    @FXML
    private Label loginErreur;
    private  static String userType="admin";



    @FXML
    void seconnecter(ActionEvent event) throws IOException {
        String username = usernameTxt.getText();
        String password = passwordTxt.getText();

        String endpointUrl;

        switch (userType) {
            case "admin":
                endpointUrl = "http://localhost:8080/api/admins/login";
                break;
            case "sender":
                endpointUrl = "http://localhost:8080/api/senders/login";
                break;
            default:
                System.out.println("Invalid user type: " + userType);
                return;
        }

        boolean loginSuccessful = login(endpointUrl, username, password, userType);

        if (loginSuccessful) {
            // Navigate to corresponding stage
            switch (userType) {
                case "admin":
                    navigateToAdminDashboard(event);
                    break;
                case "sender":
                    navigateToSenderDashboard(event);
                    break;
                default:
                    System.out.println("Invalid user type: " + userType);
            }
        } else {
            // Display an error message if login fails
            loginErreur.setText("Invalid username or password");
            loginErreur.setStyle("-fx-text-fill: #ad3a3a;");
        }
    }

    private boolean login(String endpointUrl, String username, String password, String userType) {
        try {
            // Create JSON payload
            String jsonPayload = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";

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
                System.out.println(userType + " login successful");
                // You can add additional actions here if needed
                return true;
            } else {
                // Login failed
                System.out.println(userType + " login failed");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Navigation methods
    private void navigateToAdminDashboard(ActionEvent event) throws IOException {
        // Code to navigate to admin dashboard stage
        Parent root = FXMLLoader.load(getClass().getResource("admin/account.fxml"));
        stage =  (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    private void navigateToSenderDashboard(ActionEvent event) throws IOException {
        // Code to navigate to sender dashboard stage
        Parent root1 = FXMLLoader.load(getClass().getResource("expediteur/expediter.fxml"));
        Stage stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene1 = new Scene(root1);
        stage1.setScene(scene1);
        stage1.show();
    }

}



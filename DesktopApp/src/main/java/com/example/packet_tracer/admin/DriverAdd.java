package com.example.packet_tracer.admin;

import com.example.packet_tracer.models.DriverF;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DriverAdd {
    @FXML
    private TextField cinDriverField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField dateOfBirthField;
    @FXML
    private TextField licenseNumberField;
    @FXML
    private TextField licensePlateField;
    @FXML
    private TextField brandField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    @FXML
    private void addDriver(ActionEvent event) {
        if (!validateFields()) {
            showAlert("Error! All fields are required.");
            return;
        }

        // Collect data from TextFields
        String cinDriver = cinDriverField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String dateOfBirth = dateOfBirthField.getText(); // Assuming this is in a valid format
        String licenseNumber = licenseNumberField.getText();
        String licensePlate = licensePlateField.getText();
        String brand = brandField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        LocalDate date = null;

        try {
            // Assuming the date format is "yyyy-MM-dd"
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date = LocalDate.parse(dateOfBirth, formatter);
        } catch (Exception e) {
            showAlert("Error! Invalid date format. Please use yyyy-MM-dd.");
            return;
        }

        DriverF newDriver = new DriverF();
        newDriver.setCinDriver(cinDriver);
        newDriver.setFirstName(firstName);
        newDriver.setLastName(lastName);
        newDriver.setEmail(email);
        newDriver.setDateOfBirth(date); // Should be a valid date format
        newDriver.setLicenseNumber(licenseNumber);
        newDriver.setLicensePlate(licensePlate);
        newDriver.setBrand(brand);
        newDriver.setUsername(username);
        newDriver.setPassword(password);
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            String json = mapper.writeValueAsString(newDriver);

            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_2)
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .connectTimeout(Duration.ofSeconds(20))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/drivers/json"))
                    .timeout(Duration.ofMinutes(1))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(response -> {
                        System.out.println("Driver added successfully: " + response);
                        showAlert("Driver added successfully !");
                        Stage stage = (Stage) cinDriverField.getScene().getWindow();
                        stage.close();
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void showAlert(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
    private boolean validateFields() {
        return !cinDriverField.getText().isEmpty()
                && !firstNameField.getText().isEmpty()
                && !lastNameField.getText().isEmpty()
                && !emailField.getText().isEmpty()
                && !dateOfBirthField.getText().isEmpty()
                && !licenseNumberField.getText().isEmpty()
                && !licensePlateField.getText().isEmpty()
                && !brandField.getText().isEmpty()
                && !usernameField.getText().isEmpty()
                && !passwordField.getText().isEmpty();
    }

}

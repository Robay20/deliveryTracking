package com.example.packet_tracer.admin;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

public class LivreurController {
    @FXML
    private Pane LabelExpediteur;
    @FXML
    private Pane LabelAccount;
    @FXML
    private Pane LabelPackets;
    @FXML
    private Pane LabelDeconnection;

    private Stage stage;
    private Parent root;
    private Scene scene;


    @FXML
    private AutocompleteTextField autocompleteTextField;

    private final ObservableList<String> suggestions = FXCollections.observableArrayList("all");
    private final Popup popup = new Popup();
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void switchToLogin(MouseEvent event) throws IOException {
        try {
            // Load login.fxml from the resources directory
            URL url = getClass().getResource("/com/example/packet_tracer/login.fxml");
            if (url == null) {
                throw new IOException("FXML file not found");
            }

            root = FXMLLoader.load(url);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the IOException here (e.g., show an error message to the user)
        }
    }
    @FXML
    private void switchToExpediteur(MouseEvent event) throws IOException {
        try {
            // Load login.fxml from the resources directory
            URL url = getClass().getResource("/com/example/packet_tracer/admin/expediteur.fxml");
            if (url == null) {
                throw new IOException("FXML file not found");
            }

            root = FXMLLoader.load(url);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the IOException here (e.g., show an error message to the user)
        }
    }
    @FXML
    private void switchToAccount(MouseEvent event) throws IOException {
        try {
            // Load login.fxml from the resources directory
            URL url = getClass().getResource("/com/example/packet_tracer/admin/account.fxml");
            if (url == null) {
                throw new IOException("FXML file not found");
            }

            root = FXMLLoader.load(url);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the IOException here (e.g., show an error message to the user)
        }
    }
    @FXML
    private void switchToPackets(MouseEvent event) throws IOException {
        try {
            // Load login.fxml from the resources directory
            URL url = getClass().getResource("/com/example/packet_tracer/admin/packets.fxml");
            if (url == null) {
                throw new IOException("FXML file not found");
            }

            root = FXMLLoader.load(url);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the IOException here (e.g., show an error message to the user)
        }
    }

    //-------------------------------------------------------just a test of an idea that might works -----------------------------------------------------------
    //--------------------------------------------------                                                 -------------------------------------------------------
    //-----------------------------------------                                                                    ---------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------------------------------------

    @FXML
    public void initialize() {
        autocompleteTextField.setSuggestions(suggestions);
        autocompleteTextField.setOnKeyPressed(this::handleKeyPressed);
        autocompleteTextField.textProperty().addListener((observable, oldValue, newValue) -> showSuggestions());
        colCin.setCellValueFactory(new PropertyValueFactory<>("cinDriver"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colDateOfBirth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));

        tableView.setItems(driverList);
        getalldriver(null); // Trigger data loading automatically on initialize
    }

    private void showSuggestions() {
        String input = autocompleteTextField.getText().toLowerCase();
        ObservableList<String> filteredSuggestions = suggestions.filtered(s -> s.toLowerCase().contains(input));

        if (input.isEmpty() || filteredSuggestions.isEmpty()) {
            popup.hide();
            return;
        }

        ListView<String> listView = new ListView<>(filteredSuggestions);
        listView.setOnMouseClicked(event -> {
            String selectedItem = listView.getSelectionModel().getSelectedItem();
            autocompleteTextField.setText(selectedItem);
            popup.hide();
            updateTableView(selectedItem);
        });

        popup.getContent().clear();
        popup.getContent().add(listView);
        popup.show(autocompleteTextField, autocompleteTextField.localToScreen(0, autocompleteTextField.getHeight()).getX(), autocompleteTextField.localToScreen(0, autocompleteTextField.getHeight()).getY());
    }
    private void updateTableView(String selectedItem) {
        if ("all".equalsIgnoreCase(selectedItem)) {
            tableView.setItems(driverList);
            getalldriver(null);
        } else {
            // Filter the driverList based on the selected item
            ObservableList<Driver> filteredDrivers = driverList.filtered(driver ->
                    driver.getCinDriver().equalsIgnoreCase(selectedItem));

            // Set the updated filteredDrivers to the TableView
            tableView.setItems(filteredDrivers);
        }
    }



    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.DOWN && !popup.isShowing()) {
            showSuggestions();
        }
    }

    //--------------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------data base fetch part -------------------------------------------------------------
    //----------------------------------------                          ------------------------------------------
    //---------------------------------                                      --------------------------------------
    @FXML
    private TableView<LivreurController.Driver> tableView;
    @FXML
    private TableColumn<LivreurController.Driver, String> colCin;
    @FXML
    private TableColumn<LivreurController.Driver, String> colFirstName;
    @FXML
    private TableColumn<LivreurController.Driver, String> colLastName;
    @FXML
    private TableColumn<LivreurController.Driver, String> colDateOfBirth;

    private ObservableList<LivreurController.Driver> driverList = FXCollections.observableArrayList();

    @FXML
    void getalldriver(ActionEvent event) {
        String endpointUrl = "http://localhost:8080/api/drivers";
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(20))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpointUrl))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(jsonBody -> {
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        List<LivreurController.Driver> drivers = mapper.readValue(jsonBody, new TypeReference<List<LivreurController.Driver>>(){});
                        //------------filling the suggestions----------------------
                        for (Driver driver: drivers) {
                            suggestions.add(driver.cinDriver);

                        }


                        driverList.clear();
                        driverList.addAll(drivers);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }


    public static class Driver {
        private String username;
        private String password;
        private String firstName;
        private String lastName;
        private String email;
        private String role;
        private String dateOfBirth;
        private String cinDriver;
        private String licenseNumber;
        private String licensePlate;
        private String brand;
        private List<Object> packets;  // Change Object to your specific packet class if you have one
        private boolean active;

        // Constructor
        public Driver() {}

        // Getters and Setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public String getCinDriver() {
            return cinDriver;
        }

        public void setCinDriver(String cinDriver) {
            this.cinDriver = cinDriver;
        }

        public String getLicenseNumber() {
            return licenseNumber;
        }

        public void setLicenseNumber(String licenseNumber) {
            this.licenseNumber = licenseNumber;
        }

        public String getLicensePlate() {
            return licensePlate;
        }

        public void setLicensePlate(String licensePlate) {
            this.licensePlate = licensePlate;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public List<Object> getPackets() {
            return packets;
        }

        public void setPackets(List<Object> packets) {
            this.packets = packets;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }
    }
}

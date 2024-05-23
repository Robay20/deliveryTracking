package com.example.packet_tracer.expediteur;

import com.example.packet_tracer.admin.AutocompleteTextField;
import com.example.packet_tracer.models.Packet;
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
import javafx.scene.control.Button;
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

public class HistoriqueController {
    @FXML
    private Button ButtonConfirmer;
    @FXML
    private Button ButtonAnnuler;
    @FXML
    private Pane LabelPackets;
    @FXML
    private Pane LabelDeconnection;
    @FXML
    private Pane LabelAccount;
    @FXML
    private Pane LabelNotification;

    private Stage stage;
    private Parent root;
    private Scene scene;
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
    private void switchToNotification(MouseEvent event) throws IOException {
        try {
            // Load login.fxml from the resources directory
            URL url = getClass().getResource("/com/example/packet_tracer/expediteur/notification.fxml");
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
    private void switchToSuiver(MouseEvent event) throws IOException {
        try {
            // Load login.fxml from the resources directory
            URL url = getClass().getResource("/com/example/packet_tracer/expediteur/suiver.fxml");
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
            URL url = getClass().getResource("/com/example/packet_tracer/expediteur/account.fxml");
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
    private void switchToLivreur(MouseEvent event) throws IOException {
        try {
            // Load login.fxml from the resources directory
            URL url = getClass().getResource("/com/example/packet_tracer/expediteur/livreur.fxml");
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
    private TableView<Packet> tableView;
    @FXML
    private TableColumn<Packet, Long> colIdPacket;
    @FXML
    private TableColumn<Packet, String> colClientCin;
    @FXML
    private TableColumn<Packet, Integer> colColis;
    @FXML
    private TableColumn<Packet, Integer> colSachets;
    @FXML
    private TableColumn<Packet, String> colStatus;

    private ObservableList<Packet> packetList = FXCollections.observableArrayList();
    private ObservableList<String> suggestions = FXCollections.observableArrayList("all");

    @FXML
    private AutocompleteTextField autocompleteTextField;
    private Popup popup = new Popup();

    @FXML
    public void initialize() {
        autocompleteTextField.setSuggestions(suggestions);
        autocompleteTextField.setOnKeyPressed(this::handleKeyPressed);
        autocompleteTextField.textProperty().addListener((observable, oldValue, newValue) -> showSuggestions());

        colIdPacket.setCellValueFactory(new PropertyValueFactory<>("idPacket"));
        colClientCin.setCellValueFactory(new PropertyValueFactory<>("clientCin"));
        colColis.setCellValueFactory(new PropertyValueFactory<>("colis"));
        colSachets.setCellValueFactory(new PropertyValueFactory<>("sachets"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableView.setItems(packetList);
        fetchPacketsAndPopulateTable(null); // Trigger data loading automatically on initialize
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
            tableView.setItems(packetList);
            fetchPacketsAndPopulateTable(null);
        } else {
            // Filter the packetList based on the selected item
            ObservableList<Packet> filteredPackets = packetList.filtered(packet ->
                    packet.getBL().toString().equalsIgnoreCase(selectedItem));

            // Set the updated filteredPackets to the TableView
            tableView.setItems(filteredPackets);
        }
    }

    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.DOWN && !popup.isShowing()) {
            showSuggestions();
        }
    }
    @FXML
    private void handleRefreshButton(ActionEvent event) {
        fetchPacketsAndPopulateTable(null);
    }

    @FXML
    private void fetchPacketsAndPopulateTable(Object o) {
        String endpointUrl = "http://localhost:8080/api/packets/json";
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
                        List<Packet> packets = mapper.readValue(jsonBody, new TypeReference<List<Packet>>(){});

                        // Clear the suggestions list before adding new suggestions
                        suggestions.clear();
                        suggestions.add("all");

                        for (Packet packet: packets) {
                            suggestions.add(packet.getBL().toString());
                        }

                        packetList.clear();
                        packetList.addAll(packets);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}

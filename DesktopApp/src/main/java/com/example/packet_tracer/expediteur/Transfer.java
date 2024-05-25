package com.example.packet_tracer.expediteur;

import com.example.packet_tracer.models.Transfert;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

public class Transfer {
    @FXML
    private TableView<Transfert> transferTableView;
    @FXML
    private TableColumn<Transfert, Long> colIdTransfert;
    @FXML
    private TableColumn<Transfert, String> colOldPerson;
    @FXML
    private TableColumn<Transfert, String> colNewPerson;
    @FXML
    private TableColumn<Transfert, String> colTime;

    private final ObservableList<Transfert> transferList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colIdTransfert.setCellValueFactory(new PropertyValueFactory<>("idTransfert"));
        colOldPerson.setCellValueFactory(new PropertyValueFactory<>("oldPerson"));
        colNewPerson.setCellValueFactory(new PropertyValueFactory<>("newPerson"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        transferTableView.setItems(transferList);
    }

    public void setPacketId(Long packetId) {
        fetchTransfersByPacketId(packetId);
    }

    private void fetchTransfersByPacketId(Long packetId) {
        String endpointUrl = "http://localhost:8080/api/transferts/packet/" + packetId;
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
                    System.out.println("Received JSON: " + jsonBody); // Debug logging
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        mapper.registerModule(new JavaTimeModule());
                        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        List<Transfert> transferts = mapper.readValue(jsonBody, new TypeReference<List<Transfert>>() {});
                        transferList.clear();
                        transferList.addAll(transferts);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}

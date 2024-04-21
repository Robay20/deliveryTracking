package com.example.packet_tracer.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

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
    private AutocompleteTextField autocompleteTextField;

    private final ObservableList<String> suggestions = FXCollections.observableArrayList("Apple", "Banana", "Cherry", "Date");
    private final Popup popup = new Popup();

    @FXML
    public void initialize() {
        autocompleteTextField.setSuggestions(suggestions);
        autocompleteTextField.setOnKeyPressed(this::handleKeyPressed);
        autocompleteTextField.textProperty().addListener((observable, oldValue, newValue) -> showSuggestions());
    }

    private void showSuggestions() {
        ListView<String> listView = new ListView<>(suggestions);
        listView.setOnMouseClicked(event -> {
            autocompleteTextField.setText(listView.getSelectionModel().getSelectedItem());
            popup.hide();
        });

        popup.getContent().clear();
        popup.getContent().add(listView);
        popup.show(autocompleteTextField, autocompleteTextField.getScene().getWindow().getX() + autocompleteTextField.getLayoutX(), autocompleteTextField.getScene().getWindow().getY() + autocompleteTextField.getLayoutY() + autocompleteTextField.getHeight());
    }

    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.DOWN && !popup.isShowing()) {
            showSuggestions();
        }
    }
}

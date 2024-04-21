package com.example.packet_tracer.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ExpediteurController {

    @FXML
    private TextField txtCin;

    @FXML
    private TextField txtFirstname;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtNaissance;

    @FXML
    private TextField txtPassword;
    @FXML
    private Pane LabelAccount;
    @FXML
    private Pane LabelLivreur;
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
    private void switchToLivreur(MouseEvent event) throws IOException {
        try {
            // Load login.fxml from the resources directory
            URL url = getClass().getResource("/com/example/packet_tracer/admin/livreur.fxml");
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

    @FXML
    void Onajoute(ActionEvent event) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Onmodifier(ActionEvent event) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Onsupprimer(ActionEvent event) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

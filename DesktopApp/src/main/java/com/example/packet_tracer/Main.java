package com.example.packet_tracer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Image icon = new Image("file:///../../paktrace.png");
        stage.getIcons().add(icon);
        Scene scene = new Scene(fxmlLoader.load(), 1080, 620);
        stage.setTitle("Packet Tracer");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
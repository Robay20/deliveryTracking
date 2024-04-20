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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private Button loginButton;
    @FXML
    private TextField usernameTxt;
    @FXML
    private PasswordField passwordTxt;
    static private int sceneIndex = 0; // Use sceneIndex to track which scene is currently displayed

    private Stage stage;
    private Parent root;
    private Stage stage1;
    private Parent root1;
    private Scene scene;
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void switchToScene(ActionEvent event) throws IOException {
        if(sceneIndex==0){
            sceneIndex++;
            root = FXMLLoader.load(getClass().getResource("admin/account.fxml"));
            stage =  (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else if (sceneIndex==1) {
            sceneIndex--;
            root1 = FXMLLoader.load(getClass().getResource("expediteur/expediter.fxml"));
            stage1 =  (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene1 = new Scene(root1);
            stage1.setScene(scene1);
            stage1.show();
        }
    }
}



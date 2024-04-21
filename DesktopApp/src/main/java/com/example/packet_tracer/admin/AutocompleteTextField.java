package com.example.packet_tracer.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Popup;

public class AutocompleteTextField extends TextField {
    private final ObservableList<String> suggestions;
    private final Popup popup;

    public AutocompleteTextField() {
        suggestions = FXCollections.observableArrayList();
        popup = new Popup();

        setOnKeyPressed(this::handleKeyPressed);
        textProperty().addListener((observable, oldValue, newValue) -> showSuggestions());
    }

    public void setSuggestions(ObservableList<String> suggestions) {
        this.suggestions.setAll(suggestions);
    }

    private void showSuggestions() {
        ListView<String> listView = new ListView<>(suggestions);
        listView.setOnMouseClicked(event -> {
            setText(listView.getSelectionModel().getSelectedItem());
            popup.hide();
        });

        popup.getContent().clear();
        popup.getContent().add(listView);
        popup.show(this, getScene().getWindow().getX() + getLayoutX(), getScene().getWindow().getY() + getLayoutY() + getHeight());
    }

    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.DOWN && !popup.isShowing()) {
            showSuggestions();
        }
    }
}
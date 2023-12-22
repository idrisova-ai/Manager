package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Manager extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Manager.class.getResource("MainDocument.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Recipe Manager");
        stage.setMinHeight(700);
        stage.setMinWidth(1300);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
package Defaults;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.swing.*;

public class BarManagementSystem extends Application {
    Parent root;
    Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = FXMLLoader.load(getClass().getResource("MainProgram.fxml"));
        stage = primaryStage;
        stage.setTitle("Hotel Management System");
        stage.getIcons().add(new Image("/Icons/hotel.png"));
        stage.setMinHeight(740);
        stage.setMinWidth(1000);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

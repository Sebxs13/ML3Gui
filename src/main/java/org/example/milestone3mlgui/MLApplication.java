package org.example.milestone3mlgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class MLApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MLApplication.class.getResource("MLPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        stage.setTitle("MLApp");
        stage.setScene(scene);
        stage.show();

        //g = (GridPane)scene.lookup("#gameGrid");
        VBox v = (VBox)scene.lookup("#MemContainer");

        ArrayList<WordGui> a = new ArrayList<WordGui>();
        for(int i = 0; i < 100; i++){
            a.add(new WordGui(i));
        }

        for(int i = 0; i < a.size(); i++){
            v.getChildren().addAll(a.get(i).gethbox());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
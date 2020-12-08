package com.mealwith;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primarystage) {
        primarystage.setTitle("MealwithApp");
        primarystage.centerOnScreen();
        primarystage.setResizable(false);

        // Premier parent
        Parent home = null;
        try {
            home = FXMLLoader.load(getClass().getResource("/com/mealwith/gui/Login/Login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scenehome = new Scene(home);
        primarystage.setScene(scenehome);
        primarystage.show();
    }
}
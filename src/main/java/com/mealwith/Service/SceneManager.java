package com.mealwith.Service;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.MalformedURLException;

public class SceneManager {
    public void ChangeScene(Stage stage, String fileName)
    {
        Parent root = null;
        try {
            root = FXMLLoader.load(this.getClass().getResource("../GUI/Comments/" + fileName + ".fxml"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}

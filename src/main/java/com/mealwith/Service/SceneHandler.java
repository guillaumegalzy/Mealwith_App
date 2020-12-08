package com.mealwith.Service;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneHandler {

    /**
     * Gère le changement de scene via passage d'un actionEvent et de l'URL du FXML adéquat
     * @param event
     * @param urlFXML Définit en partant du package comprenant l'App principal
     */
    public void setScene(Event event, String urlFXML){
        // Récupération de la stage via le node ayant déclenché l'actionEvent
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();

        // Ferme la stage avec l'ancienne Scene
            stage.close();

        try {

        // Chargement du FXML demandé en argument et création de la scene associée
            Parent homeRoot = FXMLLoader.load(getClass().getResource("/com/mealwith/gui/"+ urlFXML + "/" + urlFXML + ".fxml"));
            Scene scene = new Scene(homeRoot);
            stage.setScene(scene);

        // Ouvre la stage avec la nouvelle Scene
            stage.show();

        } catch (IOException e) {
            System.err.printf("Error: %s%n", e.getMessage());
        }
    }
}

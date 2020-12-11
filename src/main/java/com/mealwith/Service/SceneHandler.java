package com.mealwith.Service;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneHandler {

    /**
     * Gère le changement de scene via passage d'un actionEvent et de l'URL du FXML adéquat
     * @param event evènement appelant la fonction
     * @param urlFXML nom du FXML à charger
     * @param directory nom du directory, en partant du package 'gui'
     */
    public void setScene(Event event, String directory,String urlFXML){
        // Récupération de la stage via le node ayant déclenché l'actionEvent
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();

        // Ferme la stage avec l'ancienne Scene
            stage.close();

        try {
            // Chargement du FXML demandé en argument et création de la scene associée
                Parent homeRoot = FXMLLoader.load(getClass().getResource("/com/mealwith/GUI/" + directory + "/" + urlFXML + ".fxml"));
                Scene scene = new Scene(homeRoot);
                stage.setScene(scene);

            // Ouvre la stage avec la nouvelle Scene
                stage.show();

        } catch (IOException e) {
            System.err.printf("Error: %s%n", e.getMessage());
        }
    }
}
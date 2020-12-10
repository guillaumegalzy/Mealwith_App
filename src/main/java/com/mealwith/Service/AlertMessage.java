package com.mealwith.Service;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AlertMessage {

    /**
     * Affiche un message d'alerte configurés avec les paramètres
     * @param alertType Type d'alerte
     * @param message Message apparaissant dans l'alerte
     * @param buttonType Type de bouton
     */
    public void Alert(Alert.AlertType alertType, String message, ButtonType buttonType) {
        Alert alert = new Alert(alertType, message, buttonType);
        alert.showAndWait();
    }
}

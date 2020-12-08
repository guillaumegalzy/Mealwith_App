package com.mealwith.DAO;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

abstract class DAO {
    Connection con;

    /**
     * Connection automatique à la base de données lors de la création d'une instance de DAO
     * || Construction avec paramètres préconfigurés pour la base de données Mealwith
     */
    public DAO() {
        String url = "jdbc:mariadb://localhost:3306/mealwith";
        String user = "root";
        String password = "";

        try {
            this.con = DriverManager.getConnection(url,user,password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Error while trying to access database", ButtonType.CLOSE);
            alert.showAndWait();
        }
    }
}

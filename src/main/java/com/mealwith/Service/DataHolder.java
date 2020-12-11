package com.mealwith.Service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public final class DataHolder {

    private final static DataHolder INSTANCE = new DataHolder();
    private Connection con;

    private DataHolder(){setCon();};

    private ArrayList<Object> list = new ArrayList<>();

    public static DataHolder getINSTANCE(){
        return INSTANCE;
    }

    public void setList(ArrayList<Object> list){
         this.list = list;
    }

    public ArrayList<Object> getList(){
         return list;
    }

    /**
     * Connection automatique à la base de données lors de la création d'une instance de DAO
     * || Construction avec paramètres préconfigurés pour la base de données Mealwith
     */
    public void setCon(){
        try {
            this.con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/mealwith","root","");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Error while trying to access database", ButtonType.CLOSE);
            alert.showAndWait();
        }
    }

    /**
     * Récupère la connection déjà existante, si elle est fermée, en ouvre une nouvelle
     * @return La connection
     * @throws SQLException erreur possible car accès à la BDD
     */
    // Récupère la connection déjà existante, si elle est fermée, en ouvre une nouvelle
    public Connection getCon() throws SQLException {
        if (this.con.isClosed()){
            setCon();
        }
        return con;
    }

    /**
     * Gère le changement de scene via passage d'un actionEvent et de l'URL du FXML adéquat
     * @param stage stage appelant la fonction
     * @param dossier nom du dossier
     * @param fichier nom du fichier FXML à charger
     */
    public void ChangeScene(Stage stage, String dossier, String fichier)
    {
        Parent root = null;
        try {
            root = FXMLLoader.load(this.getClass().getResource("../GUI/" + dossier + "/" + fichier + ".fxml"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}
package com.mealwith.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DAO {
    Connection con;

    /**
     * Connection automatique à la base de données lors de la création d'une instance de DAO
     * || Construction avec paramètres
     * @param url Url de la base de données
     * @param user Nom de l'utilisateur désiré
     * @param password Mdp correspondant à l'utilisateur désiré
     */
    public DAO(String url, String user, String password) {
        try {
            this.con = DriverManager.getConnection(url,"root","");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Connection automatique à la base de données lors de la création d'une instance de DAO
     * || Construction sans paramètres
     */
    public DAO() {
        String url = "jdbc:mariadb://localhost:3306/mealwith";
        String user = "root";
        String password = "";

        try {
            this.con = DriverManager.getConnection(url,user,password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

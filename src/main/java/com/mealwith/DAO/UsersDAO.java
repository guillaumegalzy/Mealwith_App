package com.mealwith.DAO;

import com.mealwith.Entity.Users;
import com.mealwith.Service.DataHolder;
import javafx.collections.FXCollections;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UsersDAO{
    private final List<Users> repoUsers = FXCollections.observableArrayList();

    public Object FindByMail(String mail) throws SQLException {
        // Création de la requête préparée de recherche du user
            PreparedStatement oneByMail = DataHolder.getINSTANCE().getCon().prepareStatement("SELECT * from users where email=?");

        // Définit le critère de recherche pour la requête préparée
            oneByMail.setString(1,mail);

        // Exécute la requête et récupération du résultat
            ResultSet result = oneByMail.executeQuery();

        // Si le resultat est vide, alors retourne null. Sinon on retourne l'utilisateur
        if (!result.first()){
            return null;
        } else {
            // On se positionne au premier emplacement du ResultSet
            result.first();

            // Clos la connection
            DataHolder.getINSTANCE().getCon().close();

            // Création du user en utilisant le constructeur spécifique renseignant l'ensemble des membres
            return new Users(
                    result.getInt(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getString(7),
                    result.getString(8),
                    result.getInt(8)
            );
        }
    }
}
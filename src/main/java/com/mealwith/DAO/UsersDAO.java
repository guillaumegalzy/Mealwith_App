package com.mealwith.DAO;

import com.mealwith.Entity.Users;
import javafx.collections.FXCollections;

import java.sql.*;
import java.util.List;

public class UsersDAO extends DAO{
    private final List<Users> repoUsers = FXCollections.observableArrayList();

    public UsersDAO() {
        // Utilisation du constructeur par défault, sans paramètres, de la classe parente DAO préparamétrée pour la BDD 'mealwith'
        super();
    }

    public void Insert(Users users) throws SQLException {
        // Création de la requête d'ajout d'un utilisateur
           

        // Définit les paramètres pour la requête préparée


        // Exécute la requête d'ajout


        // Ferme la requête
    }

    public void Update(Users users) throws SQLException {
        // Création de la requête de màj d'un user
        
        // Définit les paramètres pour la requête préparée

        // Exécute la requête

        // Ferme la requête
    }

    public void Delete(Users users) throws SQLException {
        // Création de la requête de recherche de l'ensemble des users

        // Définit le critère de recherche pour la requête préparée

        // Exécute la requête

        // Ferme la requête
    }

    public Object FindByMail(String mail) throws SQLException {
        // Création de la requête préparée de recherche du user
            PreparedStatement oneByMail = con.prepareStatement("SELECT * from users where email=?");

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

    /**
     * Récupère l'ensemble des users de la BDD, trié par id croissant
     * @return une list de user
     * @throws SQLException
     */
    public List<Users> List() throws SQLException {
        // Création de la requête de recherche de l'ensemble des users

        // Exécute la requête et récupération du résultat

        // Ferme la requête

        return repoUsers;
    }

}
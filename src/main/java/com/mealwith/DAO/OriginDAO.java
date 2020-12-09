package com.mealwith.DAO;

import com.mealwith.Entity.Origin;
import javafx.collections.FXCollections;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class OriginDAO extends DAO{
    private final List<Origin> repoOrigin = FXCollections.observableArrayList();

    public OriginDAO() {
        // Utilisation du constructeur par défault, sans paramètres, de la classe parente DAO préparamétrée pour la BDD 'mealwith'
        super();
    }

    public String GetCountryByID(int id) throws SQLException {
        // Création de la requête de recherche de l'ensemble des users
            PreparedStatement getCountryByID = con.prepareStatement("SELECT country from origin WHERE id=?");

        // Définit le critère de recherche pour la requête préparée
            getCountryByID.setInt(1,id);

        // Exécute la requête
            ResultSet result = getCountryByID.executeQuery();

            result.first();

        // Ferme la requête
            getCountryByID.close();

        return result.getString(1);
    }

    /**
     * Récupère l'ensemble des origines de la BDD, trié par id croissant
     * @return la liste des origines inscrit dans la BDD
     * @throws SQLException erreur lors de la requête SQL
     */
    public List<Origin> List() throws SQLException {
        // Création de la requête de recherche de l'ensemble des origines
            Statement listAll = con.createStatement();

        // Exécute la requête et récupération du résultat
            ResultSet result = listAll.executeQuery("SELECT * from origin ORDER BY id");

        while(result.next()){
            // Ajoute l'origine dans le repo
            repoOrigin.add(new Origin(
                            result.getInt(1),
                            result.getString(2),
                            result.getString(3)
                    )
            );
        }

        // Ferme la requête
            listAll.close();

        // Ferme le Resulset
            result.close();

        return repoOrigin;
    }
}
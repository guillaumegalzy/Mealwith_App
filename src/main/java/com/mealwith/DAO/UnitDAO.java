package com.mealwith.DAO;

import com.mealwith.Entity.Unit;
import javafx.collections.FXCollections;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UnitDAO extends DAO{
    private final List<Unit> repoUnit = FXCollections.observableArrayList();

    public UnitDAO() {
        // Utilisation du constructeur par défault, sans paramètres, de la classe parente DAO préparamétrée pour la BDD 'mealwith'
        super();
    }

    public String GetTypeByID(int id) throws SQLException {
        // Création de la requête de recherche de l'ensemble des users
            PreparedStatement getTypeByID = con.prepareStatement("SELECT type from units WHERE id=?");

        // Définit le critère de recherche pour la requête préparée
            getTypeByID.setInt(1,id);

        // Exécute la requête
            ResultSet result = getTypeByID.executeQuery();

            result.first();

        // Ferme la requête
            getTypeByID.close();

        return result.getString(1);
    }

    /**
     * Récupère l'ensemble des unités de la BDD, trié par id croissant
     * @return la liste des unités inscrit dans la BDD
     * @throws SQLException erreur lors de la requête SQL
     */
    public List<Unit> List() throws SQLException {
        // Création de la requête de recherche de l'ensemble des unités
            Statement listAll = con.createStatement();

        // Exécute la requête et récupération du résultat
            ResultSet result = listAll.executeQuery("SELECT * from units ORDER BY id");

        while(result.next()){
            // Ajoute l'unité dans le repo
            repoUnit.add(new Unit(
                            result.getInt(1),
                            result.getString(2)
                    )
            );
        }

        // Ferme la requête
            listAll.close();

        // Ferme le Resulset
            result.close();

        return repoUnit;
    }
}

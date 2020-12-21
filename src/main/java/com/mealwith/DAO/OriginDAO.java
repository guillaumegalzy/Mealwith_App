package com.mealwith.DAO;

import com.mealwith.Entity.Origin;
import com.mealwith.Service.DataHolder;
import javafx.collections.FXCollections;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class OriginDAO{
    private final List<Origin> repoOrigin = FXCollections.observableArrayList();

    public String GetCountryByID(int id) throws SQLException {
        // Création de la requête de recherche de l'ensemble des pays
            PreparedStatement getCountryByID = DataHolder.getINSTANCE().getCon().prepareStatement("SELECT country from origin WHERE id=?");

        // Définit le critère de recherche pour la requête préparée
            getCountryByID.setInt(1,id);

        // Exécute la requête
            ResultSet result = getCountryByID.executeQuery();

            result.first();

        // Ferme la requête
            getCountryByID.close();

        // Clos la connection
        DataHolder.getINSTANCE().getCon().close();

        return result.getString(1);
    }

    /**
     * Trouve l'origine portant un ID spécifique
     * @param id ID de l'origine à récupérer
     * @return Origine
     * @throws SQLException Exception possible liée à l'usage de la BDD
     */
    public Origin FindByID(int id) throws SQLException {
        // Création de la requête de recherche de l'ensemble des pays
        PreparedStatement findOriginByID = DataHolder.getINSTANCE().getCon().prepareStatement("SELECT * from origin WHERE id=?");

        // Définit le critère de recherche pour la requête préparée
        findOriginByID.setInt(1,id);

        // Exécute la requête
        ResultSet result = findOriginByID.executeQuery();

        result.first();

        // Ferme la requête
        findOriginByID.close();

        // Clos la connection
        DataHolder.getINSTANCE().getCon().close();

        return new Origin(
                result.getInt(1),
                result.getString(2),
                result.getString(3));
    }

    /**
     * Récupère l'ensemble des origines de la BDD, trié par id croissant
     * @return la liste des origines inscrit dans la BDD
     * @throws SQLException erreur lors de la requête SQL
     */
    public List<Origin> List() throws SQLException {
        // Création de la requête de recherche de l'ensemble des origines
            Statement listAll = DataHolder.getINSTANCE().getCon().createStatement();

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

        // Clos la connection
        DataHolder.getINSTANCE().getCon().close();
        return repoOrigin;
    }
}
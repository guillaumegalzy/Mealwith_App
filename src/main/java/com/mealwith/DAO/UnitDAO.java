package com.mealwith.DAO;

import com.mealwith.Entity.Unit;
import com.mealwith.Service.DataHolder;
import javafx.collections.FXCollections;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UnitDAO{
    private final List<Unit> repoUnit = FXCollections.observableArrayList();

    public String GetTypeByID(int id) throws SQLException {
        // Création de la requête de recherche de l'ensemble des users
            PreparedStatement getTypeByID = DataHolder.getINSTANCE().getCon().prepareStatement("SELECT type from units WHERE id=?");

        // Définit le critère de recherche pour la requête préparée
            getTypeByID.setInt(1,id);

        // Exécute la requête
            ResultSet result = getTypeByID.executeQuery();

            result.first();

        // Ferme la requête
            getTypeByID.close();

        // Clos la connection
            DataHolder.getINSTANCE().getCon().close();

        return result.getString(1);
    }

    /**
     * Trouve l'unité portant un ID spécifique
     * @param id ID de l'unité à récupérer
     * @return Unité
     * @throws SQLException Exception possible liée à l'usage de la BDD
     */
    public Unit FindByID(int id) throws SQLException {
        // Création de la requête de recherche de l'ensemble des pays
        PreparedStatement findUnitByID = DataHolder.getINSTANCE().getCon().prepareStatement("SELECT * from units WHERE id=?");

        // Définit le critère de recherche pour la requête préparée
        findUnitByID.setInt(1,id);

        // Exécute la requête
        ResultSet result = findUnitByID.executeQuery();

        result.first();

        // Ferme la requête
        findUnitByID.close();

        // Clos la connection
        DataHolder.getINSTANCE().getCon().close();

        return new Unit(
                result.getInt(1),
                result.getString(2));
    }

    /**
     * Récupère l'ensemble des unités de la BDD, trié par id croissant
     * @return la liste des unités inscrit dans la BDD
     * @throws SQLException erreur lors de la requête SQL
     */
    public List<Unit> List() throws SQLException {
        // Création de la requête de recherche de l'ensemble des unités
            Statement listAll = DataHolder.getINSTANCE().getCon().createStatement();

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

        // Clos la connection
            DataHolder.getINSTANCE().getCon().close();

        return repoUnit;
    }
}

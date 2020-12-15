package com.mealwith.DAO;

import com.mealwith.Entity.CategoriesRecipes;
import com.mealwith.Service.DataHolder;
import javafx.collections.FXCollections;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class CategoriesRecipesDAO {
    private final List<CategoriesRecipes> repoCategories = FXCollections.observableArrayList();

    /**
     * Trouve le nom de la catégorie grâce à son ID
     * @param categoryID ID de la catégorie pour laquelle on veut récupérer le nom
     * @return Nom de la catégorie envoyé en paramètre
     * @throws SQLException SQLException Exception possible liée à l'usage de la BDD
     */
    public String GetNameByID(int categoryID) throws SQLException {
        // Création de la requête de recherche de l'ensemble des users
            PreparedStatement getNameByID = DataHolder.getINSTANCE().getCon().prepareStatement("SELECT name from recipe_category WHERE id=?");

        // Définit le critère de recherche pour la requête préparée
            getNameByID.setInt(1,categoryID);

        // Exécute la requête
            ResultSet result = getNameByID.executeQuery();

            result.first();

        // Ferme la requête
            getNameByID.close();

        // Clos la connection
            DataHolder.getINSTANCE().getCon().close();
        return result.getString(1);
    }

    /**
     * Trouve l'ID de la catégorie grâce à son nom
     * @param categoryName Nom de la catégorie pour laquelle on veut récupérer l'ID
     * @return ID de la catégorie envoyé en paramètre
     * @throws SQLException Exception possible liée à l'usage de la BDD
     */
    public int GetIDByName(String categoryName) throws SQLException {
        // Création de la requête de recherche de l'ensemble des users
        PreparedStatement getIDByName = DataHolder.getINSTANCE().getCon().prepareStatement("SELECT id from recipe_category WHERE name=?");

        // Définit le critère de recherche pour la requête préparée
            getIDByName.setString(1,categoryName);

        // Exécute la requête
            ResultSet result = getIDByName.executeQuery();

            result.first();

        // Ferme la requête
            getIDByName.close();

        // Clos la connection
            DataHolder.getINSTANCE().getCon().close();

            return result.getInt(1);
    }

    /**
     * Récupère l'ensemble des users de la BDD, trié par id croissant
     * @return une list de user
     * @throws SQLException Exception possible liée à l'usage de la BDD
     */
    public List<CategoriesRecipes> List() throws SQLException {
        // Création de la requête de recherche de l'ensemble des users
            Statement listAll = DataHolder.getINSTANCE().getCon().createStatement();

        // Exécute la requête et récupération du résultat
            ResultSet result = listAll.executeQuery("SELECT * from recipe_category");

            while(result.next()){
                repoCategories.add(new CategoriesRecipes(
                        result.getInt(1),
                        result.getString(2)
                    )
                );
            }

        // Ferme la requête
            result.close();

        // Ferme le Resulset
            listAll.close();

        // Clos la connection
            DataHolder.getINSTANCE().getCon().close();

        return repoCategories;
    }
}

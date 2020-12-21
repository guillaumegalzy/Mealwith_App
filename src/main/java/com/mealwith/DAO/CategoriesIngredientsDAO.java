package com.mealwith.DAO;

import com.mealwith.Entity.CategoriesIngredients;
import com.mealwith.Service.DataHolder;
import javafx.collections.FXCollections;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class CategoriesIngredientsDAO{
    private final List<CategoriesIngredients> repoCategories = FXCollections.observableArrayList();

    /**
     * Trouve le nom de la catégorie grâce à son ID
     * @param categoryID ID de la catégorie pour laquelle on veut récupérer le nom
     * @return Nom de la catégorie envoyé en paramètre
     * @throws SQLException SQLException Exception possible liée à l'usage de la BDD
     */
    public String GetNameByID(int categoryID) throws SQLException {
        // Création de la requête de recherche de l'ensemble des users
            PreparedStatement getNameByID = DataHolder.getINSTANCE().getCon().prepareStatement("SELECT name from ingredient_category WHERE id=?");

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
        PreparedStatement getIDByName = DataHolder.getINSTANCE().getCon().prepareStatement("SELECT id from ingredient_category WHERE name=?");

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
     * Trouve la catégorie d'ingrédient portant un ID spécifique
     * @param id ID de la catégorie à récupérer
     * @return Catégorie d'ingrédient
     * @throws SQLException Exception possible liée à l'usage de la BDD
     */
    public CategoriesIngredients FindByID(int id) throws SQLException {
        // Création de la requête de recherche de l'ensemble des pays
        PreparedStatement findCatIngByID = DataHolder.getINSTANCE().getCon().prepareStatement("SELECT * from ingredient_category WHERE id=?");

        // Définit le critère de recherche pour la requête préparée
        findCatIngByID.setInt(1,id);

        // Exécute la requête
        ResultSet result = findCatIngByID.executeQuery();

        result.first();

        // Ferme la requête
        findCatIngByID.close();

        // Clos la connection
        DataHolder.getINSTANCE().getCon().close();

        return new CategoriesIngredients(
                result.getInt(1),
                result.getString(2));
    }

    /**
     * Récupère l'ensemble des users de la BDD, trié par id croissant
     * @return une list de user
     * @throws SQLException Exception possible liée à l'usage de la BDD
     */
    public List<CategoriesIngredients> List() throws SQLException {
        // Création de la requête de recherche de l'ensemble des users
            Statement listAll = DataHolder.getINSTANCE().getCon().createStatement();

        // Exécute la requête et récupération du résultat
            ResultSet result = listAll.executeQuery("SELECT * from ingredient_category");

            while(result.next()){
                repoCategories.add(new CategoriesIngredients(
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

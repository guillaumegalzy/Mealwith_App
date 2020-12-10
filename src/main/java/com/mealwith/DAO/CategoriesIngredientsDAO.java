package com.mealwith.DAO;

import com.mealwith.Entity.CategoriesIngredients;
import javafx.collections.FXCollections;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class CategoriesIngredientsDAO extends DAO{
    private final List<CategoriesIngredients> repoCategories = FXCollections.observableArrayList();

    public CategoriesIngredientsDAO() {
        // Utilisation du constructeur par défault, sans paramètres, de la classe parente DAO préparamétrée pour la BDD 'mealwith'
        super();
    }

    public void Insert(CategoriesIngredients categories) throws SQLException {
        // Création de la requête d'ajout d'un utilisateur


        // Définit les paramètres pour la requête préparée


        // Exécute la requête d'ajout


        // Ferme la requête
    }

    public void Update(CategoriesIngredients categories) throws SQLException {
        // Création de la requête de màj d'un user

        // Définit les paramètres pour la requête préparée

        // Exécute la requête

        // Ferme la requête
    }

    public void Delete(CategoriesIngredients categories) throws SQLException {
        // Création de la requête de recherche de l'ensemble des users

        // Définit le critère de recherche pour la requête préparée

        // Exécute la requête

        // Ferme la requête
    }

    public String GetNameByID(int id) throws SQLException {
        // Création de la requête de recherche de l'ensemble des users
            PreparedStatement getNameByID = con.prepareStatement("SELECT name from ingredient_category WHERE id=?");

        // Définit le critère de recherche pour la requête préparée
            getNameByID.setInt(1,id);

        // Exécute la requête
            ResultSet result = getNameByID.executeQuery();

            result.first();

        // Ferme la requête
            getNameByID.close();

        return result.getString(1);
    }

    /**
     * Récupère l'ensemble des users de la BDD, trié par id croissant
     * @return une list de user
     * @throws SQLException Exception possible liée à l'usage de la BDD
     */
    public List<CategoriesIngredients> List() throws SQLException {
        // Création de la requête de recherche de l'ensemble des users
            Statement listAll = con.createStatement();

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

        return repoCategories;
    }
}

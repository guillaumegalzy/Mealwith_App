package com.mealwith.DAO;

import com.mealwith.Entity.Ingredients;
import com.mealwith.Entity.Recipes;
import com.mealwith.Service.DataHolder;
import javafx.collections.FXCollections;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class RecipesDAO {
    private final List<Recipes> repoRecipes = FXCollections.observableArrayList();

    /**
     * Récupère l'ensemble des recipes de la BDD, trié par id croissant
     * @return une list de recipes
     * @throws SQLException erreur lors de la requête SQL
     */
    public List<Recipes> List() throws SQLException {
        // Création de la requête de recherche de l'ensemble des ingrédients
            Statement listAll = DataHolder.getINSTANCE().getCon().createStatement();

        // Exécute la requête et récupération du résultat
            ResultSet resultRecipes = listAll.executeQuery("SELECT * from recipes ORDER BY id");

        while(resultRecipes.next()){
            // Ajoute l'ingrédient dans le repo
                repoRecipes.add(new Recipes(
                        resultRecipes.getInt(1),
                        resultRecipes.getInt(2),
                        resultRecipes.getString(4),
                        resultRecipes.getString(5),
                        new ImageView(new Image(resultRecipes.getString(5)))
                        ));
        }

        // Ferme la requête
            listAll.close();

        // Ferme le Resulset
        resultRecipes.close();

        // Clos la connection
            DataHolder.getINSTANCE().getCon().close();

        return repoRecipes;
    }

    /**
     * Récupère la liste des ingredients nécessaire à la recette, trié par id croissant d'ingredient
     * @return une list d'ingredient
     * @throws SQLException erreur lors de la requête SQL
     */
    public List<Ingredients> ListIngredientsInRecipes(int recipeID) throws SQLException {
        List<Ingredients> repoIngredientInRecipes = FXCollections.observableArrayList();

        // Création de la requête de recherche de l'ensemble des ingrédients
            PreparedStatement listIngredients = DataHolder.getINSTANCE().getCon().prepareStatement("SELECT ingredient_id from ingredient_recipe WHERE recipe_id = ? ORDER BY ingredient_id");

        // Définition du paramètre de la requête
            listIngredients.setInt(1,recipeID);

        // Exécute la requête et récupération du résultat
            ResultSet resultIngredientsRecipes = listIngredients.executeQuery();

            IngredientsDAO repoIngredients = new IngredientsDAO();

        while(resultIngredientsRecipes.next()){
            // Ajoute l'ingrédient dans le repo
                repoIngredientInRecipes.add(repoIngredients.Find(resultIngredientsRecipes.getInt("ingredient_id")));
        }

        // Ferme la requête
            listIngredients.close();

        // Ferme le Resulset
            resultIngredientsRecipes.close();

        // Clos la connection
            DataHolder.getINSTANCE().getCon().close();

        return repoIngredientInRecipes;
    }
}
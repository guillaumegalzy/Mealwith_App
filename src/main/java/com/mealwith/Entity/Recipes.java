package com.mealwith.Entity;

import com.mealwith.DAO.CategoriesRecipesDAO;
import com.mealwith.DAO.RecipesDAO;
import javafx.scene.image.ImageView;

import java.sql.SQLException;
import java.util.List;

public class Recipes {
    private int id;
    private int category_id;
    private String category_name;
    private String name;
    private String picture;
    private ImageView picture_Img;
    private List<Ingredients> ingredientInRecipe;

    public Recipes(int id, int category_id, String name, String picture, ImageView picture_Img) throws SQLException {

        this.id = id;

        this.category_id = category_id;
        CategoriesRecipesDAO repoCatRec = new CategoriesRecipesDAO();
        this.category_name = repoCatRec.GetNameByID(category_id);

        this.name = name;

        this.picture = picture;
        this.picture_Img = picture_Img;
        this.picture_Img.setFitWidth(100);
        this.picture_Img.setFitHeight(100);
        this.picture_Img.setPreserveRatio(true);
    }

    public void setIngredientInRecipe() throws SQLException {
        RecipesDAO recipesDAO = new RecipesDAO();
        this.ingredientInRecipe = recipesDAO.ListIngredientsInRecipes(this.id);
    }

    public List<Ingredients> getIngredientInRecipe() {
        return ingredientInRecipe;
    }

    public String getCategory_name() {
        return category_name;
    }

    public ImageView getPicture_Img() {
        return picture_Img;
    }

    public int getId() {
        return id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }
}

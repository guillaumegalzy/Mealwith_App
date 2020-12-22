package com.mealwith.DAO;

import com.mealwith.Entity.Ingredients;
import com.mealwith.Service.DataHolder;
import javafx.collections.FXCollections;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class IngredientsDAO{
    private final List<Ingredients> repoIngredients = FXCollections.observableArrayList();

    public void Insert(Ingredients ingredients) throws SQLException {
        // Création de la requête d'ajout d'un utilisateur
            PreparedStatement insertOne = DataHolder.getINSTANCE().getCon().prepareStatement("INSERT into ingredients (category_id,origin_id,unit_id,name,price,temp_min,temp_max,shelf_life,picture) values (?,?,?,?,?,?,?,?,?)");

        // Définit les paramètres pour la requête préparée
            insertOne.setInt(1, ingredients.getCategory_id());
            insertOne.setInt(2, ingredients.getOrigin_id());
            insertOne.setInt(3, ingredients.getUnit_id());
            insertOne.setString(4, ingredients.getName());
            insertOne.setDouble(5, ingredients.getPrice());
            insertOne.setInt(6, ingredients.getTemp_min());
            insertOne.setInt(7, ingredients.getTemp_max());
            insertOne.setInt(8, ingredients.getShelf_life());
            insertOne.setString(9, ingredients.getPicture());

        // Exécute la requête d'ajout
            insertOne.executeQuery();

        // Ferme la requête
            insertOne.close();
    }

    /**
     * Met à jour l'ingrédient envoyé en paramètre dans la BDD
     * @param ingredients Ingrédient avec ses membres mis à jour
     */
    public void Update(Ingredients ingredients) throws SQLException {
        // Création de la requête de màj d'un user
            PreparedStatement updateOne = DataHolder.getINSTANCE().getCon().prepareStatement(
                    "UPDATE ingredients " +
                            "SET category_id = ?,origin_id = ?,unit_id = ?,name = ?,price = ?,temp_min=?,temp_max=?,shelf_life=?,picture=?" +
                            "WHERE id=?");

        // Définit les paramètres pour la requête préparée
            updateOne.setInt(1,ingredients.getCategory_id());
            updateOne.setInt(2,ingredients.getOrigin_id());
            updateOne.setInt(3,ingredients.getUnit_id());
            updateOne.setString(4,ingredients.getName());
            updateOne.setDouble(5,ingredients.getPrice());
            updateOne.setInt(6,ingredients.getTemp_min());
            updateOne.setInt(7,ingredients.getTemp_max());
            updateOne.setInt(8,ingredients.getShelf_life());
            updateOne.setString(9,ingredients.getPicture());

            updateOne.setInt(10,ingredients.getId()); // ID de l'ingredient à mettre à jour

        // Exécute la requête
            updateOne.executeUpdate();

        // Ferme la requête
            updateOne.close();

        // Clos la connection
            DataHolder.getINSTANCE().getCon().close();
    }

    /**
     * Récupère un ingredient spécifique en BDD via son ID
     * @param ingredientID ID de l'ingredient en BDD
     * @return Ingredients
     */
    public Ingredients Find(int ingredientID) throws SQLException {
        // Création de la requête de recherche de l'ensemble des ingredients
            PreparedStatement findOne = DataHolder.getINSTANCE().getCon().prepareStatement("SELECT * from ingredients WHERE id =?");

        // Définit le critère de recherche pour la requête préparée
            findOne.setInt(1, ingredientID);

        // Exécute la requête
            ResultSet result = findOne.executeQuery();

        // Ferme la requête
            findOne.close();

        // Clos la connection
            DataHolder.getINSTANCE().getCon().close();

            result.first();

        return new Ingredients(
                result.getInt(1),
                result.getInt(2),
                result.getInt(3),
                result.getInt(4),
                result.getString(5),
                result.getDouble(6),
                result.getInt(7),
                result.getInt(8),
                result.getInt(9),
                result.getString(10),
                new ImageView(new Image(result.getString(10)))
        );
    }

    /**
     * Récupère un ingredient spécifique en BDD via son ID
     * @param ingredientName Nom de l'ingredient en BDD
     * @return Ingredients
     */
    public Ingredients FindByName(String ingredientName) throws SQLException {
        // Création de la requête de recherche de l'ensemble des ingredients
            PreparedStatement findOne = DataHolder.getINSTANCE().getCon().prepareStatement("SELECT * from ingredients WHERE name =?");

        // Définit le critère de recherche pour la requête préparée
            findOne.setString(1, ingredientName);

        // Exécute la requête
            ResultSet result = findOne.executeQuery();

        // Ferme la requête
            findOne.close();

        // Clos la connection
            DataHolder.getINSTANCE().getCon().close();

            result.first();

            return new Ingredients(
                    result.getInt(1),
                    result.getInt(2),
                    result.getInt(3),
                    result.getInt(4),
                    result.getString(5),
                    result.getDouble(6),
                    result.getInt(7),
                    result.getInt(8),
                    result.getInt(9),
                    result.getString(10),
                    new ImageView(new Image(result.getString(10)))
            );
    }

    public void Delete(Ingredients ingredients) throws SQLException {
        // Création de la requête de recherche de l'ensemble des ingredients
            PreparedStatement delOne = DataHolder.getINSTANCE().getCon().prepareStatement("DELETE from ingredients WHERE id =?");

        // Définit le critère de recherche pour la requête préparée
            delOne.setInt(1, ingredients.getId());

        // Exécute la requête
            delOne.execute();

        // Ferme la requête
            delOne.close();

        // Clos la connection
            DataHolder.getINSTANCE().getCon().close();
    }

    /**
     * Récupère l'ensemble des ingredients de la BDD, trié par id croissant
     * @return une list de user
     * @throws SQLException erreur lors de la requête SQL
     */
    public List<Ingredients> List() throws SQLException {
        // Création de la requête de recherche de l'ensemble des ingrédients
            Statement listAll = DataHolder.getINSTANCE().getCon().createStatement();

        // Exécute la requête et récupération du résultat
            ResultSet result = listAll.executeQuery("SELECT * from ingredients ORDER BY id");

        while(result.next()){
            // Ajoute l'ingrédient dans le repo
                repoIngredients.add(new Ingredients(
                                result.getInt(1),
                                result.getInt(2),
                                result.getInt(3),
                                result.getInt(4),
                                result.getString(5),
                                result.getDouble(6),
                                result.getInt(7),
                                result.getInt(8),
                                result.getInt(9),
                                result.getString(10),
                                new ImageView(new Image(result.getString(10)))
                        )
                );
        }

        // Ferme la requête
            listAll.close();

        // Ferme le Resulset
            result.close();

        // Clos la connection
            DataHolder.getINSTANCE().getCon().close();

        return repoIngredients;
    }
}
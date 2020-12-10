package com.mealwith.DAO;

import com.mealwith.Entity.Ingredients;
import javafx.collections.FXCollections;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class IngredientsDAO extends DAO{
    private final List<Ingredients> repoIngredients = FXCollections.observableArrayList();

    public IngredientsDAO() {
        // Utilisation du constructeur par défault, sans paramètres, de la classe parente DAO préparamétrée pour la BDD 'mealwith'
        super();
    }

    public void Insert(Ingredients ingredients) throws SQLException {
        // Création de la requête d'ajout d'un utilisateur


        // Définit les paramètres pour la requête préparée


        // Exécute la requête d'ajout


        // Ferme la requête
    }

    public void Update(Ingredients ingredients) throws SQLException {
        // Création de la requête de màj d'un user

        // Définit les paramètres pour la requête préparée

        // Exécute la requête

        // Ferme la requête
    }

    public void Delete(Ingredients ingredients) throws SQLException {
        // Création de la requête de recherche de l'ensemble des ingredients
            PreparedStatement delOne = con.prepareStatement("DELETE from ingredients WHERE id =?");

        // Définit le critère de recherche pour la requête préparée
            delOne.setInt(1, ingredients.getId());

        // Exécute la requête
            delOne.execute();

        // Ferme la requête
            delOne.close();
    }

    public void Find() throws SQLException {

    }

    /**
     * Récupère l'ensemble des ingredients de la BDD, trié par id croissant
     * @return une list de user
     * @throws SQLException erreur lors de la requête SQL
     */
    public List<Ingredients> List() throws SQLException {
        // Création de la requête de recherche de l'ensemble des ingrédients
            Statement listAll = con.createStatement();

        // Exécute la requête et récupération du résultat
            ResultSet result = listAll.executeQuery("SELECT * from ingredients ORDER BY id");

        while(result.next()){
            // Crée une image à partir de l'url inscrit dans la bdd sous le champ 'picture'
                Image image = new Image(result.getString(10));
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                imageView.setPreserveRatio(true);

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
                                imageView
                        )
                );
        }

        // Ferme la requête
            listAll.close();

        // Ferme le Resulset
            result.close();

        return repoIngredients;
    }
}
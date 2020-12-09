package com.mealwith.DAO;

import com.mealwith.Entity.CategoriesIngredients;
import com.mealwith.Entity.Ingredients;
import javafx.collections.FXCollections;

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
        // Création de la requête de recherche de l'ensemble des users

        // Définit le critère de recherche pour la requête préparée

        // Exécute la requête

        // Ferme la requête
    }

    public void Find() throws SQLException {

    }

    /**
     * Récupère l'ensemble des users de la BDD, trié par id croissant
     * @return une list de user
     * @throws SQLException
     */
    public List<Ingredients> List() throws SQLException {
        // Création de la requête de recherche de l'ensemble des users
            Statement listAll = con.createStatement();

        // Exécute la requête et récupération du résultat
            ResultSet result = listAll.executeQuery("SELECT * from ingredients");

        while(result.next()){
            repoIngredients.add(new Ingredients(
                            result.getInt(2),
                            result.getInt(3),
                            result.getInt(4),
                            result.getString(5),
                            result.getDouble(6),
                            result.getInt(7),
                            result.getInt(8),
                            result.getInt(9),
                            result.getString(10)
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

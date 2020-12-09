package com.mealwith.gui.Ingredients;

import com.mealwith.DAO.IngredientsDAO;
import com.mealwith.Entity.Ingredients;
import com.mealwith.Service.SceneHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.controlsfx.control.textfield.CustomTextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class IngredientsController implements Initializable {
    @FXML
    public ImageView ImgLogo;
    @FXML
    public HBox Home;
    @FXML
    public TableColumn<String,Ingredients> column_ingredient;
    @FXML
    public TableColumn<String,Image> column_details,column_delete;
    @FXML
    public TableView<Ingredients> tab_ingredient;
    @FXML
    public CustomTextField test;

    public ObservableList<Ingredients> listIngredients = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Récupération des images
            Image Img = new Image("img/Logo Mealwith.png");
            ImgLogo.setImage(Img);

        // Gestionnaire d'écoute sur le logo pour renvoyer au menu
            Home.setOnMouseClicked(event -> {
                SceneHandler sceneHandler = new SceneHandler();
                sceneHandler.setScene(event,Home.getId());
            });

        // Récupération des ingrédients dans la BDD
            IngredientsDAO repoIngredients = new IngredientsDAO();
            try {
                    listIngredients.addAll(repoIngredients.List());

                // Lie le membre name de chaque ingrédients à la colonne du tableau
                    column_ingredient.setCellValueFactory(new PropertyValueFactory<>("name"));

                // Ajout les éléments du repo en tant que données du tableau
                    tab_ingredient.setItems(listIngredients);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        // Test autcomplete field
//            TextFields.bindAutoCompletion(test, Collections.addAll(listIngredients));

    }
}
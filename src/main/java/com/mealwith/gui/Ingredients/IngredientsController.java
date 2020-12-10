package com.mealwith.gui.Ingredients;

import com.mealwith.DAO.IngredientsDAO;
import com.mealwith.Entity.Ingredients;
import com.mealwith.Service.SceneHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class IngredientsController implements Initializable {
    @FXML
    public ImageView ImgLogo;
    @FXML
    public HBox Home;
    @FXML
    public TableColumn<String,Integer> column_ID;
    @FXML
    public TableColumn<String,String> column_ingredient;
    @FXML
    public TableColumn<String,Image> column_picture;
    @FXML
    public TableView<Ingredients> tab_ingredient;
    @FXML
    public Button btnDelete,btnDetails,btnModify,btnAdd;

    public ObservableList<Ingredients> listIngredients = FXCollections.observableArrayList();
    public SceneHandler sceneHandler = new SceneHandler();
    public static List<Object> dataSend = new ArrayList<>(); // Données qui seront stockées par ce controlleur et utilisé par le controlleur de destination

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Vide les données envoyées
            dataSend.clear();

        // Récupération des images
            Image Img = new Image("img/Logo Mealwith.png");
            ImgLogo.setImage(Img);

        // Gestionnaire d'écoute sur le logo pour renvoyer au menu
            Home.setOnMouseClicked(event -> sceneHandler.setScene(event,Home.getId(),Home.getId()));

        // Récupération des ingrédients dans la BDD
            IngredientsDAO repoIngredients = new IngredientsDAO();
            try {
                    listIngredients.addAll(repoIngredients.List());

                // Lie le membre approprié de chaque ingrédients à la colonne du tableau
                    column_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
                    column_ingredient.setCellValueFactory(new PropertyValueFactory<>("name"));
                    column_picture.setCellValueFactory(new PropertyValueFactory<>("picture_Img"));

                // Ajout les éléments du repo en tant que données du tableau
                    tab_ingredient.setItems(listIngredients);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
    }

    public void btnClick(ActionEvent actionEvent) throws SQLException {
        // Récupère l'ID du bouton dans une string
            String btnText = ((Button)actionEvent.getSource()).getId();

        //Action différente selon le boutton
            switch (btnText){
                // Ajout d'un ingredient à la base de données
                case "btnDetails":
                    // Informe le controlleur du formulaire que l'on veut effectuer un ajout
                        dataSend.add("Details"); //Stockage de l'opération demandée
                        dataSend.add(tab_ingredient.getSelectionModel().getSelectedItem()); //Stockage du ingredient concerné
                    // Redirection vers le formulaire d'ajout
                        sceneHandler.setScene(actionEvent,"Ingredients/Formulaire","Formulaire");
                    break;

                // Ajout d'un ingredient à la base de données
                case "btnAdd":
                    // Informe le controlleur du formulaire que l'on veut effectuer un ajout
                        dataSend.add("Add"); //Stockage de l'opération demandée
                    // Redirection vers le formulaire d'ajout
                    sceneHandler.setScene(actionEvent,"Ingredients/Formulaire","Formulaire");
                    break;

                // Modification d'un ingredient existant
                case "btnModify":
                    // Informe le controlleur du formulaire que l'on veut effectuer une modification
                        dataSend.add("Modify"); //Stockage de l'opération demandée
                        dataSend.add(tab_ingredient.getSelectionModel().getSelectedItem()); //Stockage du ingredient concerné
                    // Redirection vers le formulaire de modification
                    sceneHandler.setScene(actionEvent,"Ingredients/Formulaire","Formulaire");
                    break;

                // Suppression d'un ingredient à la base de données
                case "btnDelete":
                    // Récupération de l'ID du ingredient à supprimer
                        Ingredients ingredients = tab_ingredient.getSelectionModel().getSelectedItem();

                    // Le supprime de la BDD
                        IngredientsDAO repoIngredients = new IngredientsDAO();
                        repoIngredients.Delete(ingredients);

                    // Le supprime de l'observableList
                        listIngredients.remove(ingredients);
                    break;

                default:break;
            }
    }
}
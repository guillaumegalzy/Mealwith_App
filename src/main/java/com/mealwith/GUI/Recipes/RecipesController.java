package com.mealwith.GUI.Recipes;

import com.mealwith.DAO.RecipesDAO;
import com.mealwith.Entity.Recipes;
import com.mealwith.GUI.Recipes.Formulaire.FormulaireController;
import com.mealwith.Service.AlertMessage;
import com.mealwith.Service.CustomsFonts;
import com.mealwith.Service.DataHolder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class RecipesController implements Initializable {
    @FXML
    public ImageView ImgLogo;
    @FXML
    public HBox Home;
    @FXML
    public VBox catMenu;
    @FXML
    public TableColumn<String, Integer> column_ID;
    @FXML
    public TableColumn<String, String> column_name, column_category;
    @FXML
    public TableColumn<String, Image> column_picture;
    @FXML
    public TableView<Recipes> tab_recipes;
    @FXML
    public Button btnDetails;
    @FXML
    public Label catAll, catBreak, catDess, catMain, catSide,catSnack,catSoup;
    @FXML
    public FontIcon iconTri, catAllIcon, catBreakIcon, catDessIcon, catMainIcon, catSideIcon,catSoupIcon,catSnackIcon;

    private final CustomsFonts customsFonts = new CustomsFonts(); // Service permettant de stocker les Fonts utilisés dans le projet
    public Text textLogo; // Logotype

    public ObservableList<Recipes> listRecipes = FXCollections.observableArrayList();
    public static List<Object> dataSend = new ArrayList<>(); // Données qui seront stockées par ce controlleur et utilisé par le controlleur de destination
    public static List<Object> dataReceive = new ArrayList<>(); // Stockage des données récupérées du controlleur de provenance
    public Map<Label, FontIcon> catFilter = new HashMap<>(); // Map associant les noms des catérogies dans le menu de gauche avec leur icône respective
    public RecipesDAO repoRecipes = new RecipesDAO();
    public AlertMessage alert = new AlertMessage();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Changement de la font du Logo
        textLogo.setFont(customsFonts.LogoFont(Double.parseDouble("80")));

        // Vide les précédentes données récupérées et envoyées
            dataReceive.clear();
            dataSend.clear();

        // Récupération des images
        ImgLogo.setImage(new Image("img/Logo Mealwith.png"));

        // Gestionnaire d'écoute sur le logo pour renvoyer au menu
        Home.setOnMouseClicked(event -> DataHolder.getINSTANCE().ChangeScene((Stage) Home.getScene().getWindow(), Home.getId(), Home.getId()));

        // Récupération des données stockées par le formulaire
            getData();

        // Récupération des ingrédients dans la BDD
        if (!dataReceive.isEmpty()){
                listRecipes.addAll((Collection<? extends Recipes>) dataReceive.get(0));
        }else{
            try {
                listRecipes.addAll(repoRecipes.List());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
            // Lie le membre approprié de chaque ingrédients à la colonne du tableau
            column_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
            column_name.setCellValueFactory(new PropertyValueFactory<>("name"));
            column_category.setCellValueFactory(new PropertyValueFactory<>("category_name"));
            column_picture.setCellValueFactory(new PropertyValueFactory<>("picture_Img"));

            // Ajout les éléments du repo en tant que données du tableau
            tab_recipes.setItems(listRecipes);

        // Stocke les labels et leur icône respective dans la Map 'catFilter
            catFilter.put(catAll, catAllIcon);
            catFilter.put(catBreak, catBreakIcon);
            catFilter.put(catDess, catDessIcon);
            catFilter.put(catMain, catMainIcon);
            catFilter.put(catSide, catSideIcon);
            catFilter.put(catSnack, catSnackIcon);
            catFilter.put(catSoup, catSoupIcon);

        for (Map.Entry<Label, FontIcon> element : catFilter.entrySet()) {
            element.getKey().setStyle("");
        }
    }

    public void btnClick(ActionEvent actionEvent){
        if (tab_recipes.getSelectionModel().isEmpty()) {
            alert.Alert(AlertType.WARNING,"Please select a recipe before.",ButtonType.CLOSE);
        } else {
            // Informe le controlleur du formulaire que l'on veut effectuer un ajout
                dataSend.add(tab_recipes.getSelectionModel().getSelectedIndex()); //Stockage de la recette concernée
                dataSend.add(listRecipes); //Stockage de l'ensemble des recettes

            // Redirection vers le formulaire d'ajout
                DataHolder.getINSTANCE().ChangeScene((Stage) btnDetails.getScene().getWindow(), "Recipes/Formulaire", "Formulaire");
        }
    }

    /**
     * Récupère les datas stockées par le formulaire de recette
     */
    public void getData()  {
        dataReceive.addAll(FormulaireController.dataSend);
    }

    /**
     * Change  l'aspect visuelle de la catégorie sélectionnée en tant que filtre
     *
     * @param event Evenement déclenchant l'action
     */
    public void MenuClick(Event event) {
        Label choiceLabel = (Label) event.getSource();
        String categoryName = choiceLabel.getText();
        String labelID = choiceLabel.getId();
        for (Map.Entry<Label, FontIcon> element : catFilter.entrySet()) {
            if (element.getKey().getId().equals(labelID)) {
                element.getValue().setVisible(true);
                element.getKey().setStyle("-fx-font-weight: bold");
                if (!categoryName.equals("All")) {
                    FilteredList<Recipes> listRecipesFilter = listRecipes.filtered(recipes -> recipes.getCategory_name().equals(categoryName));
                    tab_recipes.setItems(listRecipesFilter);
                } else{
                    tab_recipes.setItems(listRecipes);
                }
            } else {
                element.getValue().setVisible(false);
                element.getKey().setStyle("");
            }
        }
    }
}
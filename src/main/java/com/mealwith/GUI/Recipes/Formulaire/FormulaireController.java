package com.mealwith.GUI.Recipes.Formulaire;

import com.mealwith.Entity.Ingredients;
import com.mealwith.Entity.Recipes;
import com.mealwith.GUI.Recipes.RecipesController;
import com.mealwith.Service.CustomsFonts;
import com.mealwith.Service.DataHolder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

public class FormulaireController implements Initializable {
    @FXML
    public HBox Home,RatingHBox,RatingHBoxStar;
    @FXML
    public ImageView ImgLogo, ImgRecipe;
    @FXML
    public Button btnCancel;
    @FXML
    public TextField inputName,inputID;
    @FXML
    public VBox VBoxIngredients;
    @FXML
    public TitledPane titlePaneIng;


    private final CustomsFonts customsFonts = new CustomsFonts(); // Service permettant de stocker les Fonts utilisés dans le projet
    public Text textLogo; // Logotype

    public static List<Object> dataReceive = new ArrayList<>(); // Stockage des données récupérées du controlleur de provenance
    public static List<Object> dataSend = new ArrayList<>(); // Données envoyés par ce formulaire
    public List<FontIcon> starRatings = new ArrayList<>();
    public List<Recipes> listRecipes = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Changement de la font du Logo
            textLogo.setFont(customsFonts.LogoFont(Double.parseDouble("80")));

        // Vide les précédentes données récupérées et envoyées
            dataReceive.clear();
            dataSend.clear();

        // Ajout gestionnaire d'écoute sur le logo pour renvoyer au menu
            Home.setOnMouseClicked(event -> DataHolder.getINSTANCE().ChangeScene((Stage) Home.getScene().getWindow(),"Home","Home"));

        // Récupération des images
            ImgLogo.setImage(new Image("img/Logo Mealwith.png"));
            ImgRecipe.setImage(new Image("img/Ingredients.png"));

        // Récupération des étoiles du ratings
            for (Node star: RatingHBoxStar.getChildren()) {
                starRatings.add((FontIcon) star);
            }

        // Récupération des données stockées par le controlleur de provenance
            getData();

        try {
            // Chargement des valeurs dans les champs
                loadPage();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Récupère les datas stockées par le controlleur principal et remplis les champs s'il s'agit d'une modification ou d'un détail
     */
    public void getData()  {
        // Récupération de la stage principale à partir du tableau
            dataReceive.addAll(RecipesController.dataSend);
            listRecipes.addAll((Collection<? extends Recipes>) RecipesController.dataSend.get(1));
    }

    /**
     * Fonction récupérant toute les informations nécessaire en cas de modification ou de visualisation d'un ingredient
     * @throws SQLException Exception possible liée à l'usage de la BDD
     */
    public void loadPage() throws SQLException{
         //Remplissage des champs avec les informations de la recette
            Recipes recipeSelected = listRecipes.get((Integer) dataReceive.get(0));
            this.inputID.setText(String.valueOf(recipeSelected.getId()));
            this.inputName.setText(recipeSelected.getName());
            this.ImgRecipe.setImage(new Image(recipeSelected.getPicture()));

            for (FontIcon starIcon: starRatings) {
                starIcon.setIconColor(Color.rgb(255,225,77));
            }

        for (Ingredients ingredient: recipeSelected.getIngredientInRecipe()) {
            HBox hbox = new HBox();
            hbox.setSpacing(30);
            hbox.setStyle("-fx-alignment: center-left");

            Label ingredientID = new Label();
            ingredientID.setText("ID : " + ingredient.getId());
            ingredientID.setStyle("-fx-font-weight: bold");

            Label ingredientName = new Label();
            ingredientName.setText(ingredient.getName());

            Image img;
            ImageView imageView = ingredient.getPicture_Img();

            hbox.getChildren().addAll(ingredientID,ingredientName,imageView);

            VBoxIngredients.getChildren().add(hbox);
            VBoxIngredients.setFillWidth(true);
        }
    }

    public void btnClick(ActionEvent actionEvent) {
            dataSend.add(listRecipes);
        // Redirection vers le formulaire 'Ingredients'
            DataHolder.getINSTANCE().ChangeScene((Stage) btnCancel.getScene().getWindow(),"Recipes","Recipes");
    }
}
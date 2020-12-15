package com.mealwith.GUI.Ingredients.Formulaire;

import com.mealwith.DAO.CategoriesIngredientsDAO;
import com.mealwith.DAO.OriginDAO;
import com.mealwith.DAO.UnitDAO;
import com.mealwith.Entity.CategoriesIngredients;
import com.mealwith.Entity.Ingredients;
import com.mealwith.Entity.Origin;
import com.mealwith.Entity.Unit;
import com.mealwith.GUI.Ingredients.IngredientsController;
import com.mealwith.Service.CustomsFonts;
import com.mealwith.Service.DataHolder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FormulaireController implements Initializable {
    @FXML
    public HBox Home,RatingHBox,RatingHBoxStar;
    @FXML
    public ImageView ImgLogo,ImgIngredient;
    @FXML
    public Button btnAccept,btnCancel;
    @FXML
    public ComboBox<String> comboUnit,comboOrigin,comboCategory;
    @FXML
    public TextField inputPrice,inputTempMin,inputTempMax,inputName,inputID;
    @FXML
    public Slider sliderTempMin,sliderTempMax;

    private final CustomsFonts customsFonts = new CustomsFonts(); // Service permettant de stocker les Fonts utilisés dans le projet
    public Text textLogo; // Logotype

    public List<Object> dataReceive = new ArrayList<>(); // Stockage des données récupérées du controlleur de provenance
    public OriginDAO repoOrigin = new OriginDAO();
    public ObservableList<String> listOrigin = FXCollections.observableArrayList();
    public UnitDAO repoUnit = new UnitDAO();
    public ObservableList<String> listUnit = FXCollections.observableArrayList();
    public CategoriesIngredientsDAO repoCatIngr = new CategoriesIngredientsDAO();
    public ObservableList<String> listCatIngr = FXCollections.observableArrayList();
    public List<FontIcon> starRatings = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Changement de la font du Logo
            textLogo.setFont(customsFonts.LogoFont(Double.parseDouble("80")));

        // Vide les précédentes données récupérées
            dataReceive.clear();

        // Ajout gestionnaire d'écoute sur le logo pour renvoyer au menu
            Home.setOnMouseClicked(event -> DataHolder.getINSTANCE().ChangeScene((Stage) Home.getScene().getWindow(),Home.getId(),Home.getId()));

        // Ajout gestionnaires d'écoute sur les sliders pour la température et les inputs réciproquement
            sliderTempMin.valueProperty().addListener((observable, oldValue, newValue) -> inputTempMin.setText(String.valueOf(newValue.intValue())));
            sliderTempMax.valueProperty().addListener((observable, oldValue, newValue) -> inputTempMax.setText(String.valueOf(newValue.intValue())));

            inputTempMin.setOnAction(event -> sliderTempMin.setValue(Double.parseDouble(inputTempMin.getText())));
            inputTempMax.setOnAction(event -> sliderTempMax.setValue(Double.parseDouble(inputTempMax.getText())));

        // Ajout gestionnaires d'écoute sur les sliders pour la température et les inputs réciproquement
            comboUnit.setOnInputMethodTextChanged(event -> comboUnit.cancelEdit());

        // Récupération des images
            ImgLogo.setImage(new Image("img/Logo Mealwith.png"));
            ImgIngredient.setImage(new Image("img/Ingredients.png"));

        // Récupération des étoiles du ratings
            for (Node star: RatingHBoxStar.getChildren()) {
                starRatings.add((FontIcon) star);
            }

        // Remplissage des combobox
            try {
                // Récupération des origines présentes dans la BDD et ajout dans la combobox
                    for (Origin origin: repoOrigin.List()) {
                        listOrigin.add(origin.getCountry());
                    }
                    comboOrigin.setItems(listOrigin);

                // Récupération des unités présentes dans la BDD et ajout dans la combobox
                    for (Unit unit: repoUnit.List()) {
                        listUnit.add(unit.getType());
                    }
                    comboUnit.setItems(listUnit);

                // Récupération des catégories d'ingrédients présentes dans la BDD et ajout dans la combobox
                    for (CategoriesIngredients catIngr: repoCatIngr.List()) {
                        listCatIngr.add(catIngr.getName());
                    }
                    comboCategory.setItems(listCatIngr);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


        try {
            // Récupération des données stockées par le controlleur de provenance
                getData();
            // Chargement des valeurs dans les champs si nécessaire
                loadPage(dataReceive.get(0).toString());
            // Débloque les saisies, si nécessaire
                unlockField(dataReceive.get(0).toString());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Récupère les datas stockées par le controlleur principal et remplis les champs s'il s'agit d'une modification ou d'un détail
     */
    public void getData()  {
        // Récupération de la stage principale à partir du tableau
            dataReceive.addAll(IngredientsController.dataSend);
    }

    /**
     * Fonction récupérant toute les informations nécessaire en cas de modification ou de visualisation d'un ingredient
     * @param operation Opération demandée "Modify","Add" ou "Details". Donnée émise par le controlleur de provenance
     * @throws SQLException Exception possible liée à l'usage de la BDD
     */
    public void loadPage(String operation) throws SQLException{

        // S'il s'agit d'une modification ou d'un détails
        if(operation.equals("Modify") || operation.equals("Details")){

            // Remplissage des champs avec les informations du disque
            Ingredients ingredients = (Ingredients) this.dataReceive.get(1);
            this.inputID.setText(String.valueOf(ingredients.getId()));
            this.inputName.setText(ingredients.getName());
            this.inputPrice.setText(String.valueOf(ingredients.getPrice()));
            this.inputTempMin.setText(String.valueOf(ingredients.getTemp_min()));
            this.sliderTempMin.setValue(ingredients.getTemp_min());
            this.inputTempMax.setText(String.valueOf(ingredients.getTemp_max()));
            this.sliderTempMax.setValue(ingredients.getTemp_max());
            this.ImgIngredient.setImage(new Image(ingredients.getPicture()));
            this.comboCategory.getSelectionModel().select(ingredients.getCategory_name());
            this.comboUnit.getSelectionModel().select(ingredients.getUnit_name());
            this.comboOrigin.getSelectionModel().select(ingredients.getOrigin_name());
            this.RatingHBox.setVisible(true);
            for (FontIcon starIcon: starRatings) {
                starIcon.setIconColor(Color.rgb(255,225,77));
            }
        }
    }

    /**
     * Rend éditable l'ensemble des champs et ajout du gestionnaire d'écoute sur l'image pour pouvoir la changer
      * @param operation Opération demandée "Modify","Add" ou "Details". Donnée émise par le controlleur de provenance
     */
    public void unlockField(String operation) {
        // S'il s'agit d'une modification exclusivement
        if (operation.equals("Modify") || operation.equals("Add")) {

            // rend editable les champs et les sliders
            this.inputID.setDisable(true);
            this.inputName.setEditable(true);
            this.inputPrice.setEditable(true);
            this.inputTempMin.setEditable(true);
            this.inputTempMax.setEditable(true);
            this.sliderTempMin.setDisable(false);
            this.sliderTempMax.setDisable(false);
            this.comboCategory.setDisable(false);
            this.comboUnit.setDisable(false);
            this.comboOrigin.setDisable(false);

            // Ajout gestionnaire d'écoute sur l'image, pour permettre sa modification
            ImgIngredient.setCursor(Cursor.cursor("HAND"));

            // FileChooser on Click
            ImgIngredient.setOnMouseClicked(event -> {
                // Création et paramétrage d'un filechooser
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Sélectionner une image");
                fileChooser.getExtensionFilters().addAll( //Extensions autorisées
                        new FileChooser.ExtensionFilter("Image (.jpg, .jpeg, .png)", "*.jpeg", "*.jpg", "*.png")
                );

                //Chemin d'accès au fichier de resources du projet pour paramétrage du chemin d'accès par défaut
                String directory = System.getProperty("user.dir");
                Path path = Paths.get(directory + "/src/main/resources/img");
                fileChooser.setInitialDirectory(new File(String.valueOf(path)));

                // Ouverture de la fenêtre de dialogue et récupération de l'image choisie
                File selectedFile = fileChooser.showOpenDialog(ImgIngredient.getScene().getWindow());

                // Modification de l'image prévisualisée si elle a été choisie
                if (selectedFile != null) {
                    Image newImage = new Image("/img/" + selectedFile.getName());
                    ImgIngredient.setImage(newImage);
                }
            });

            // Affichage PopUp info on Over
            ImgIngredient.setOnMouseEntered(event -> {
                PopOver popup = new PopOver();
                Label infoPopup = new Label();
                infoPopup.setText("Click on the image to change it");
                popup.setContentNode(infoPopup);
                popup.show(ImgIngredient);
                popup.setAutoHide(true);
            });
        }
    }

    public void btnClick(ActionEvent actionEvent) {
        // Récupère l'ID du bouton dans une string
            String btnText = ((Button)actionEvent.getSource()).getId();

        //Action différente selon le boutton
            /*if (btnText.equals("btnAccept")){
                // Ajout d'un ingredient à la base de données

            }*/

        // Redirection vers le formulaire 'Ingredients'
            DataHolder.getINSTANCE().ChangeScene((Stage) btnAccept.getScene().getWindow(),"Ingredients","Ingredients");
    }
}
package com.mealwith.gui.Ingredients.Formulaire;

import com.mealwith.DAO.CategoriesIngredientsDAO;
import com.mealwith.DAO.OriginDAO;
import com.mealwith.DAO.UnitDAO;
import com.mealwith.Entity.CategoriesIngredients;
import com.mealwith.Entity.Ingredients;
import com.mealwith.Entity.Origin;
import com.mealwith.Entity.Unit;
import com.mealwith.Service.SceneHandler;
import com.mealwith.gui.Ingredients.IngredientsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FormulaireController implements Initializable {
    @FXML
    public HBox Home;
    @FXML
    public ImageView ImgLogo,ImgIngredient;
    @FXML
    public Button btnAccept,btnCancel;
    @FXML
    public ComboBox<String> comboUnit;
    @FXML
    public ComboBox<String> comboOrigin;
    @FXML
    public ComboBox<String> comboCategory;
    @FXML
    public TextField inputPrice;


    public List<Object> dataReceive = new ArrayList<>(); // Stockage des données récupérées du controlleur de provenance
    public OriginDAO repoOrigin = new OriginDAO();
    public ObservableList<String> listOrigin = FXCollections.observableArrayList();
    public UnitDAO repoUnit = new UnitDAO();
    public ObservableList<String> listUnit = FXCollections.observableArrayList();
    public CategoriesIngredientsDAO repoCatIngr = new CategoriesIngredientsDAO();
    public ObservableList<String> listCatIngr = FXCollections.observableArrayList();
    public SceneHandler sceneHandler = new SceneHandler();
    public TextField inputTempMin,inputTempMax,inputName,inputID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Vide les précédentes données récupérées
            dataReceive.clear();

        // Gestionnaire d'écoute sur le logo pour renvoyer au menu
            Home.setOnMouseClicked(event -> sceneHandler.setScene(event,Home.getId(),Home.getId()));

        // Récupération des images
            Image Img = new Image("img/Logo Mealwith.png");
            ImgLogo.setImage(Img);

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

        // Récupération des données stockées par le controlleur de provenance
        try {
            getData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        /*// Ajout gestionnaire d'écoute sur la combobox, affiche l'inputArtist si la valeur sélectionnée est "Ajouter un nouvel artiste"
        this.comboArtist.valueProperty().addListener((observable, oldValue, newValue) ->
                inputArtist.setVisible(newValue.equals("Ajouter un nouvel artiste")));*/

        /*// Ajout gestionnaire d'écoute sur l'image, pour permettre sa modification
        if(dataReceive.get(0).equals("Modification")) {
            ImgDisc.setOnMouseClicked(event -> {
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
                File selectedFile = fileChooser.showOpenDialog(ImgDisc.getScene().getWindow());

                // Modification de l'image prévisualisée
                Image newImage = new Image("/img/" + selectedFile.getName());
                ImgDisc.setImage(newImage);
            });
        }*/
    }

    /**
     * Récupère les datas stockées par le controlleur principal et remplis les champs s'il s'agit d'une modification ou d'un détail
     */
    public void getData() throws SQLException {
        // Récupération de la stage principale à partir du tableau
            dataReceive.addAll(IngredientsController.dataSend);

        // S'il s'agit d'une modification
            if(this.dataReceive.get(0).equals("Modify") || this.dataReceive.get(0).equals("Details")){
                // Remplissage des champs avec les informations du disque
                    Ingredients ingredients = (Ingredients) this.dataReceive.get(1);
                    this.inputID.setText(String.valueOf(ingredients.getId()));
                    this.inputName.setText(ingredients.getName());
                    this.inputPrice.setText(String.valueOf(ingredients.getPrice()));
                    this.inputTempMin.setText(String.valueOf(ingredients.getTemp_min()));
                    this.inputTempMax.setText(String.valueOf(ingredients.getTemp_max()));
                    this.ImgIngredient.setImage(new Image(ingredients.getPicture()));
                    this.comboCategory.getSelectionModel().select(ingredients.getCategory_name());
                    this.comboUnit.getSelectionModel().select(ingredients.getUnit_name());
                    this.comboOrigin.getSelectionModel().select(ingredients.getOrigin_name());
            }
    }

    public void btnClick(ActionEvent actionEvent) {
        // Récupère l'ID du bouton dans une string
            String btnText = ((Button)actionEvent.getSource()).getId();

        //Action différente selon le boutton
        if (btnText.equals("btnAccept")){
            // Ajout d'un ingredient à la base de données

        }

        // Redirection vers le formulaire 'Ingredients'
            sceneHandler.setScene(actionEvent,"Ingredients","Ingredients");
    }
}
